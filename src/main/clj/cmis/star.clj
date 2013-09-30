(ns cmis.star
  (:require [cmis.util :as util]
            [clojure.java.jdbc :as j]
            [clojure.java.jdbc.sql :as s]
            [clj-time.core :as time]
            [clj-time.coerce :as coerce]))

(defn in?
  [seq el]
  (some #(= el %) seq))

(def dimensions (atom {}))
(def cache (atom {}))

(defn clear-cache [] (swap! cache {}))

(defn mark-previous-dimensions-as-stopped
  "Update a dimension as stopped where the identifiers match and hasn't been stopped before"
  [ds table identifiermap]
  (let [identifiermap* (assoc identifiermap :stopped_at nil)]
    (j/update! ds table {:stopped_at (coerce/to-timestamp (time/now))}
               (s/where identifiermap*))))

(defn insert-to-db!
  "Insert a new dimension to the database

   Save a new dimension to the database,  and mark previous open dimension as closed ( if any )"
  [ds table uuid values identifiermap]
  (let [values* (assoc values
                  :id uuid
                  :created_at (coerce/to-timestamp (time/now)))]
    (mark-previous-dimensions-as-stopped ds table identifiermap)
    (j/insert! ds table values*)))

(defn find-dimension
  "Query for the uuid in the given table where the dimension matches the where clause

   Returns a list of uuids that match with the predicate"
  [ds table where-clause]
  (let [where-clause* (assoc where-clause :stopped_at nil)]
    (j/query ds
             (s/select :id table
                       (s/where where-clause*)))))

(defn slowly-changing-dimension
  [ds table values keys identifiers]
  (let [keymap             (assoc (select-keys values keys) :stopped_at nil)
        identifiermap      (select-keys values identifiers)
        add-to-cache!      (fn [k v] (swap! cache assoc table (assoc (get @cache table) k v)))
        get-from-db        (fn [] (first (j/query ds (s/select :id table (s/where keymap)))))
        uuid-from-cache    (get (get @cache table) keymap)]    
    (if uuid-from-cache
       uuid-from-cache
      (let [uuid-from-db (:id (get-from-db))]
        (if uuid-from-db
          (do
            (add-to-cache! keymap uuid-from-db)
            uuid-from-db)
          (let [uuid (util/random-uuid)]
            (insert-to-db! ds table uuid values identifiermap)
            (add-to-cache! keymap uuid)
            uuid))))))

(defn insert
  [ds table value]
  (j/insert! ds table value))