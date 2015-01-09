(ns cmis-dashboard.pages
  (:require cmis-dashboard.service
            cmis-statistics.core
            ring.util.codec)
  (:use hiccup.core))

(defn linear-regression-graph
  [ds ci event hostname]  
  (if-let [graph (some->> (cmis-dashboard.service/find-all-measurements ds ci event hostname)
                          (cmis-statistics.core/data-to-matrix)
                          (cmis-statistics.core/linear-model-graph)
                          (java.io.ByteArrayInputStream.))]
    graph
    {:status 404
     :body "Graph not found for specified ci and event"}))

(defn linear-regression-page
  [ds ci]
  (let [events (cmis-dashboard.service/events-for-ci ds ci)
        hosts (cmis-dashboard.service/instances-for-ci ds ci)]
    (html
     [:head
      [:title (str "Linear regressions for " ci)]]
     [:body
      [:ul 
       (for [event events]
         [:li [:h3 event]
          [:ul
           (for [host hosts]
             (let [args (ring.util.codec/form-encode {:ci ci :event event :hostname host})]
               [:li 
                [:h4 host]
                [:img {:src (str "/reports/linear-regression/graphs/generate?" args)}]]))]])]])))

