(ns cmis.check.nagios.cpu  
  (:import [cmis.service.event AEventService])
  (:require [cmis.util :as u]
            [cmis.service.event :refer [insert]]))

(def nagios-cpu-pattern
  #"CPU STATISTICS OK : user=([\d.]+)% system=([\d.]+)% iowait=([\d.]+)% idle=([\d.]+)%")

(defn extract
  [^AEventService es {:keys [timestamp hostname msg] :as line}]
  (let [m (re-matches nagios-cpu-pattern msg)]
    (if m
      (let [[_ cpu-user cpu-system cpu-iowait cpu-idle] m]
        (doall
         (for [[check result] [["cpu user" cpu-user]
                               ["cpu system" cpu-system]
                               ["cpu io wait" cpu-iowait]
                               ["cpu idle" cpu-idle]]]
           (do
             (if (not (nil? result))
               (.insert es
                       {:timestamp timestamp
                        :name hostname
                        :event check
                        :unit "%"
                        :value (u/int-value (Double/parseDouble result))
                        :labels []})))))))))
