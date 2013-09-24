(ns cmis.esx
  (:use [clj-xpath.core])
  (:require [clj-http.client :as http]
            [cmis.esx.core :as esx]))

(declare listDomainsOnServer)

(defrecord Domain
    [name
     state
     memory
     cpu-speed
     number-of-cpus])

(defrecord ComputeResource
    [name
     bootTime
     memory
     cpuThreads
     cpus
     cpu-speed])

(defprotocol AHypervisor
  (version [this] "Get the version of the hypervisor")
  (listDomains [this] "List all domains on this hypervisor.
                       Returns a list of Domain records.")
  (computeResources [this] "Information about the compute resources on this hypervisor"))

(def
  ^{:doc "Convertor table between the esx power state and the domain state"}
  power-state-to-state
  {
   "poweredOn" :on
   "poweredOff" :off
   "suspended"  :suspended
   })

(defn- esxdomain-to-domain
  "Translate information from esx to the standard format"  
  [domain]
  (Domain. (:name domain)
           (get power-state-to-state (:runtime.powerState domain))
           (Integer/parseInt (:runtime.maxMemoryUsage domain))
           (Integer/parseInt (:runtime.maxCpuUsage domain))
           nil))

(defn- esxcomputeResource-to-computeResource
  [cr]
  (ComputeResource. (:name cr)
                    (clj-time.coerce/from-string (:runtime.bootTime cr))
                    (Long/parseLong (:hardware.memorySize cr))
                    (Integer/parseInt (:hardware.cpuInfo.numCpuThreads cr))
                    (Integer/parseInt (:hardware.cpuInfo.numCpuPackages cr))
                    (Long/parseLong (:hardware.cpuInfo.hz cr))))
           
(defrecord EsxHypervisor [hostname username password]
  AHypervisor
  (version [this] (version hostname))
  (listDomains [this]
    (map esxdomain-to-domain
         (listDomainsOnServer hostname username password)))
  (computeResources [this]
    (map esxcomputeResource-to-computeResource
         (hostInfo hostname username password))))

(defn- listDatacenters
  [hostname ctx cookie-store]
  (esx/retrieve-properties hostname
                           (:propertyCollector ctx)
                           {:type "PropertyFilterSpec"
                            :propSet {:type "Datacenter"
                                      :properties ["hostFolder"]}
                            :objectSet {:type "Folder"
                                        :name (:rootFolder ctx)
                                        :skip false
                                        :selectSet [{:name "folderToChildEntity"
                                                     :type "Folder"
                                                     :path "childEntity"
                                                     :skip false}]
                                        }}
                           :cookie-store cookie-store))

(defn- listComputeResources
  [hostname ctx folder cookie-store]
  (esx/retrieve-properties hostname
                           (:propertyCollector ctx)
                           {:type "PropertyFilterSpec"
                            :propSet {:type "ComputeResource"
                                      :properties ["name" "host" "resourcePool"]}
                            :objectSet {:type "Folder"
                                        :name folder
                                        :skip false
                                        :selectSet [{:name "folderToChildEntity"
                                                     :type "Folder"
                                                     :path "childEntity"
                                                     :skip false}]
                                        }}
                           :cookie-store cookie-store))

(def
  ^{:doc "A list of properties to extract from the vm in vmware"}
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
                    "runtime.host"
                    ])

(defn- listVirtualMachines
  [hostname ctx folder cookie-store]
  (esx/retrieve-properties hostname
                           (:propertyCollector ctx)
                           {:type "PropertyFilterSpec"
                            :propSet {:type "VirtualMachine"
                                      :properties vm-properties}
                            :objectSet {:type "HostSystem"
                                        :name folder
                                        :skip false                                        
                                        :selectSet [{:name "hostSystemToVm"
                                                     :type "HostSystem"
                                                     :path "vm"
                                                     :skip false}]
                                        }}
                           :cookie-store cookie-store))

(defn- getHostInfo
  [hostname host ctx cookie-store]
  (esx/retrieve-properties hostname
                           (:propertyCollector ctx)
                           {:type "PropertyFilterSpec"
                            :propSet {:type "HostSystem"
                                      :properties ["name"
                                                   "runtime.bootTime"
                                                   "runtime.inMaintenanceMode"
                                                   "summary.managementServerIp"
                                                   "hardware.cpuInfo.hz"
                                                   "hardware.cpuInfo.numCpuPackages"
                                                   "hardware.cpuInfo.numCpuThreads"
                                                   "hardware.memorySize"
                                                   ]}
                            :objectSet {:type "HostSystem"
                                        :name host
                                        :skip false
                                        }
                            }
                           :cookie-store cookie-store))


(defn hostInfo
  [hostname username password]
  (esx/with-cookie-store cs
    (let [ctx (esx/retrieve-service-content hostname)
          login-ctx (esx/login hostname username password
                               (:sessionManager ctx)
                               :cookie-store cs)
          datacenters (listDatacenters hostname ctx cs)
          datacenterProperties (reduce merge {} (:propSet datacenters))]
      (map (fn [[_ v]]
                (let [compute-resource (listComputeResources hostname ctx (:value v) cs)
                      props (reduce merge {} (:propSet compute-resource))
                      host (-> props :host :value)
                      hi (getHostInfo hostname host ctx cs)]
                  (into {}
                        (map (fn [[k v]] [k (:value v)])
                             (reduce merge {} (:propSet hi))))
                  ))
              datacenterProperties))))

(defn listDomainsOnServer
  [hostname username password]
  (esx/with-cookie-store cs
    (let [ctx (esx/retrieve-service-content hostname)
          login-ctx (esx/login hostname username password
                               (:sessionManager ctx)
                               :cookie-store cs)
          datacenters (listDatacenters hostname ctx cs)
          datacenterProperties (reduce merge {} (:propSet datacenters))]
      (mapcat (fn [[_ v]]
                (let [compute-resource (listComputeResources hostname ctx (:value v) cs)
                      props (reduce merge {} (:propSet compute-resource))
                      host (-> props :host :value)
                      vms (listVirtualMachines hostname ctx host cs)
                      extract-kv (fn [[k v]] {k (:value v)})
                      properties (mapcat (partial map extract-kv) (:propSet vms))]
                  (map (partial reduce merge {})
                       (partition (count vm-properties) properties))))            
              datacenterProperties))))