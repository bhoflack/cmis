(ns cmis.service.event-test
  (:use clojure.test
        cmis.service.event
        cmis.event.model)
  (:require [cmis.db :as db]
            [clj-time.core :as time]
            [clojure.java.jdbc :as jdbc]
            [honeysql.core :as sql]))

(deftest event-service-test
  (let [ds {:subprotocol "hsqldb"
            :subname "mem:cmis"
            :user "sa"
            :password ""}
        ss (cmis.service.event.StarService. ds)]
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
          )))

    (testing "Appending to the same ci"
      )
    
    ))