(ns cmis.core
  (:require clojure.edn
            [clojure.tools.cli :refer [parse-opts]]
            [clojurewerkz.quartzite.scheduler :as qs]
            [clojurewerkz.quartzite.triggers :as t]
            [clojurewerkz.quartzite.jobs :as j]
            [clojurewerkz.quartzite.schedule.cron :refer [schedule cron-schedule]]
            cmis.datastore.event
            cmis.datastore.ciapplication
            cmis.datastore.idempotent
            cmis.jobs
            cmis.service.event
            cmis.service.idempotent
	    cmis.service.product
            [clojure.tools.logging :as log])
  (:import [cmis.datastore.event EventDatastore]
           [cmis.datastore.ciapplication CiApplicationDatastore]
           [cmis.datastore.idempotent IdempotentDS]
           [cmis.service.idempotent Idempotent]
           [cmis.jobs CmdbImport NagiosImport]
           [cmis.service.event StarService]
           [cmis.service.product ProductService]
           [com.mchange.v2.c3p0 ComboPooledDataSource])
  (:gen-class :name cmis.core))

(defn schedule-jobs
  [ds cmdb-product-url]
  (log/info "Scheduling the quartz jobs")
  (let [ds1 {:datasource ds}        
        ps (ProductService. ds1)
        eds (EventDatastore. ds1)
        cids (CiApplicationDatastore. ds1)
        event-service (StarService. eds cids ps)
        idempotentds (IdempotentDS. ds1)
        idempotent (Idempotent. idempotentds)
        
        cmdb-job (j/build
                  (j/of-type CmdbImport)
                  (j/with-identity (j/key "cmdb-import"))
                  (j/using-job-data {:productservice ps
                                     :cmdb-product-url cmdb-product-url}))
        cmdb-startup-job (j/build
                  (j/of-type CmdbImport)
                  (j/with-identity (j/key "cmdb-startup-import"))
                  (j/using-job-data {:productservice ps
                                     :cmdb-product-url cmdb-product-url}))

        
        cmdb-trigger (t/build
                      (t/with-identity (t/key "cmdb-import-trigger"))
                      (t/start-now)
                      (t/with-schedule (schedule
                                        (cron-schedule "0 0 1 * * ?"))))

        startup-cmdb (t/build
                      (t/with-identity (t/key "startup-cmdb"))
                      (t/start-now))
        
        nagios-job (j/build
                    (j/of-type NagiosImport)
                    (j/with-identity (j/key "nagios-import"))
                    (j/using-job-data {:idempotent idempotent
                                       :event-service event-service}))

        nagios-startup-job (j/build
                    (j/of-type NagiosImport)
                    (j/with-identity (j/key "nagios-startup-import"))
                    (j/using-job-data {:idempotent idempotent
                                       :event-service event-service}))
        
        startup-nagios (t/build
                        (t/with-identity (t/key "startup-nagios"))
                        (t/start-now))

        nagios-trigger (t/build
                        (t/with-identity (t/key "nagios-import-trigger"))
                        (t/start-now)
                        (t/with-schedule (schedule
                                          (cron-schedule "0 25 * * * ?"))))]       
    (qs/initialize)
    (qs/start)

    (qs/schedule cmdb-job cmdb-trigger)
    (qs/schedule cmdb-startup-job startup-cmdb)
    (qs/schedule nagios-job nagios-trigger)
    (qs/schedule nagios-startup-job nagios-trigger)))
