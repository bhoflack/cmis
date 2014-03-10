(ns cmis.cmdb
  (:use net.cgrand.enlive-html))

(defn id-contains?
  [expr]
  (pred (fn [el]
          (let [id (-> el :attrs :id)]
            (if (not (nil? id))
              (.contains id expr))))))

(defn html-id-like
  [resource id]
  (->>
   (-> resource
       (select [:body :div#content-core (id-contains? id) :span :> text-node])
       rest)
   (clojure.string/join "\n")
   (clojure.string/trim)))

(defn parse-product-page
  "Extract the product data from the product page."
  [^java.io.InputStream is]
  (let [res (-> is html-resource)]
    {:background  (html-id-like res "Background")
     :functionality (html-id-like res "Functionality")
                                        ;     :critical_level (Integer/parseInt (html-id-like res "CriticalLevel"))
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