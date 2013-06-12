(ns cmis.transform.cpu
  (:use cascalog.api)
  )

(def nagios-cpu-pattern #"CPU STATISTICS OK : user=([\d.]+)% system=([\d.]+)% iowait=([\d.]+)% idle=([\d.]+)%")

(deffilterop is-cpu-service-pattern? [msg]
  (re-matches nagios-cpu-pattern msg))

(defmapop cpu-service-pattern [msg]
  (map #(Double/parseDouble %)
       (rest (re-matches nagios-cpu-pattern msg))))
