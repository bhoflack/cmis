(ns cmis.service.product
  (:require [cmis.star :as star]))

(defprotocol AProductService
  (put [ps prod]))

(deftype ProductService
    [ds]
  AProductService
  (put [_ product]
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

      (doall
       (for [instance (:installed_instances product)]
         (star/slowly-changing-dimension ds
                                         :temp_apps_for_host
                                         {:application_id appid
                                          :hostname instance}
                                         [:hostname]
                                         [:hostname :application_id])))
      )))
                                                   