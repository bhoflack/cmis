(ns cmis.core
  (:require [clojure.tools.cli :refer [parse-opts]]
            [clojurewerkz.quartzite.scheduler :as qs]
            [clojurewerkz.quartzite.triggers :as t]
            [clojurewerkz.quartzite.jobs :as j]
                                        ;[clojurewerkz.quartzite.schedule.daily-interval :refer [schedule starting-daily-at every-day time-of-day with-interval-in-days]]
            [clojurewerkz.quartzite.schedule.cron :refer [schedule cron-schedule]]
            cmis.datastore.event
            cmis.datastore.ciapplication
            cmis.datastore.idempotent
            cmis.jobs
            cmis.service.event
	    cmis.service.product)
  (:import [cmis.datastore.event EventDatastore]
           [cmis.datastore.ciapplication CiApplicationDatastore]
           [cmis.datastore.idempotent Idempotent]
           [cmis.jobs CmdbImport NagiosImport]
           [cmis.service.event StarService]
           [cmis.service.product ProductService])
  (:gen-class))

(defn -main
  [& args]
  (qs/initialize)
  (qs/start)
  (let [ds {:subprotocol "postgresql"
            :classname "org.postgresql.Driver"
            :subname "//localhost/cmis2"
            :user "cmis"
            :password "cmis"}
        
        ps (ProductService. ds)
        eds (EventDatastore. ds)
        cids (CiApplicationDatastore. ds)
        event-service (StarService. eds cids ps)
        idempotentds (Idempotent. ds)
        
        cmdb-job (j/build
                  (j/of-type CmdbImport)
                  (j/with-identity (j/key "cmdb-import"))
                  (j/using-job-data {:productservice ps}))
        cmdb-trigger (t/build
                      (t/with-identity (t/key "cmdb-import-trigger"))
                      (t/start-now)
                      (t/with-schedule (schedule
                                        (cron-schedule "0 0 1 * * ?"))))
        nagios-job (j/build
                    (j/of-type NagiosImport)
                    (j/with-identity (j/key "nagios-import"))
                    (j/using-job-data {:idempotent idempotentds
                                       :event-service event-service}))
        nagios-trigger (t/build
                        (t/with-identity (t/key "nagios-import-trigger"))
                        (t/start-now)
                        (t/with-schedule (schedule
                                          (cron-schedule "0 0 2 * * ?"))))]
    (qs/schedule cmdb-job cmdb-trigger)
    (qs/schedule nagios-job nagios-trigger)))
        