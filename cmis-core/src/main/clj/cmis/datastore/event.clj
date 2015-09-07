(ns cmis.datastore.event
  (:require [cmis.util :as util]            
            [cmis.datastore :refer [timedimension]]
            [star.core :as star]
            [clojure.tools.logging :as log])
  (:gen-class))

(defprotocol AEventDatastore
  (save [this measurement]
    "Save an Event to the datastore")
  (slowly-changing-dimension [this table values keys identifiers]
    "Add an entry to a slowly changing dimension"))
    
(defn site-for-hostname
  [hostname]
  (cond
   (.contains hostname "sensors.elex.be") "ieper"
   (.contains hostname "sofia.elex.be") "sofia"
   (.contains hostname "erfurt.elex.be") "erfurt"
   (.contains hostname "colo.elex.be") "diegem"
   (.contains hostname "apps.elex.be") "diegem"
   (.contains hostname "kiev.elex.be") "kiev"
   (.contains hostname "tess.elex.be") "tessenderlo"
   (partial re-matches #"[\w-]*.elex.be") "diegem"
   :else "unknown"))

(defmulti to-star-schema
  (fn [_ val] (set (keys val))))

(defmethod to-star-schema #{:timestamp :name :event :unit :value :environment :labels}
  [ds {:keys [timestamp event unit name value environment labels]}]
  (let [time_id (star/slowly-changing-dimension ds
                                                :dim_time
                                                (timedimension timestamp)
                                                [:timestamp] [:timestamp])
        event_id (star/slowly-changing-dimension ds
                                                 :dim_event
                                                 {:name event :unit unit}
                                                 [:name :unit] [:name :unit])
        environment_id (star/slowly-changing-dimension ds
                                                       :dim_environment
                                                       {:environment environment}
                                                       [:environment] [:environment])
        ci_id (star/slowly-changing-dimension ds
                                              :dim_ci
                                              {:name name}
                                              [:name] [:name])
        id (util/random-uuid)
        fact {:id id
              :time_id time_id
              :event_id event_id
              :ci_id ci_id
              :environment_id environment_id
              :value value}]
    (log/debug "Saving fact " fact)    
    (star/insert-into-facttable ds :fact_measurement fact)
    id))

(defmethod to-star-schema #{:timestamp :ci_id :event :unit :value :environment :labels :name}
  [ds {:keys [timestamp ci_id event unit value environment labels]}]
  (let [time_id (star/slowly-changing-dimension ds
                                                :dim_time
                                                (timedimension timestamp) [:timestamp] [:timestamp])
        event_id (star/slowly-changing-dimension ds
                                                 :dim_event
                                                 {:name event :unit unit}
                                                 [:name :unit] [:name :unit])
        environment_id (star/slowly-changing-dimension ds
                                                       :dim_environment
                                                       {:environment environment}
                                                       [:environment] [:environment])
        id (util/random-uuid)]
    (star/insert-into-facttable ds :fact_measurement {:id id
                                                      :time_id time_id
                                                      :event_id event_id
                                                      :ci_id ci_id
                                                      :environment_id environment_id
                                                      :value value
                                                      })
    id))

(defrecord EventDatastore [ds]
  AEventDatastore
  (save [this result] (to-star-schema ds result))
  (slowly-changing-dimension [_ table values keys identifiers]
    (star/slowly-changing-dimension ds table values keys identifiers)))
