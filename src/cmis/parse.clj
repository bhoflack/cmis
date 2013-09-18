(ns cmis.parse
  (:use clojure.java.io
        clj-time.coerce
        [clj-time.core :exclude (extend)])
  (:require [clojure.java.jdbc :as j]
            [clojure.java.jdbc.sql :as s]
            [clojure.tools.logging :as log])
  (:gen-class))

(def nagios-service-pattern #"\[(\d+)\] [\w ]*SERVICE [\w]*: (.+);(.+);(\w+);(\w+);(\d*);(.*)")
(def nagios-cpu-pattern #"CPU STATISTICS OK : user=([\d.]+)% system=([\d.]+)% iowait=([\d.]+)% idle=([\d.]+)%")
(def nagios-io-latency #".*- io .* latency=(\d+) ms")
(def nagios-mem-free #"Memory .* - ([\d\.]*)% \(([\d]*)kB\) free")
(def iostat-re #"rsec/s=(\d+),wsec/s=(\d+),%util=(\d+)")

(defn list-files
  [dir]
  (filter #(.isFile %) (file-seq dir)))

(defn parse-service-lines
  [file]
  (let [reverse-cons (fn [seq x] (cons x seq))
        matches (agent [])]
    (with-open [rdr (reader file)]
      (doseq [line (line-seq rdr)]
        (let [m (re-matches nagios-service-pattern line)]
          (if m
            (let [[_ timestamp hostname check status _ _ msg] m
                  timestamp* (from-long (* (Long/parseLong timestamp) 1000))
                  check {:timestamp timestamp*
                         :hostname hostname
                         :check check
                         :status status
                         :msg msg}]
              (send matches reverse-cons check))))))
    @matches))
    
(defn uniq
  [col]
  (java.util.HashSet. col))

(defn to-double
  [n]
  (try
    (Double/parseDouble n)
    (catch java.lang.Exception _)))

(defn to-integer
  [n]
  (try
    (Integer/parseInt n)
    (catch java.lang.Exception _)))

(defn not-nil?
  ([obj] (not (nil? obj)))
  ([obj & others] (and (not-nil? obj)
                       (reduce (fn [el acc] (and el acc)) true (map not-nil? others)))))

(defn parse-memory-results
  [service-lines]
  (let [memory-results (filter #(= (:check %) "MEMORY") service-lines)
        memory-matches (filter #(-> %
                                    :msg
                                    (partial re-matches nagios-mem-free))
                               memory-results)]
    (filter (fn [res] (not-nil? (:memory-relative res) (:memory-absolute res)))
            (map (fn [res]
                   (let [[_ memory-relative memory-absolute] (re-matches nagios-mem-free (:msg res))]
                     (assoc res
                       :memory-relative (to-double memory-relative)
                       :memory-absolute (to-integer memory-absolute))))
                 memory-matches))))

(defn parse-iostat-results
  [service-lines]
  (let [iostat-results (filter #(= (:check %) "IOSTAT") service-lines)]
    (for [res iostat-results
          [dev results] (partition 2 (clojure.string/split (:msg res) #":"))]
      (let [[_ rsec wsec util] (re-matches iostat-re results)]
        (assoc res
          :device (cons {:device dev
                         :rsec (to-integer rsec)
                         :wsec (to-integer wsec)
                         :util (to-integer util)}
                        (:device res)))))))
                                    
(defn parse-cpu-results
  [service-lines]
  (let [cpu-results (filter #(= (:check %) "CPU_STATS") service-lines)
        cpu-matches (filter #(-> %
                             :msg
                             (partial re-matches nagios-cpu-pattern))
                        cpu-results)]
    (filter (fn [r] (not-nil? (:cpu-user r) (:cpu-system r) (:cpu-iowait r) (:cpu-idle r)))
            (map (fn [res]
                   (let [[_ cpu-user cpu-system cpu-iowait cpu-idle] (re-matches nagios-cpu-pattern (:msg res))]
                     (assoc res
                       :cpu-user (to-double cpu-user)
                       :cpu-system (to-double cpu-system)
                       :cpu-iowait (to-double cpu-iowait)
                       :cpu-idle (to-double cpu-idle))))
                 cpu-matches))))

(defn parse-latency-results
  [service-lines]
  (let [latency-results (filter #(or (= (:check %) "ESXi IO read")
                                     (= (:check %) "ESXi IO write"))
                             service-lines)
        latency-matches (filter #(-> %
                                     :msg
                                     (partial re-matches nagios-io-latency))
                                latency-results)]
    (filter #(not (nil? (:latency %)))
            (map (fn [res]
                   (let [[_ latency] (re-matches nagios-io-latency (:msg res))]
                     (assoc res :latency (to-integer latency))))
                 latency-matches))))

(defn random-uuid [] (java.util.UUID/randomUUID))

(defn in?
  [seq el]
  (some #(= el %) seq))

(defn submap
  ([map keys]
     (let [subset (filter (fn [[k _]] (in? keys k)) map)]
       (reduce (fn [acc [k v]] (assoc acc k v)) {} subset))))

(def dimensions (atom {}))
(def cache (atom {}))

(defn slowly-changing-dimension
  [ds table values keys]
  (let [keymap (submap values keys)
        add-to-cache! (fn [k v] (swap! cache assoc table (assoc (get @cache table) k v)))
        get-from-db (fn [] (first (j/query ds (s/select :id table (s/where keymap)))))
        insert-to-db (fn [uuid] (let [values* (assoc values :id uuid)]
                                  (j/insert! ds table values*)))

        uuid-from-cache (get (get @cache table) keymap)]
    (if uuid-from-cache
       uuid-from-cache
      (let [uuid-from-db (:id (get-from-db))]
        (if uuid-from-db
          (do
            (add-to-cache! keymap uuid-from-db)
            uuid-from-db)
          (let [uuid (random-uuid)]
            (insert-to-db uuid)
            (add-to-cache! keymap uuid)
            uuid))))))
        
(defn create-time-dimension
  [timestamp]
  {:ts (java.sql.Date. (to-long timestamp))
   :day (day timestamp)
   :hour (hour timestamp)
   :min (minute timestamp)
   :month (month timestamp)
   :year (year timestamp)
   })

(defmulti int-value type)
(defmethod int-value java.lang.Double
  [d] (if (nil? d) nil (.intValue d)))
(defmethod int-value java.lang.String
  [d] (if (nil? d) nil (Integer/parseInt d)))
(defmethod int-value :default
  [d] (throw (Exception. (str "No method for type " d " type: " (type d)))))

(defn- env-for-hostname
  [hostname]
  (cond
   (.contains hostname "-test") "test"
   (.contains hostname "-uat") "uat"
   :else "production"))

(defn to-star-schema
  [ds rs]
  (let [timedimension (create-time-dimension (:timestamp rs))
        time_id (slowly-changing-dimension ds :dim_time timedimension [:ts])
        serverdimension {:hostname (:hostname rs)}
        server_id (slowly-changing-dimension ds :dim_server serverdimension [:hostname])
        check-dimension {:name (:check rs) :unit (:unit rs)}
        check_id (slowly-changing-dimension ds :dim_check check-dimension [:name :unit])
        env-dimension {:environment (env-for-hostname (:hostname rs))}
        env_id (slowly-changing-dimension ds :dim_env env-dimension [:environment])
        val (:val rs)]
    (if (not (nil? val))
        (j/insert! ds :fact_measurement {:id (random-uuid)
                                         :time_id time_id
                                         :check_id check_id
                                         :server_id server_id
                                         :env_id env_id
                                         :val val}))))

(defmulti save (fn [_ rs] (get rs :check)))

(defmethod save "CPU_STATS"
  [ds rs]
  (for [[check unit val] [["cpu user" "%" (:cpu-user rs)]
                          ["cpu system" "%" (:cpu-system rs)]
                          ["cpu io wait" "%" (:cpu-iowait rs)]
                          ["cpu idle" "%" (:cpu-idle rs)]]]
    (to-star-schema ds {:timestamp (:timestamp rs)
                        :hostname (:hostname rs)
                        :check check
                        :unit unit
                        :val (if (nil? val) nil (int-value val))})))

(defmethod save "ESXi IO write"
  [ds rs]
  (to-star-schema ds {:timestamp (:timestamp rs)
                      :hostname (:hostname rs)
                      :check "io write latency"
                      :val (:latency rs)
                      :unit "ms"}))

(defmethod save "ESXi IO read"
  [ds rs]
  (to-star-schema ds {:timestamp (:timestamp rs)
                      :hostname (:hostname rs)
                      :check "io read latency"
                      :val (:latency rs)
                      :unit "ms"}))

(defmethod save "MEMORY"
  [ds rs]
  (for [[check unit val] [["memory relative" "%" (:memory-relative rs)]
                          ["memory absolute" "kB" (:memory-absolute rs)]]]
  (to-star-schema ds {:timestamp (:timestamp rs)
                      :hostname (:hostname rs)
                      :check check
                      :val val
                      :unit unit})))

(defmethod save "IOSTAT"
  [ds rs]
  (for [device (:device rs)
        [check value unit] [[(str "rsec " (:device device))  (:rsec device) "reads/sec"]
                            [(str "wsec " (:device device))  (:wsec device) "writes/sec"]
                            [(str "util " (:device device))  (:util device) "%"]]]
    (to-star-schema ds {:timestamp (:timestamp rs)
                        :hostname (:hostname rs)
                        :check check
                        :val value
                        :unit unit})))
        
(defmethod save :default
  [ds rs]
  (throw (Exception. (str "Check " (:check rs) " doesn't exist yet"))))

(defmulti process #(.isDirectory %))

(defmethod process true
  [dir]
  (println "Processing directory " dir)
  (map process (.listFiles dir)))

(defmethod process false
  [file]
  (println "Processing file " file)
  (let [ds {:subprotocol "postgresql"
            :classname "org.postgresql.Driver"            
            :subname "//localhost/cmis"
            :user "cmis"
            :password "cmis"}]
    (let [service-lines (parse-service-lines (.getPath file))
          cpu-results (parse-cpu-results service-lines)
          latency-results (parse-latency-results service-lines)
          memory-results (parse-memory-results service-lines)
          iostat-results (parse-iostat-results service-lines)]
      (for [rs (concat latency-results cpu-results memory-results iostat-results)]
        (do 
          (log/info "saving result " rs)
          (save ds rs))))))
  
(defn -main [f] (process (java.io.File. f)))

