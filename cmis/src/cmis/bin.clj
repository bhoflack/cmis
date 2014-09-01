(ns cmis.bin
  (:require cmis.core
            cmis-dashboard.handler
            cmis.check.nagios)  
  (:use ring.server.standalone)
  (:import [com.mchange.v2.c3p0 ComboPooledDataSource])
  (:gen-class :name cmis.bin))

(defn -main
  ([] (-main "cmis.config"))
  ([config-file]
     (let [{:keys [datasource hostname private-key-path username]}
           (-> config-file
               (slurp)
               (clojure.edn/read-string))]

       (assert datasource  "The datasource is required")
       (assert hostname "The hostname is required")
       (assert private-key-path "The private key path is required")
       (assert username "The username is required")
       
       (reset! cmis.check.nagios/config {:hostname hostname
                                         :private-key-path private-key-path
                                         :username username})

       (let [ds (doto (ComboPooledDataSource.)
                  (.setDriverClass (:classname datasource))
                  (.setJdbcUrl (str "jdbc:" (:subprotocol datasource) ":" (:subname datasource)))
                  (.setUser (:user datasource))
                  (.setPassword (:password datasource))
                  ;; expire excess connections after 30 minutes of inactivity:
                  (.setMaxIdleTimeExcessConnections (* 30 60))
                  ;; expire connections after 3 hours of inactivity:
                  (.setMaxIdleTime (* 3 60 60)))]
         (reset! cmis-dashboard.handler/ds datasource)
         (cmis.core/schedule-jobs ds)
         (serve cmis-dashboard.handler/app {:open-browser? false})))))
