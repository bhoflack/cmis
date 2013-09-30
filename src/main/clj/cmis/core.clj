(ns cmis.core
  (:import [cmis.check ACheck]
           [cmis.datastore ADataStore]))

(defn import-checks
  ^{:doc "Import the results to the database"}
  [^ADataStore datastore
   checks]
  (map (fn [^ACheck check] (.perform check datastore))
         checks))
  