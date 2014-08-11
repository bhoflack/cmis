(ns cmis.service.ci
  (:require [clojure.java.jdbc :as jdbc]
            [cmis.star :as star]
            [cmis.util :as u]))

(defprotocol ACiService
  (insert [cis ci] "Insert a CI to the datasource."))

(deftype CiService [ds]
  ACiService
  (insert [this ci]
    (star/slowly-changing-dimension ds
                                    :dim_ci
                                    ci
                                    [:name :cpu :num_cpus :memory :parent]
                                    [:name])))