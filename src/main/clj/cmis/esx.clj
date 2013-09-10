(ns cmis.esx
  (:use [clj-xpath.core])
  (:require [clj-http.client :as http]
            [cmis.esx.core :as esx]))

(defprotocol AHypervisor
  (version [this] "Get the version of the hypervisor")
  (listDomains [this] "List all domains on this hypervisor"))

(defrecord EsxHypervisor [hostname username password]
  AHypervisor
  (version [this] (version hostname))
  (listDomains [this] (listDomainsOnServer hostname username password)))

(defn- listDatacenters
  [hostname ctx]
  (esx/retrieve-properties hostname
                           (:propertyCollector ctx)
                           {:type "PropertyFilterSpec"
                            :propSet {:type "Datacenter"
                                      :properties ["hostFolder"]}
                            :objectSet {:type "Folder"
                                        :name (:rootFolder ctx)
                                        :skip false
                                        :selectSet {:name "folderToChildEntity"
                                                    :type "Folder"
                                                    :path "childEntity"
                                                    :skip false}}}))

(defn- listComputeResources
  [hostname ctx folder]
  (esx/retrieve-properties hostname
                           (:propertyCollector ctx)
                           {:type "PropertyFilterSpec"
                            :propSet {:type "ComputeResource"
                                      :properties ["name" "host" "resourcePool"]}
                            :objectSet {:type "Folder"
                                        :name folder
                                        :skip false
                                        :selectSet {:name "folderToChildEntity"
                                                    :type "Folder"
                                                    :path "childEntity"
                                                    :skip false}}}))

(def
  "A list of properties to extract from the vm in vmware"
  vm-properties [
                    "name"
                    "config.memoryAllocation.limit"
                    "config.memoryAllocation.reservation"
                    "config.cpuAllocation.limit"
                    "config.cpuAllocation.reservation"
                    "summary.quickStats.guestMemoryUsage"
                    "summary.quickStats.overallCpuUsage"
                    "runtime.bootTime"
                    "runtime.maxCpuUsage"
                    "runtime.maxMemoryUsage"
                    "runtime.powerState"
                    ])

(defn- listVirtualMachines
  [hostname ctx folder]
  (esx/retrieve-properties hostname
                           (:propertyCollector ctx)
                           {:type "PropertyFilterSpec"
                            :propSet {:type "VirtualMachine"
                                      :properties vm-properties}
                            :objectSet {:type "HostSystem"
                                        :name folder
                                        :skip false                                        
                                        :selectSet {:name "hostSystemToVm"
                                                    :type "HostSystem"
                                                    :path "vm"
                                                    :skip false}}}))


(defn listDomainsOnServer
  [hostname username password]
  (let [ctx (esx/retrieve-service-content hostname)
        login-ctx (esx/login hostname username password (:sessionManager ctx))
        datacenters (listDatacenters hostname ctx)
        datacenterProperties (reduce merge {} (:propSet datacenters))]
    (mapcat (fn [[_ v]]
              (let [compute-resource (listComputeResources hostname ctx (:value v))
                    props (reduce merge {} (:propSet compute-resource))
                    host (-> props :host :value)
                    vms (listVirtualMachines hostname ctx host)
                    extract-kv (fn [[k v]] {k (:value v)})
                    properties (mapcat (partial map extract-kv) (:propSet vms))]
                (map (partial reduce merge {})
                     (partition (count vm-properties) properties))))            
            datacenterProperties)))                             