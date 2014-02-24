(ns cmis.datastore
  (:require [cmis
             [star :as star]
             [util :as util]]
            [clojure.tools.logging :as log])
  (:gen-class))

(defprotocol ADataStore
  (save [this result]
    "Save a result to the datastore")
  (slowly-changing-dimension [_ table values keys identifiers]
    "Add an entry to a slowly changing dimension"))

(defn timedimension
  [ts]
  {:timestamp (java.sql.Date. (clj-time.coerce/to-long ts))
   :hour (clj-time.core/hour ts)
   :minute (clj-time.core/minute ts)
   :second (clj-time.core/sec ts)
   :day_of_week (clj-time.core/day-of-week ts)
   :day_of_month (clj-time.core/day ts)
   :month (clj-time.core/month ts)
   :quarter (inc (.intValue (/ (clj-time.core/month ts) 4)))
   :year (clj-time.core/year ts)})

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
    (star/insert ds :fact_measurement fact)
    id))

(defmethod to-star-schema #{:timestamp :ci_id :check :hostname :unit :value}
  [ds {:keys [timestamp check unit hostname value ci_id]}]
  (let [time_id (star/slowly-changing-dimension ds :dim_time (timedimension timestamp) [:ts] [:ts])
        parameter_id (star/slowly-changing-dimension ds :dim_parameter {:name check :unit unit} [:name :unit] [:name :unit])
        site_id (star/slowly-changing-dimension ds :dim_site {:site (site-for-hostname hostname)} [:site] [:site])
        id (util/random-uuid)]
    (star/insert ds :fact_measurement {:id id
                                       :time_id time_id
                                       :parameter_id parameter_id
                                       :ci_id ci_id
                                       :site_id site_id
                                       :value value
                                       })
    id))

(defrecord StarDataStore [ds]
  ADataStore
  (save [this result] (to-star-schema ds result))
  (slowly-changing-dimension [_ table values keys identifiers]
    (star/slowly-changing-dimension ds table values keys identifiers)))