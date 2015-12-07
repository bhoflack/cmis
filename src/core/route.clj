(ns core.route
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [core.handler :refer [add-event add-service]]))

(defroutes app
  (POST "/event" [] add-event)
  (POST "/service" [] add-service)
  (route/not-found "Endpoint not found"))
