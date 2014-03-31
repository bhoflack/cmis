(ns cmis.datastore.ciapplication
    (:require [cmis
             [star :as star]
             [util :as util]]
            [cmis.datastore :refer [timedimension]]
            [clojure.tools.logging :as log])
  (:gen-class))

(defprotocol ACiApplicationDatastore
  (save [this ci-application-event]
    "Save a ci application event
 
     A ci-application-event is a map with as fields:
       - timestamp      - a DateTime
       - applicationid  - the uuid containing the application id
       - measurementid  - the uuid containing the measurement id
       - hostname       - the string containing the hostname of the ci application event"))

(deftype CiApplicationDatastore [ds]
  ACiApplicationDatastore
  (save [_ {:keys [timestamp applicationid measurementid hostname]}]
    (log/debug "Saving application event 1")
    (let [timeid (star/slowly-changing-dimension ds :dim_time (timedimension timestamp) [:ts] [:ts])
          hostid (star/slowly-changing-dimension ds :dim_ci {:name hostname} [:name] [:name])
          id (util/random-uuid)]
      (log/debug "Saving application event")      
      (star/insert ds
                   :fact_host_application
                   {:id id
                    :measurement_id measurementid
                    :application_id applicationid
                    :ci_id hostid
                    :time_id timeid})
      id))
  )    