(ns cmis.star-test
  (:use clojure.test
        cmis.star)
  (:require [cmis.db :as db]
            [clojure.java.jdbc :as jdbc]
            [honeysql.core :as sql]))

(deftest insert-to-db-test
    (let [ds {:subprotocol "hsqldb"
              :subname "file:/tmp/cmis"
              :user "sa"
              :password ""}]
    (db/with-database! ds
      (let [host {:name "hosta"
                  :cpu "AMD Opteron"
                  :num_cpus 4
                  :memory 2048
                  }
            host* {:name "hosta"
                   :cpu "Intel Xeon"
                   :num_cpus 4
                   :memory 2048
                   }]

        (testing "It saves the dimension"
          (slowly-changing-dimension ds
                                     :dim_ci
                                     host
                                     [:name :cpu :num_cpus :memory]
                                     [:name])
          (is (= host
                 (first (jdbc/query ds (sql/format {:select [:name :cpu :num_cpus :memory]
                                                    :from [:dim_ci]
                                                    :where [:= :name (:name host)]}))))))
        
        (testing "When the identifiers are changed,  it saves a new entry and marks the old as closed"
          (slowly-changing-dimension ds
                                     :dim_ci
                                     host*
                                     [:name :cpu :num_cpus :memory]
                                     [:name])
          (let [hosts (jdbc/query ds (sql/format {:select [:name :cpu :num_cpus :memory :created_at :stopped_at]
                                                  :from [:dim_ci]
                                                  :where [:= :name (:name host)]}))]
            (is (= 2 (count hosts)))          
            (is (some (fn [h]
                        (and (= (:cpu host) (:cpu h))
                             (not (nil? (:stopped_at h)))))
                      hosts))
            (is (some (fn [h]
                        (and (= (:cpu host*) (:cpu h))
                             (nil? (:stopped_at h))))
                      hosts))

            (is (every? (fn [h] (not (nil? (:created_at h)))) hosts))
            ))
        ))))
        