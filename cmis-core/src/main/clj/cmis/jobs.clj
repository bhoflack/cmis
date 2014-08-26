(ns cmis.jobs  
  (:require [clojurewerkz.quartzite.jobs :refer [defjob]]
            [clojurewerkz.quartzite.conversion :as qc]
            [cmis.cmdb :as cmdb]
            [cmis.service.product :as product]
            [cmis.check.nagios :refer [import-events]])
  (:require [clojure.tools.logging :as log]))

(defn from-context
  [ctx k]  
  (let [k* (name k)]
    (-> ctx
        (qc/from-job-data)
        (get k*))))

(defjob
  ^{:doc "A job for importing the cmdb data to the database"}
  CmdbImport [ctx]
  (log/info "Starting job CmdbImport")
  (let [ps (from-context ctx :productservice)]
    (assert (not (nil? ps)))
    (some-> "http://cmdb.elex.be/products/"
            (cmdb/find-product-information)
            (->> (map (partial product/put ps)))
            (doall))))

(defjob
  ^{:doc "A job for importing the nagios data to the database"}
  NagiosImport [ctx]
  (log/info "Starting job NagiosImport")
  (let [idempotent (from-context ctx :idempotent)
        event-service (from-context ctx :event-service)]
    (import-events idempotent event-service)))