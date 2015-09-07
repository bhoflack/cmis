(ns cmis.daemon
  (:import [cmis.esx EsxHypervisor])
  (:require [cmis
             [esx :as esx]
             [nagios :as nagios]]
            [star.core :as star]
            [clojure.tools.logging :as log]))

(defn each-vmware-server
  [nagios f]
  (let [vmware-servers (.withHostGroup nagios "vmesx_hosts")
        vmware-servers* (map :host_name vmware-servers)]
    (map f vmware-servers*)))

(defn save-host-info-to-*-schema
  [ds hypervisor]    
  (let [host-info (first (.computeResources hypervisor))
        parent-uuid (star/slowly-changing-dimension ds
                                                    :dim_ci
                                                    host-info
                                                    [:name
                                                     :bootTime
                                                     :memory
                                                     :cpuThreads
                                                     :cpus
                                                     :cpuSpeed]
                                                    [:name :memory :cpus :cpuSpeed :cpuThreads])]
    (doall (for [domain (.listDomains hypervisor)]
             (let [domain* (assoc domain
                             :cpus (/ (:cpuSpeed domain) (:cpuSpeed host-info))
                             :parent_id parent-uuid
                             :state (.toString (:state domain)))]
               (star/slowly-changing-dimension ds
                                               :dim_ci
                                               domain*
                                               [:name
                                                :state
                                                :memory
                                                :cpuSpeed
                                                :numberOfCpus]
                                               [:name :state :memory :cpuSpeed :numberOfCpus]
                                               ))))))

(defn extract-host-info
  [hostname]
  (let [vmware (EsxHypervisor. hostname "esxnagios" "123Qweasdzxc")]
    (.listDomains vmware)))

(defn extract-and-save
  [ds hostname]
  (let [host-info (extract-host-info hostname)]
    (save-host-info-to-*-schema ds host-info)))
                 
(defn import-vmware-info
  [ds nagios]
  (let [vmware-servers (.withHostGroup nagios "vmesx_hosts")
        vmware-servers* (map :host_name vmware-servers)
        host-info (map (fn [hostname]
                         (future
                           (try
                             (save-host-info-to-*-schema ds (EsxHypervisor. hostname "esxnagios" "123Qweasdzxc"))
                             (catch Exception e
                               (log/warn "Exception while listing domains for "
                                         hostname ": " (.getMessage e))))))
                       vmware-servers*)]
    
    host-info))

(defn daemon
  [ds nagios]
  (loop []
    (.update nagios) ; do a cvs update
    (import-vmware-info ds nagios)
    (Thread/sleep (* 1000
                     60
                     10))
    (recur)))

  
