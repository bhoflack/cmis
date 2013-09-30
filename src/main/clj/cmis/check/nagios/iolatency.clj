(ns cmis.check.nagios.iolatency
  (:import [cmis.datastore ADataStore])
  (:require [cmis.util :as u]))

(def nagios-io-latency #".*- io .* latency=(\d+) ms")

(def check-name
  {"ESXi IO read" "io read latency"
   "ESXi IO write" "io write latency"})

(defn extract
  [^ADataStore ds {:keys [timestamp hostname msg check]}]
  (if (or (= "ESXi IO read" check)
          (= "ESXi IO write" check))  
    (let [m (re-matches nagios-io-latency msg)]
      (if m
        (let [[_ latency] m]
          (.save ds
                 {:timestamp timestamp
                  :hostname hostname
                  :check (get check-name check)
                  :unit "ms"
                  :value (u/int-value latency)}))))))