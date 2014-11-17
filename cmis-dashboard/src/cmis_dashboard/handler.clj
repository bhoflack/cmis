(ns cmis-dashboard.handler
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [clojure.data.json :as json]
            [cmis-dashboard.service :as cmis]
            ))

(def ds (atom nil))

(defroutes app-routes
  (context "/cis" []
           (defroutes cis-routes
             (GET "/" []
                  (-> @ds
                      (cmis/find-all-products)
                      (json/write-str)))
             (GET "/product" {{name :name} :params}
                  (let [product (some-> @ds
                                        (cmis/find-ci-with-name name)
                                        (cmis/transform-measurements))]
                    (if (nil? product)
                      {:status 404 :headers {}}
                      (json/write-str product))))))

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