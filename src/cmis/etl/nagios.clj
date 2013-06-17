(ns cmis.etl.nagios
  (:require [clj-time.core :as time]
            [clj-time.coerce :as coerce])
  )

(def nagios-service-pattern #"\[(\d+)\] [\w ]*SERVICE [\w]*: (.+);(.+);(\w+);(\w+);(\d*);(.*)")
(def nagios-cpu-pattern #"CPU STATISTICS OK : user=([\d.]+)% system=([\d.]+)% iowait=([\d.]+)% idle=([\d.]+)%")
(def nagios-io-latency #".*- io .* latency=(\d+) ms")
(def nagios-mem-free #"Memory .* - ([\d\.]*)% \(([\d]*)kB\) free")

(defn each-line
  "Perform actions on each line of a file"
  [file & fns]
  (with-open [reader (clojure.java.io/reader file)]
    (doseq [line (line-seq reader)]
      (doall
       (map #(% line) fns)))))

(defn each-service-line
  "Perform actions on each service line of a file"
  [file & fns]
  (each-line file
             (fn [line]
               (let [m (re-matches nagios-service-pattern line)]
                 (if m
                   (let [[_ timestamp hostname check status _ _ msg] m
                         timestamp* (coerce/from-long (* (Long/parseLong timestamp) 1000))
                         check {:timestamp timestamp*
                                :hostname hostname
                                :check check
                                :status status
                                :msg msg}]
                     (doall
                      (map #(% check) fns))))))))

(defn reverse-cons [seq x] (cons x seq))

(defmulti int-value type)
(defmethod int-value java.lang.Double
  [d] (if (nil? d) nil (.intValue d)))
(defmethod int-value java.lang.String
  [d] (if (nil? d) nil (Integer/parseInt d)))
(defmethod int-value :default
  [d] (throw (Exception. (str "No method for type " d " type: " (type d)))))

(defn extract-cpu-info
  [results {:keys [timestamp hostname msg] :as line}]
  (let [m (re-matches nagios-cpu-pattern msg)]
    (if m
      (let [[_ cpu-user cpu-system cpu-iowait cpu-idle] m]
        (doall
         (for [[check result] [["cpu user" cpu-user]
                               ["cpu system" cpu-system]
                               ["cpu io wait" cpu-iowait]
                               ["cpu idle" cpu-idle]]]
           (if (not (nil? result))
             (send results reverse-cons {:timestamp timestamp
                                         :hostname hostname
                                         :check check
                                         :unit "%"
                                         :value (int-value (Double/parseDouble result))}))))))))

(def check-name
  {"ESXi IO read" "io read latency"
   "ESXi IO write" "io write latency"})

(defn extract-io-latency
  [results {:keys [timestamp hostname msg check]}]
  (if (or (= "ESXi IO read" check)
          (= "ESXi IO write" check))  
    (let [m (re-matches nagios-io-latency msg)]
      (if m
        (let [[_ latency] m]
          (send results reverse-cons {:timestamp timestamp
                                      :hostname hostname
                                      :check (get check-name check)
                                      :unit "ms"
                                      :value (int-value latency)}))))))

(defn parse
  [& files]
  (let [measurements (agent [])]
    (doall
     (map #(each-service-line %
                              (partial extract-cpu-info measurements)
                              (partial extract-io-latency measurements))
         files))
    @measurements))
                             