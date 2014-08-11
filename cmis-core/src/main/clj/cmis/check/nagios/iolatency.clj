(ns cmis.check.nagios.iolatency
  (:import [cmis.service.event AEventService])
  (:require [cmis.util :as u]
            [cmis.service.event :refer [insert]]))

(def nagios-io-latency #".*- io .* latency=(\d+) ms")

(def check-name
  {"ESXi IO read" "io read latency"
   "ESXi IO write" "io write latency"})

(defn extract
  [^AEventService ds {:keys [timestamp hostname msg check]}]
  (if (or (= "ESXi IO read" check)
          (= "ESXi IO write" check))  
    (let [m (re-matches nagios-io-latency msg)]
      (if m
        (let [[_ latency] m]
          (insert ds
                  {:timestamp timestamp
                   :name hostname
                   :event (get check-name check)
                   :unit "ms"
                   :value (u/int-value latency)
                   :labels []}))))))