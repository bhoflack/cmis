(ns cmis.service.ci-test
  (:use clojure.test
        cmis.service.ci)
  (:require [cmis.db :as db]
            [clojure.java.jdbc :as jdbc]
            [honeysql.core :as sql])
  (:import [cmis.service.ci CiService]))

(deftest insert-ci-test
  (let [ds {:subprotocol "hsqldb"
            :subname "mem:cmis"
            :user "sa"
            :password ""}
        cis (CiService. ds)]
    (db/with-database! ds

      (testing "Inserting a ci"
        (let [ci {:name "riddler.colo.elex.be"
                  :cpu "Intel(R) Xeon(R) CPU           E5620  @ 2.40GHz"
                  :num_cpus 16
                  :memory 6131640}
              id (.insert cis ci)]
          (is (= ci
                 (first 
                  (jdbc/query ds
                              (sql/format {:select [:name :cpu :num_cpus :memory]
                                           :from [:dim_ci]
                                           :where [:= :id (.toString id)]})))))))

      (testing "Inserting a ci with a parent"
        (let [host {:name "riddler.colo.elex.be"
                    :cpu "Intel(R) Xeon(R) CPU           E5620  @ 2.40GHz"
                    :num_cpus 16
                    :memory 6131640}
              host_id (.insert cis host)
              ewaf {:name "ewaf.colo.elex.be"
                    :cpu "Intel(R) Xeon(R) CPU           E5620  @ 2.40GHz"
                    :num_cpus 4
                    :memory 1024
                    :parent host_id}
              dlog {:name "dlog.colo.elex.be"
                    :cpu "Intel(R) Xeon(R) CPU           E5620  @ 2.40GHz"
                    :num_cpus 4
                    :memory 2048
                    :parent host_id}]
          (doall (map (fn [ci] (.insert cis ci)) [ewaf dlog]))
          
          (is (= #{"ewaf.colo.elex.be" "dlog.colo.elex.be"}
                 (into #{}
                       (map :name
                            (jdbc/query ds
                                        (sql/format {:select [:child.name]
                                                     :from [[:dim_ci :parent]]
                                                     :join [[:dim_ci :child] [:= :parent.id :child.parent]]
                                                     :where [:= :parent.name "riddler.colo.elex.be"]}))))))))

      (testing "Updating a ci"
        (let [updated_host {:name "riddler.colo.elex.be"
                            :cpu "AMD Opteron blablabla"
                            :num_cpus 128
                            :memory 128000000}]
          (.insert cis updated_host)
          (is (= (list updated_host)
                 (jdbc/query ds
                             (sql/format {:select [:name :cpu :num_cpus :memory]
                                          :from [:dim_ci]
                                          :where [:and
                                                  [:= :name "riddler.colo.elex.be"]
                                                  [:= :stopped_at nil]]}))))))
                                         
        )))
          
          
           
          
      

        