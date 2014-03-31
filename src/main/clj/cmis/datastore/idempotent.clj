(ns cmis.datastore.idempotent
  (:require [clojure.java.jdbc :as jdbc]
            [honeysql.core :as sql]
            [clj-time.core :refer [now]]
            [clj-time.coerce :refer [to-timestamp]]
            [cmis.util :refer [convert-uuid]]))

(defprotocol AIdempotent
  (contains-entry? [this key] "Verify if a key already contains in the database")
  (put [this key] "Put an entry in the database"))

(defrecord Idempotent [ds]
  AIdempotent
  (contains-entry? [_ key]
    (some-> ds
            (jdbc/query (sql/format {:select [:key]
                                     :from [:idempotent_nagios_archives]
                                     :where [:= :key key]}))
            (->> (some #(= key (:key %))))))
  (put [_ key]
    (let [id (->> (java.util.UUID/randomUUID)
                  (convert-uuid ds))
          created-at (-> (now)
                         (to-timestamp))]          
      (some-> ds
              (jdbc/insert! :idempotent_nagios_archives
                            {:id id
                             :key key
                             :created_at created-at})))))
    
    

