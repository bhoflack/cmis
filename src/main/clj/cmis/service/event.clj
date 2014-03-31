(ns cmis.service.event
  "Implementation of the service that writes events to the repository."
  (:use clojure.core.typed
        cmis.event.model)
  (:require [cmis.datastore.event :as eventdatastore]
            [cmis.datastore.ciapplication :as ciapplicationdatastore]
            [cmis.service.product :refer [products-for-hostname]])
  (:import [cmis.datastore.event AEventDatastore]
           [cmis.datastore.ciapplication ACiApplicationDatastore]
           [cmis.service.product AProductService]))

(ann-protocol AEventService
              insert [AEventService Event -> (Option EventId)])
(defprotocol> AEventService
  "A event service captures events and saves them in the repository to aid querying"

  (insert [es event] "Insert an event into the repository"))

(deftype StarService
    [^AEventDatastore eds
     ^ACiApplicationDatastore cids
     ^AProductService ps]
  AEventService
  (insert [this event]    
    (let [measurementid (eventdatastore/save eds event)]
      (some-> (products-for-hostname ps (:name event))
              (->> (map (partial assoc {:timestamp (:timestamp event)
                                        :measurementid measurementid
                                        :hostname (:name event)}
                                 :applicationid)))
              (->> (map (partial ciapplicationdatastore/save cids)))
              (doall))
      measurementid)    
    ))