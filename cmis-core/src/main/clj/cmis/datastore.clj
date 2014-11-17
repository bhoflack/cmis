(ns cmis.datastore
  (:require [cmis
             [star :as star]
             [util :as util]]
            [clojure.tools.logging :as log])
  (:gen-class))

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
