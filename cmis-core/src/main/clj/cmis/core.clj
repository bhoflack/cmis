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
	    cmis.service.product)
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
  [ds]
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
                  (j/using-job-data {:productservice ps}))
        
        cmdb-trigger (t/build
                      (t/with-identity (t/key "cmdb-import-trigger"))
                      (t/start-now)
                      (t/with-schedule (schedule
                                        (cron-schedule "0 0 1 * * ?"))))                      
        nagios-job (j/build
                    (j/of-type NagiosImport)
                    (j/with-identity (j/key "nagios-import"))
                    (j/using-job-data {:idempotent idempotent
                                       :event-service event-service}))

        nagios-trigger (t/build
                        (t/with-identity (t/key "nagios-import-trigger"))
                        (t/start-now)
                        (t/with-schedule (schedule
                                          (cron-schedule "0 0 1 * * ?"))))]       
    (qs/initialize)
    (qs/start)        
    (qs/schedule cmdb-job cmdb-trigger)
    (qs/schedule nagios-job nagios-trigger)))
  

(defn -main
  ([] (-main "cmis.config"))
  ([config-file]
     (let [config (-> config-file
                      (slurp)
                      (clojure.edn/read-string))
           ds-spec (:datasource config)
           ds (doto (ComboPooledDataSource.)
                (.setDriverClass (:classname ds-spec)) 
                (.setJdbcUrl (str "jdbc:" (:subprotocol ds-spec) ":" (:subname ds-spec)))
                (.setUser (:user ds-spec))
                (.setPassword (:password ds-spec))
                ;; expire excess connections after 30 minutes of inactivity:
                (.setMaxIdleTimeExcessConnections (* 30 60))
                ;; expire connections after 3 hours of inactivity:
                (.setMaxIdleTime (* 3 60 60)))]
       (schedule ds))))
        