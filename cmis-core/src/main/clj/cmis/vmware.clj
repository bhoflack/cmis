(ns cmis.nagios
  "Use the vmware vSphere management SDK to get information from a vmware host.

   For more information: http://communities.vmware.com/community/vmtn/developer/forums/managementapi
  "
  
  (:import [vmware.vim25 ManagedObjectReference VimService]))

(defn managed-object-reference
  [type value]
  (let [r (ManagedObjectReference.)]
    (.setType r type)
    (.setValue r value)
    r))

(defn vim-service
  ([]
     (let [vim-service (VimService.)]
       (.getVimPort vim-service)))
  ([url]
     (let [vim-service (VimService. url)]
       (.getVimPort vim-service))))
    

(defn retrieve-service-content
  ([wsdlLocation]
     (let [siRef (managed-object-reference "ServiceInstance" "ServiceInstance")
           vim (vim-service (java.net.URL. wsdlLocation))]
       (.retrieveServiceContent vim siRef))))
       
(let [url (java.net.URL. "https://elaine-test.sensors.elex.be/sdk/vimService.wsdl")
      service (vim-service url)      
      svcRef (ManagedObjectReference.)]
  (doto svcRef
    (.setType "ServiceInstance")
    (.setValue "ServiceInstance"))
  (let [sic (.retrieveServiceContent service svcRef)
        sessionManager (.getSessionManager sic)]
    (.login service sessionManager "esxnagios" "123Qweasdzxc" nil)
    (println "ok")))
  
  
             