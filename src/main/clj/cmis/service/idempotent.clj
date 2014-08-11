(ns cmis.service.idempotent
  "Idempotent service that builds on idempotentDS to store the filename."
  (:require cmis.datastore.idempotent)
  (:import [cmis.datastore.idempotent AIdempotentDS]
           [java.io File]))

(defprotocol AIdempotent
  (contains-entry? [this key] "Verify if a key already contains in the database")
  (put [this key] "Put an entry in the database"))

(deftype Idempotent [^AIdempotentDS ds]
  AIdempotent
  (contains-entry? [_ key]
    (-> key
        (File.)
        (.getName)
        (->> (.contains-entry? ds))))

  (put [_ key]
    (-> key
        (File.)
        (.getName)
        (->> (.put ds)))))
