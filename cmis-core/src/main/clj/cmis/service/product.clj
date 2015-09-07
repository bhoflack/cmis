(ns cmis.service.product
  ^{:doc "Services related to products"}
  (:require [star.core :as star]
            [cmis.util :refer [string->uuid]]
            [clojure.java.jdbc :as jdbc]
            [honeysql.core :as sql]
            [clojure.tools.logging :as log]))

(defprotocol AProductService
  (put [ps prod] "Put a product in the product service")
  (products-for-hostname [ps hostname] "Get a list of all product ci uuid's for the given hostname"))

(deftype ProductService [ds]
  AProductService
  (put [_ product]
    (assert product)
    (log/info "Putting product " product)
    (let [product* (dissoc product :installed_instances)
          appid (star/slowly-changing-dimension ds
                                                :dim_ci
                                                product*
                                                [:name]
                                                [:background
                                                 :ops_support
                                                 :users
                                                 :nagios_classes
                                                 :critical_level
                                                 :version
                                                 :development
                                                 :functionality
                                                 :cfengine_classes
                                                 :business_owner])]

      (doseq [instance (:installed_instances product)]        
        (star/slowly-changing-dimension ds
                                        :temp_apps_for_host
                                        {:application_id appid
                                         :hostname instance}
                                        [:hostname]
                                        [:hostname :application_id]))
      appid
      ))

  (products-for-hostname [_ hostname]
    (some-> ds
            (jdbc/query
             (sql/format {:select [:application_id]
                          :from [:temp_apps_for_host]
                          :where [:= :hostname hostname]}))
            (->> (map :application_id))
            (->> (map string->uuid)))
    ))
