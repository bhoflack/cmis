(ns cmis.check.nagios.cpu
  (:import [cmis.datastore ADataStore])  
  (:require [cmis.util :as u]))

(def nagios-cpu-pattern
  #"CPU STATISTICS OK : user=([\d.]+)% system=([\d.]+)% iowait=([\d.]+)% idle=([\d.]+)%")

(defn extract
  [^ADataStore ds {:keys [timestamp hostname msg] :as line}]
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
               (.save ds
                      {:timestamp timestamp
                       :hostname hostname
                       :check check
                       :unit "%"
                       :value (u/int-value (Double/parseDouble result))})))))))))
