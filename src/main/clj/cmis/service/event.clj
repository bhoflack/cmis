(ns cmis.service.event
  "Implementation of the service that writes events to the repository."
  (:use [clojure.core.typed]
        [cmis.event.model])
  (:require [cmis.datastore :as datastore]))

(ann-protocol AEventService
              insert [AEventService Event -> (Option EventId)])
(defprotocol> AEventService
  "A event service captures events and saves them in the repository to aid querying"

  (insert [es event] "Insert an event into the repository"))

(ann-datatype StarService
              [datasource :- String])
(deftype StarService [datasource]
  AEventService
  (insert [this event]
    (datastore/to-star-schema datasource event)))