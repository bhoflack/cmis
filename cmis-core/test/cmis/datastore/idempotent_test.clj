(ns cmis.datastore.idempotent-test
  (:use clojure.test
        cmis.datastore.idempotent)
  (:import [cmis.datastore.idempotent Idempotent])
  (:require [cmis.db :as db]))

(deftest idempotent-test
    (let [ds {:subprotocol "hsqldb"
              :subname "mem:cmis"
              :user "sa"
              :password ""}
          idempotent (Idempotent. ds)]
      (db/with-database! ds
        (testing "When an entry doesn't exist it returns false"
          (is (= nil
                 (contains-entry? idempotent "does not exist"))))

        (testing "Allows to add entries to the database"
          (put idempotent "/my/path")
          (is (= true (contains-entry? idempotent "/my/path")))))))
                 
            

