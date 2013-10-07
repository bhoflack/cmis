(ns cmis.check
  (:import [cmis.datastore ADataStore])
  (:gen-class))

(defprotocol ACheck
  (perform [this ^DataStore ds] "Perform the checks and enter the results in the datasource"))