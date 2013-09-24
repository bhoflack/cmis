(ns cmis.daemon
  (:import [cmis.esx EsxHypervisor])
  (:require [cmis
             [esx :as esx]
             [nagios :as nagios]
             [star :as star]]
            [clojure.tools.logging :as log]
            ))

(defn each-vmware-server
  [nagios f]
  (let [vmware-servers (.withHostGroup nagios "vmesx_hosts")
        vmware-servers* (map :host_name vmware-servers)]
    (map f vmware-servers*)))

(defn save-host-info-to-*-schema
  [ds host-info]
  (let [host-info* (dissoc host-info :domains :interfaces :networks :storage_pools)        
        parent-uuid (star/slowly-changing-dimension ds
                                                    :dim_ci
                                                    host-info*
                                                    [:hostname :model :cpus :cpu_freq :sockets
                                                     :numa_cells :threads_per_core :memory
                                                     :hypervisor :hypervisor_version]
                                                    [:hostname])]
    (doall (for [domain (:domains host-info)]
      (let [domain-info {:hostname (:name domain)
                         :parent_id parent-uuid
                         :memory (:memory domain)
                         :cpus (:nr_cpus domain)
                         :state (name (:state domain))}]
        (star/slowly-changing-dimension ds
                                        :dim_ci
                                        domain-info
                                        [:hostname :parent_id :memory :cpus :state]
                                        [:hostname]
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
        host-info (map (fn [hostname] (future (try
                                                (.listDomains (EsxHypervisor. hostname "esxnagios" "123Qweasdzxc"))
                                                (catch Exception e
                                                  (log/warn "Exception while listing domains for " hostname ": " (.getMessage e)))))) vmware-servers*)]
    
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

  