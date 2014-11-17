(ns cmis.service.event
  "Implementation of the service that writes events to the repository."
  (:use clojure.core.typed
        cmis.event.model)
  (:require [cmis.datastore.event :as eventdatastore]
            [cmis.datastore.ciapplication :as ciapplicationdatastore]
            [cmis.service.product :refer [products-for-hostname]]
            [clojure.tools.logging :as log])
  (:import [cmis.datastore.event AEventDatastore]
           [cmis.datastore.ciapplication ACiApplicationDatastore]
           [cmis.service.product AProductService]))

(defn environment-for-event
  [{:keys [name]}]
  (cond
   (.contains name "-test") "test"
   (.contains name "-uat") "uat"
   :else "prod"))

(defn add-environment-to-event
  [event]
  (-> event
      (assoc :environment (environment-for-event event))))
       
(defprotocol AEventService
  "A event service captures events and saves them in the repository to aid querying"

  (insert [es event] "Insert an event into the repository")
  (slowly-changing-dimension [es table values keys identifiers]))

(deftype StarService
    [^AEventDatastore eds
     ^ACiApplicationDatastore cids
     ^AProductService ps]
  AEventService
  (insert [this event]    
    (let [measurementid (-> event
                            (add-environment-to-event)
                            (->> (eventdatastore/save eds)))]
      (some-> (products-for-hostname ps (:name event))
              (->> (map (partial assoc {:timestamp (:timestamp event)
                                        :measurementid measurementid
                                        :hostname (:name event)}
                                 :applicationid)))
              (->> (map (partial ciapplicationdatastore/save cids)))
              (doall))
      measurementid))

  (slowly-changing-dimension [_ table values keys identifiers]
    (eventdatastore/slowly-changing-dimension eds table values keys identifiers)))