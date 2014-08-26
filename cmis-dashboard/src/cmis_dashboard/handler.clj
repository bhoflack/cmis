(ns cmis-dashboard.handler
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [clojure.data.json :as json]
            [cmis-dashboard.service :as cmis]
            ))

(def ds (atom nil))

(defroutes app-routes
  (context "/cis/" []
           (defroutes cis-routes
             (GET "/" []
                  (-> @ds
                      (cmis/get-all-cis)
                      (cmis/transform)
                      (json/write-str)))))
  (route/resources "")
  (route/resources "/")
  (route/not-found "Not Found"))

(defn wrap-dir-index [handler]
  (fn [req]
    (handler
     (update-in req [:uri]
                #(if (= "/" %) "/index.html" %)))))

(def app
  (handler/site (-> app-routes
                    wrap-dir-index)))
