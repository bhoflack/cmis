(ns cmis.core
  (:use cascalog.api)
  (:require [cmis.transform :as transform]
            [cmis.transform.cpu :as cpu]
            [cascalog.ops :as c])
  (:gen-class))

(defn compute-nagios-cpu-report
  [output-tap nagios-dir]
  (?<- output-tap
       [?count]
       ((lfs-textline nagios-dir) ?l)
       (transform/is-nagios-service-line? ?l)
       (transform/nagios-service-line ?l :> ?timestamp ?host ?service ?state _ _ ?msg)
       (cpu/is-cpu-service-pattern? ?msg)
       (cpu/cpu-service-pattern ?msg :> ?cpu-user ?cpu-system ?cpu-iowait ?cpu-idle)
       (c/count ?count)
       ))

(defn compute-nagios-reports
  [output-tap nagios-dir]
  (compute-nagios-cpu-report output-tap nagios-dir))
  
(defn -main [nagios-dir output-dir]
  (compute-nagios-reports (lfs-textline output-dir) nagios-dir)
  )

