(ns cmis.core)

(defn import-checks
  ^{:doc "Import the results to the database"}
  [datastore checks]
  (map (fn [check] (.perform check datastore))
         checks))
  