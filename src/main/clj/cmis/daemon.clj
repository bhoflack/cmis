(ns cmis.daemon
  (:require [cmis
             [esx :as esx]
             [nagios :as nagios]
             [star :as star]])
  )

(defn each-vmware-server
  [nagios f]
  (let [vmware-servers (.withHostGroup nagios "vmesx_hosts")
        vmware-servers* (map :host_name vmware-servers)]
    (map f vmware-servers*)))

(defn extract-host-info
  [hostname]
  (let [uri (format "esx://esxnagios@%s/?no_verify=1" hostname)]
    (libvirt/with-connection uri "123Qweasdzxc" c
      (assoc (libvirt/node-info c)
        :domains (libvirt/list-domains c)))))

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

(defn extract-and-save
  [ds hostname]
  (let [host-info (extract-host-info hostname)]
    (save-host-info-to-*-schema ds host-info)))
        
         
(defn import-vmware-info
  [ds nagios]  
  (doall (each-vmware-server nagios (partial extract-and-save ds))))

(defn daemon
  [ds nagios]
  (loop []
    (.update nagios) ; do a cvs update
    (import-vmware-info ds nagios)
    (Thread/sleep (* 1000
                     60
                     10))
    (recur)))

  