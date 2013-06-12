(ns cmis.web
  (:use hiccup.core
        hiccup.form
        hiccup.page
        compojure.core
        compojure.handler)
  (:require [compojure.route :as route]
            [ring.util.response :as response]
            [clojure.java.jdbc :as j]
            [clojure.data.json :as json]))

(def ds {:subprotocol "postgresql"
         :classname "org.postgresql.Driver"            
         :subname "//localhost/cmis"
         :user "cmis"
         :password "cmis"})

(def pages (atom {"io-write-latency" {:title "IO write latency"
                                      :query "select dc.name, fm.val, dc.unit from fact_measurement fm inner join dim_check dc on fm.check_id = dc.id inner join dim_env de on fm.env_id = de.id where de.environment = 'production' and dc.name = 'io write latency' order by dc.name, fm.val desc limit 10"
                                      :fields ["name"
                                               ["value" :val]
                                               :unit
                                               ]}}))


(def css
  ["/css/bootstrap.min.css"])

(def javascript
  ["http://code.jquery.com/jquery.js"
   "/js/bootstrap.min.js"])

(defn layout [& content]
  (html5
   [:head
    [:title "cmis"]
    (map include-css css)]
   [:body
    [:div {:class "navbar navbar-inverse"}
     [:div {:class "navbar-inner"}
      [:div {:class "container"}
       [:a {:class "brand" :href "/"} "CMIS"]
       [:ul {:class "nav"}        
        [:li [:a {:href "/p"} "Pages"]]
        [:li [:a {:href "/p/add"} "Add page"]]
       ]]]]
    [:div {:class "container-fluid"}
     [:div {:class "row-fluid"}
      [:div {:class "span2"}]
      [:div {:class "span10"}
       content
       ]
     ]]
    (map include-js javascript)
    ]
   ))

(defn wiki-layout [title & content]
  (layout
       [:h3 title]
       content))

(def add-page
  (wiki-layout "Create page"
   (form-to [:post "/p/add"]
            (label "title" "Title")
            (text-field "title")
            (label "id" "Identifier")
            (text-field "id")
            (label "fields" "Fields")
            (text-field "fields")
            (label "query" "Query")
            (text-area "query")
            (submit-button "Create")
            )))

(defn column-name
  [c]
  (if (or (vector? c) (list? c))
    (let [[_ col] c]
      col)
    c))

(defn column-alias
  [c]
  (if (or (vector? c) (list? c))
    (let [[alias _] c]
      alias)
      c))

(defn query-page
  [{{:keys [id]} :params}]
  (let [{:keys [title query fields]} (get @pages id)
        results (j/query ds [query])]
    (wiki-layout title
                 (if (nil? results)
                   [:p "No results found"]
                   [:table {:class "table"}
                    [:th
                     (for [k fields]
                       [:td (name (column-alias k))])]
                    (for [result results]
                      [:tr
                       [:td
                        (for [k fields]
                          (let [k* (column-name k)
                                k** (keyword k*)
                                v (get result k**)]
                            [:td v]))]])])
                   
                 )))
   
(defn pages-page
  [_]
  (wiki-layout "Page list"
   [:ul
    (for [[id page] @pages]
      [:li [:a {:href (str "/p/" id)} (:title page)]])]
   [:a {:href "/p/add"} "add"]))

(defn create-page! [title id query fields]
  (swap! pages assoc id {:title title :query query :fields fields})
  (println @pages))

(defn intro-page
  [_]
  (layout 
   [:div {:class "hero-unit"}
    [:h1 "CMIS"]
    [:p "Cmis is the capacity management information system."]]))                

(def handler
  (site
   (defroutes urls
     (POST "/p/add" {{:keys [title id query fields]} :params}
           (let [fields* (json/read-str fields)]
             (create-page! title id query fields*)
             (response/redirect "/p")
             )
           )
     (GET "/p/add" [] add-page)
     (GET "/p/:id" [] query-page)
     (GET "/p" [] pages-page)
     (GET "/" [] intro-page)
     (route/resources "/"))))

    
  