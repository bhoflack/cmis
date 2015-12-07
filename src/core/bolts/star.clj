(ns core.bolts.star
  (:require [backtype.storm.clojure :refer :all]
            [core.util :refer [random-uuid]]
            [star.core :as star]))

(defn timedimension
  [ts]
  {:timestamp (java.sql.Date. (clj-time.coerce/to-long ts))
   :hour (clj-time.core/hour ts)
   :minute (clj-time.core/minute ts)
   :second (clj-time.core/second ts)
   :day_of_week (clj-time.core/day-of-week ts)
   :day_of_month (clj-time.core/day ts)
   :month (clj-time.core/month ts)
   :quarter (inc (.intValue (/ (clj-time.core/month ts) 4)))
   :year (clj-time.core/year ts)})

(defn write-to-star-schema
  [ds timestamp event environment name value unit]
  (let [time_id (star/slowly-changing-dimension ds
                                                :dim_time
                                                (timedimension timestamp)
                                                [:timestamp]
                                                [:timestamp])
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
        measurement_id (random-uuid)]
    (star/insert-into-facttable ds :fact_measurement {:id measurement_id
                                                      :time_id time_id
                                                      :event_id event_id
                                                      :ci_id ci_id
                                                      :environment_id environment_id
                                                      :value value})))

; Write measurements to a star schema
(defbolt to-star-schema [] {:params [ds]}
  [{measurement "measurement" type "type" ci "ci" :as tuple} collector]
  (println "Got tuple " tuple)
  (let [{timestamp "timestamp" hostname "hostname" unit "unit" event "event" value "measurement"} measurement]
    (write-to-star-schema ds timestamp event "prod" hostname value unit)))

