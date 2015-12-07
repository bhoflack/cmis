(ns core.handler
  (:require [core.ci :as ci]))

(defn add-ci [ci-service req]
  (let [hostname (get-in req [:body :hostname])
        memory (get-in req [:body :memory])
        cpus (get-in req [:body :cpus])]
    (if (or (nil? hostname)
            (nil? memory)
            (nil? cpus))
      {:status 400
       :headers {"Content-Type" "text/plain"}
       :body "Parameters hostname, memory and cpus are required"}
      {:status 200
       :body (ci/add ci-service
                     (ci/Ci. hostname
                             memory
                             cpus))})))

(defn add-event [event-service req]
  (let [hostname (get-in req [:body :hostname])
        metric (get-in req [:body :metric])
        service (get-in req [:body :service])
        timestamp (get-in req [:body :timestamp])]
    (if (or (nil? hostname)
            (nil? metric)
            (nil? service)
            (nil? timestamp))
      {:status 400
       :headers {"Content-Type" "text/plain"}
       :body "Parameters hostname, metric, service and timestamp are required."}
      {:status 200
       :body (event/add event-service
                        (event/Event. hostname
                                      metric
                                      service
                                      timestamp))})))

(defn add-service [service-repository req]
  (let [name (get-in req [:body :name])
        unit (get-in req [:body :unit])]
    (if (or (nil? name) (nil? unit))
      {:status 400
       :headers {"Content-Type" "text/plain"}
       :body "Parameters name and unit are required."}
      {:status 200
       :body (service/add service-repository
                          (service/Service. name unit))})))
