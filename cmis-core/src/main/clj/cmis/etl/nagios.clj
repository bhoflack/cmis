(ns cmis.etl.nagios
  (:require [clj-time.core :as time]
            [clj-time.coerce :as coerce]
            ))

(def nagios-cpu-pattern #"CPU STATISTICS OK : user=([\d.]+)% system=([\d.]+)% iowait=([\d.]+)% idle=([\d.]+)%")
(def nagios-io-latency #".*- io .* latency=(\d+) ms")
(def nagios-mem-free #"Memory .* - ([\d\.]*)% \(([\d]*)kB\) free")

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
                             