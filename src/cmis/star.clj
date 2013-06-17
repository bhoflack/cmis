(ns cmis.star
  (:require [cmis.util :as util]
            [clojure.java.jdbc :as j]
            [clojure.java.jdbc.sql :as s]))

(defn in?
  [seq el]
  (some #(= el %) seq))

(defn submap
  ([map keys]
     (let [subset (filter (fn [[k _]] (in? keys k)) map)]
       (reduce (fn [acc [k v]] (assoc acc k v)) {} subset))))

(def dimensions (atom {}))
(def cache (atom {}))

(defn slowly-changing-dimension
  [ds table values keys]
  (let [keymap (submap values keys)
        add-to-cache! (fn [k v] (swap! cache assoc table (assoc (get @cache table) k v)))
        get-from-db (fn [] (first (j/query ds (s/select :id table (s/where keymap)))))
        insert-to-db (fn [uuid] (let [values* (assoc values :id uuid)]
                                  (j/insert! ds table values*)))
        uuid-from-cache (get (get @cache table) keymap)]    
    (if uuid-from-cache
       uuid-from-cache
      (let [uuid-from-db (:id (get-from-db))]
        (if uuid-from-db
          (do
            (add-to-cache! keymap uuid-from-db)
            uuid-from-db)
          (let [uuid (util/random-uuid)]
            (insert-to-db uuid)
            (add-to-cache! keymap uuid)
            uuid))))))