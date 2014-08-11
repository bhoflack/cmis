(ns cmis.event.model
  "The model for an event."
  (:use [clojure.core.typed]))

(def-alias Label String)
(def-alias Event (HMap :mandatory {:timestamp   org.joda.time.DateTime
                                   :event       String
                                   :unit        String
                                   :value       Number
                                   :name        String
                                   :environment String
                                   :labels      (Seq Label)}))
(def-alias EventId java.util.UUID)

(ann create-event [org.joda.time.DateTime String String Number String String (Seq Label) -> Event])
(defn create-event
  "Create an event"
  [^org.joda.time.DateTime timestamp event unit value hostname environment labels]
  {:timestamp timestamp
   :event event
   :unit unit
   :value value
   :name hostname
   :environment environment
   :labels labels})
  