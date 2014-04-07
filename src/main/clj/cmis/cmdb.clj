(ns cmis.cmdb
  ^{:doc "Functions for reading the cmdb"}    
  (:use net.cgrand.enlive-html)
  (:require [cmis.util :refer (uri->stream)]))

(defn id-contains?
  [expr]
  (pred (fn [el]
          (let [id (-> el :attrs :id)]
            (if (not (nil? id))
              (.contains id expr))))))

(defn- extract
  [resource selectors]
  (->>
   (-> resource
       (select selectors)
       rest)
   (clojure.string/join "\n")
   (clojure.string/trim)))

(defn html-id-like
  [resource id]
  (extract resource [:body :div#content-core (id-contains? id) :span :> text-node]))

(defn extract-critical-level
  [resource id]
  (extract resource [:body :div#content-core (id-contains? id) :> text-node]))

(defn parse-product-page
  "Extract the product data from the product page.

   Parameters
     - is - An inputstream containing the product page

   Returns a hash containing:
     :name - the string containing the product name
     :background - the string containing the background of the product
     :functionality - the string containing the functionality of the product
     :critical_level - the integer containing the critical_level of the service ( higher is more critical )
     :business_owner - the string containing the business owner
     :development - the string containing the development responsible
     :ops_support - the string containing the ops support responsible
     :users - the string containing the identified users
     :version - the string containing the version
     :cfengine_classes - the string containing the cfengine classes
     :nagios_classes - the string containing the nagios classes
     :installed_instances - the seq containing the strings of the installed instances"
  [^java.io.InputStream is]
  (let [res (-> is html-resource)]
    {:name (clojure.string/trim
            (-> res
                (select [:body :h1#parent-fieldname-title :> text-node])
                first))
     :background  (clojure.string/replace (html-id-like res "Background") "\n" "")
     :functionality (html-id-like res "Functionality")
     :critical_level (Integer/parseInt (extract-critical-level res "CriticalLevel"))
     :business_owner (html-id-like res "BusinessOwner")
     :development (html-id-like res "Development")
     :ops_support (html-id-like res "OpsSupport")
     :users (html-id-like res "Users")
     :version (html-id-like res "version")
     :cfengine_classes (html-id-like res "CfengineClasses")
     :nagios_classes (html-id-like res "NagiosClasses")
     :installed_instances (-> (html-id-like res "InstalledInstances")
                              (clojure.string/split #"\n"))
     }))

(defn- extract-name-and-url
  [a]
  {:uri (some-> a
                (get-in [:attrs :href])
                (java.net.URI.))
   :name (first (get a :content))})

(defn parse-product-list-page
  "Extract the different products and links in a product list page

   Parameters
     - is - An inputstream containing the product list page

   Returns a hash containing
     - next-url a string containing the next url
     - products a seq of hashes containing:
       - name - a string containing the product name
       - uri - a java.net.URI containing the url of the product page"
  [^java.io.InputStream is]
  (let [res (html-resource is)]
    {:next-url (some-> res
                   (select [:div.listingBar :span.next :a])
                   (->> (map #(get-in % [:attrs :href])))
                   (first)
                   (java.net.URI.))
     :products (-> res
                   (select [:dt :span.summary :a.contenttype-ci])
                   (->> (map (partial extract-name-and-url))))}))

(defn list-products
  "List all products on the cmdb

   Parameters
     - is - An inputstream containing the first product page

   Returns a list of hashes containing
     - name - a string containing the product name
     - uri - a java.net.URI containing the product link"
  [^java.io.InputStream basepageis]
  (loop [is basepageis
         products {}]
    (if (nil? is)
        products
        (let [plp (-> is (parse-product-list-page))]
          (recur (some-> plp
                         (:next-url)
                         (uri->stream))
                 (concat products (:products plp)))))))

(defn find-product-information
  "Create a list with the product information for all products"
  [baseurl]
  (some-> baseurl
          (java.net.URL.)
          (.openStream)
          (list-products)
          (->> (map :uri))
          (->> (map uri->stream))
          (->> (map parse-product-page))))
