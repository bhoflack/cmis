(ns cmis.star
  ^{:doc "A simple module to provide support for start scheme databases."}
  (:require [cmis.util :as util]
            [clojure.java.jdbc :as j]
            [java-jdbc.sql :as s]
            [honeysql.core :as sql]
            [clj-time.core :as time]
            [clj-time.coerce :as coerce]
            [clojure.tools.logging :as log]))

(defprotocol AUUIDCache
  ^{:doc "The protocol to describe a uuid cache."}
  (put-uuid! [cache table key value] "Put an entry in the cache for the given table,  key and value")
  (get-uuid [cache table key] "Get an entry from the cache from the given table and key")
  (clear! [cache] "Clear the cache"))

(deftype MemoryCache [cache]
  ^{:doc "An in memory implementation of a uuid cache."}
  AUUIDCache
  (put-uuid! [this table key value]
    (swap! cache assoc table (get @cache table)))

  (get-uuid [this table key]
    (get (get @cache table) key))

  (clear! [this]
    (swap! cache (atom {}))))

(def ^{:doc "The default AUUIDCache implementation,  used by slowly-changing-dimension."}
  default-cache (MemoryCache. (atom {})))

(defn- mark-previous-dimensions-as-stopped
  "Update a dimension as stopped where the identifiers match and hasn't been stopped before"
  [ds table identifiermap]  
  (let [identifiermap* (assoc identifiermap :stopped_at nil)]
    (log/debug "Marking dimensions as stopped which match " identifiermap*)

    (j/execute! ds
                [(s/update table {:stopped_at (coerce/to-timestamp (time/now))}
                           (s/where identifiermap*))])))
;    (j/update! ds table 
;               [(s/where identifiermap*)])))

(defn- insert-to-db!
  "Insert a new dimension to the database

   Save a new dimension to the database,  and mark previous open dimension as closed ( if any )

   Parameters
     - ds            - a map containing the datasource
     - table         - the name of the table
     - uuid          - the uuid of the entry
     - values        - a map containing the column name and the value
     - identifiermap - a map containing the identifiers

   Returns"
  [ds table uuid values identifiermap]
  (let [values* (assoc values
                  :id uuid
                  :created_at (coerce/to-timestamp (time/now)))
        values** (util/convert-uuids ds values*)
        identifiermap* (util/convert-uuids ds identifiermap)]
    (log/debug "Inserting to db table " table " uuid " uuid " values " values** " identifiers " identifiermap)
    (mark-previous-dimensions-as-stopped ds table identifiermap*)
    (j/insert! ds table values**)))

(defn find-dimension
  "Query for the uuid in the given table where the dimension matches the where clause

   Returns a list of uuids that match with the predicate"
  [ds table where-clause]
  (let [where-clause* (assoc where-clause :stopped_at nil)]
    (j/query ds
             (s/select :id table
                       (s/where where-clause*)))))

(defn slowly-changing-dimension
  "Save an entry into a slowly changing dimension

   Parameters
     - ds          - a map containing the datasource data
     - table       - a string or keyword containing the table name
     - values      - a map containing the values,  with as keys the column name and values the value
     - keys        - a seq containing the keys that mark when the dimension should be updated
     - identifiers - a seq containing the keys that identify the dimension
     - cache       - the AUuidCache cache to use ( optional,  defaults to default-cache )

   Example
     (slowly-changing-dimension ds
                                :dim_ci
                                {:memory 2048 :hostname \"test\" :num_cpus 2}
                                [:hostname :memory :num_cpus]
                                [:hostname])
     ; returns #uuid \"68333cc2-5e39-43d7-9410-7ca5f7d34dba\"

   Returns the uuid for the entry."
  ([ds table values keys identifiers cache]
     (assert values)
     (log/info "Saving to slowly changing dimension " table " " values)
     (let [keymap             (assoc (select-keys values keys) :stopped_at nil)
           identifiermap      (select-keys values identifiers)
           get-from-db        (fn [] (first (j/query ds (s/select :id table (s/where keymap)))))
           uuid-from-cache    (get-uuid cache table keymap)]
       (log/debug "Checking if entry exists for table " table " keys " keys " identifiers " identifiers " " identifiermap)
       (if uuid-from-cache
         uuid-from-cache
         (let [uuid-from-db (:id (get-from-db))]
           (if uuid-from-db
             (do
               (put-uuid! cache table  keymap uuid-from-db)
               uuid-from-db)
             (let [uuid (util/random-uuid)]
               (insert-to-db! ds table uuid values identifiermap)
               (put-uuid! cache table keymap uuid)
               uuid))))))
  ([ds table values keys identifiers]
     (assert values)
     (slowly-changing-dimension ds table values keys identifiers default-cache)))

(defn insert
  [ds table value]
  (j/insert! ds table
             (util/convert-uuids ds value)))