(ns cmis.cmdb
  (:use net.cgrand.enlive-html))

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
  "Extract the product data from the product page."
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