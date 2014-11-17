(ns cmis.libvirt
  (import [org.libvirt Connect ConnectAuthPasswordProvided DomainInfo$DomainState]))

(def domain-state
  { DomainInfo$DomainState/VIR_DOMAIN_BLOCKED  :blocked
    DomainInfo$DomainState/VIR_DOMAIN_CRASHED  :crashed
    DomainInfo$DomainState/VIR_DOMAIN_NOSTATE  :no-state
    DomainInfo$DomainState/VIR_DOMAIN_PAUSED   :paused
    DomainInfo$DomainState/VIR_DOMAIN_RUNNING  :running
    DomainInfo$DomainState/VIR_DOMAIN_SHUTDOWN :shutdown
    DomainInfo$DomainState/VIR_DOMAIN_SHUTOFF  :shutoff })

(defn connect
  "Connect to a libvirt server"
  [uri & {:keys [password]
          :or {:password ""}}]
  (Connect. uri (ConnectAuthPasswordProvided. password) 0))

(defmacro with-connection
  [uri password connection & body]
  `(let [c# (connect ~uri :password ~password)
         ~connection c#]
     (try
       ~@body
       (finally (.close c#)))
     ))

(defn domain-to-clj
  [domain]
  {:name (.getName domain)
   :os_type (.getOSType domain)
   :state (get domain-state (.state (.getInfo domain)))
   :memory (.memory (.getInfo domain))
   :nr_cpus (.nrVirtCpu (.getInfo domain))
   })

(defn list-domains
  "List all domains on the server"
  [c]
  (doall
   (map (fn [dom]
         (domain-to-clj (.domainLookupByID c dom)))
       (.listDomains c))))
  
(defn node-info
  "Information about the host server"
  [c]
  (let [ni (.nodeInfo c)]
    {:hostname (.getHostName c)
     :uri (.getURI c)
     :model (.model ni)
     :cores (.cores ni)
     :cpus  (.cpus ni)
     :cpu_freq (.mhz ni)
     :numa_cells (.nodes ni)
     :sockets (.sockets ni)
     :threads_per_core (.threads ni)
     :memory (.memory ni)
     :hypervisor (.getType c)
     :hypervisor_version (.getVersion c)
     :storage_pools (seq (.listStoragePools c))
     :interfaces (seq (.listInterfaces c))
     :networks (seq (.listNetworks c))
     }))