(ns cmis.service.idempotent
  "Idempotent service that builds on idempotentDS to store the filename."
  (:require [clojure.tools.logging :as log]
            cmis.datastore.idempotent)
  (:import [cmis.datastore.idempotent AIdempotentDS]
           [java.io File]))

(defprotocol AIdempotent
  (contains-entry? [this key] "Verify if a key already contains in the database")
  (put [this key] "Put an entry in the database"))

(deftype Idempotent [^AIdempotentDS ds]
  AIdempotent
  (contains-entry? [_ {:keys [path sha]}]
    (let [filename (-> path (File.) (.getName))
          entry (str filename "/" sha)]
      (log/info (str "Checking if entry " entry " exists"))
      (.contains-entry? ds entry)))
  
  (put [_ {:keys [path sha]}]
    (let [filename (-> path (File.) (.getName))
          entry (str filename "/" sha)]
      (log/info (str "Putting entry " entry))
      (.put ds entry))))