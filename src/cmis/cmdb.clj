(ns cmis.cmdb
  (:require [net.cgrand.enlive-html :as html]))

(def products-url "http://cmdb.elex.be/collections/procedures/collections/products")

(defn fetch-url [url]
  (html/html-resource (java.net.URL. url)))

(defn product-list
  []
  (let [pages (distinct (map #(get-in % [:attrs :href]) (html/select (fetch-url products-url) [:div.listingBar :a])))
        pages* (conj pages products-url)]
    (mapcat (fn [url]
              (map (fn [el] [(first (:content el)) (get-in el [:attrs :href])]) (html/select (fetch-url url) [:a.contenttype-ci]))) pages*)))

(defn product-detail
  [url]
  (let [p (fetch-url url)]
    {:dependencies (map (fn [el] (first (:content el)))
                        (html/select p
                                     [(html/attr-starts :id "archetypes-fieldname-DependsON") :ul :li :a]))
     :version (html/select p [(html/attr-starts :id "parent-fieldname-version" html/content)])
     :instances (html/select p
                             [(html/attr-starts :id "archetypes-fieldname-InstalledInstances") :> :span html/content])}))
    
(defn deployed-instances
  [url]
  (html/select (fetch-url url)
               [(html/attr-starts :id "archetypes-fieldname-InstalledInstances") :> :span html/content]))

(defn products
  []
  (map (fn [[product url]]
         (assoc (product-detail url) :name product))
       (product-list)))
