(ns cmis.etl
  (:require  (cmis.star)
             (cmis.util)
             [cmis.etl.nagios :as nagios]
             (clj-time.core) (clj-time.coerce)
             [clojure.java.jdbc :as j]))

(defn timedimension
  [ts]
  {:ts (java.sql.Date. (clj-time.coerce/to-long ts))
   :hour (clj-time.core/hour ts)
   :minute (clj-time.core/minute ts)
   :second (clj-time.core/sec ts)
   :day_of_week (clj-time.core/day-of-week ts)
   :day_of_month (clj-time.core/day ts)
   :month (clj-time.core/month ts)
   :quarter (inc (/ (clj-time.core/month ts) 4))
   :year (clj-time.core/year ts)})

(defn site-for-hostname
  [hostname]
  (cond
   (.contains hostname "sensors.elex.be") "ieper"
   (.contains hostname "sofia.elex.be") "sofia"
   (.contains hostname "erfurt.elex.be") "erfurt"
   (.contains hostname "colo.elex.be") "diegem"
   (.contains hostname "apps.elex.be") "diegem"
   :else "unknown"))
   
(defn to-star-schema
  [ds {:keys [timestamp check unit hostname value]}]
  (let [time_id (cmis.star/slowly-changing-dimension ds :dim_time (timedimension timestamp) [:ts])
        parameter_id (cmis.star/slowly-changing-dimension ds :dim_parameter {:name check :unit unit} [:name :unit])
        ci_id (cmis.star/slowly-changing-dimension ds :dim_ci {:name hostname} [:name])
        site_id (cmis.star/slowly-changing-dimension ds :dim_site {:site (site-for-hostname hostname)} [:site])]
    (j/insert! ds :fact_measurement {:id (cmis.util/random-uuid)
                                     :time_id time_id
                                     :parameter_id parameter_id
                                     :ci_id ci_id
                                     :site_id site_id
                                     :value value})))
    

(defn parse-nagios-log
  [ds & files]
  (doall
   (for [file files]
     (let [measurements (nagios/parse file)]
       (map (partial to-star-schema ds) measurements)))))                       