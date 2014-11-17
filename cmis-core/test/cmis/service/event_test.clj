(ns cmis.service.event-test
  (:use clojure.test
        cmis.service.event
        cmis.service.product
        cmis.event.model)
  (:require [cmis.db :as db]
            [clj-time.core :as time]
            [clojure.java.jdbc :as jdbc]
            [honeysql.core :as sql])
  (:import [cmis.service.event StarService]
           [cmis.service.product ProductService]
           [cmis.datastore.event EventDatastore]
           [cmis.datastore.ciapplication CiApplicationDatastore]))

(deftest event-service-test
  (let [ds {:subprotocol "hsqldb"
            :subname "mem:cmis"
            :user "sa"
            :password ""}
        eds (EventDatastore. ds)
        cids (CiApplicationDatastore. ds)
        ps (ProductService. ds)
        ss (StarService. eds cids ps)]
    (db/with-database! ds
      (testing "Inserting to the event service"
        (let [event1 (create-event (time/now)
                                   "free-memory"
                                   "MB"
                                   100
                                   "ewaf-test.colo.elex.be"
                                   "test"
                                   ["electronic-wafermapping"
                                    "cmis"
                                    "pp2"
                                    "diegem"])
              event2 (create-event (time/now)
                                   "free-memory"
                                   "MB"
                                   80
                                   "ewaf-uat.colo.elex.be"
                                   "test"
                                   ["electronic-wafermapping"
                                    "cmis"
                                    "pp2"
                                    "diegem"])
              event3 (create-event (time/now)
                                   "cpu usage"
                                   "%"
                                   5
                                   "ewaf-test.colo.elex.be"
                                   "test"
                                   ["electronic-wafermapping"
                                    "cmis"
                                    "pp2"
                                    "diegem"])
              [id1 id2 id3] [(insert ss event1)
                             (insert ss event2)
                             (insert ss event3)]]
          (is (= java.util.UUID (type id1)))

                                        ; verify that the measurement is stored in the db
          (is (= '({:value 100})
                 (jdbc/query ds
                             (sql/format {:select [:value]
                                          :from [[:fact_measurement :f]]
                                          :where [:= :f.id (.toString id1)]}))))

                                        ; verify that we can find the measurement using the dimensions
          (is (= #{{:value 80} {:value 100}}
                 (into #{}
                       (jdbc/query ds
                                   (sql/format {:select [:value]
                                                :from [[:fact_measurement :fm]]
                                                :join [[:dim_event :event] [:= :fm.event_id :event.id]]
                                                :where [:= :event.name "free-memory"]
                                                })))))
          ))
      
      (testing "Associating an application with a event"
                                        ; first associate an application with a hostname
        (->> {:name "ewaf"
              :background "bla bla bla"
              :ops_support "ALH"
              :users "???"
              :critical_level 1
              :version "2.24.0"
              :development "CBS"
              :business_owner "JUT"
              :installed_instances ["ewaf.colo.elex.be"]}
             (put ps))
                                        ; now save the event
        (->> (create-event (time/now)
                           "cpu usage"
                           "%"
                           10
                           "ewaf.colo.elex.be"
                           "prod"
                           [])
             (insert ss))

        (let [values (-> ds
                         (jdbc/query (sql/format {:select [:value]
                                                  :from [[:fact_measurement :fm]]
                                                  :join [[:fact_host_application :fha] [:= :fha.measurement_id :fm.id]
                                                         [:dim_ci :ci_app] [:= :fha.application_id :ci_app.id]]
                                                  :where [:= :ci_app.name "ewaf"]}))
                         (->> (into #{})))]        
                                        ; verify if we can find the event for the host
          (is (= values #{{:value 10}})))
      ))))