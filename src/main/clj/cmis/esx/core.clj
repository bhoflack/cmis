(ns cmis.esx.core
  (:use [clojure.data.zip.xml :only (attr text xml-> xml1->)]
        clostache.parser)
  (:require [clj-http.client :as http]
            [clojure.data.xml :as xml]
            [clojure.zip :as zip]))

(def cookie-store
  (atom (clj-http.cookies/cookie-store)))

(defn logout!
  []
  (reset! cookie-store (clj-http.cookies/cookie-store)))

(defn request
  [hostname body]
  (let [uri (format "https://%s/sdk" hostname)]
    (http/post uri
               {:insecure? true
                :headers {"Accept" "*/*" "Content-Type" "text/xml"}
                :cookie-store @cookie-store
                :body body})))

(defn soap
  [body]
  (format "<?xml version=\"1.0\" encoding=\"UTF-8\"?>
    <soapenv:Envelope
     xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"
     xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\"
     xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"
     xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">
    <soapenv:Body>
    %s
    </soapenv:Body>
    </soapenv:Envelope>" body))

(defn retrieve-service-content
  [hostname]
  (let [response (request hostname           
                          (soap "<RetrieveServiceContent xmlns=\"urn:vim25\">
                                   <_this xmlns=\"urn:vim25\" xsi:type=\"ManagedObjectReference\" type=\"ServiceInstance\">ServiceInstance</_this>
                                 </RetrieveServiceContent>"))
        body (:body response)
        xml (xml/parse-str body)
        zipped (zip/xml-zip xml)]
    {:rootFolder (xml1-> zipped :Body :RetrieveServiceContentResponse :returnval :rootFolder text)
     :propertyCollector (xml1-> zipped :Body :RetrieveServiceContentResponse :returnval :propertyCollector text)
     :viewManager (xml1-> zipped :Body :RetrieveServiceContentResponse :returnval :viewManager text)
     :name (xml1-> zipped :Body :RetrieveServiceContentResponse :returnval :about :name text)
     :fullName (xml1-> zipped :Body :RetrieveServiceContentResponse :returnval :about :fullName text)
     :vendor (xml1-> zipped :Body :RetrieveServiceContentResponse :returnval :about :vendor text)
     :version (xml1-> zipped :Body :RetrieveServiceContentResponse :returnval :about :version text)
     :build (xml1-> zipped :Body :RetrieveServiceContentResponse :returnval :about :build text)
     :osType (xml1-> zipped :Body :RetrieveServiceContentResponse :returnval :about :osType text)
     :virtualDiskManager (xml1-> zipped :Body :RetrieveServiceContentResponse :returnval :virtualDiskManager text)
     :fileManager (xml1-> zipped :Body :RetrieveServiceContentResponse :returnval :fileManager text)
     :licenseManager (xml1-> zipped :Body :RetrieveServiceContentResponse :returnval :licenseManager text)
     :sessionManager (xml1-> zipped :Body :RetrieveServiceContentResponse :returnval :sessionManager text)
     }))     
    
(defn login
  [hostname username password session-manager]
  (let [response (request hostname
                          (soap (format "<Login xmlns=\"urn:vim25\">
                                           <_this xmlns=\"urn:vim25\" xsi:type=\"ManagedObjectReference\" type=\"SessionManager\">%s</_this>
                                           <userName xmlns=\"urn:vim25\" xsi:type=\"xsd:string\">%s</userName><password xmlns=\"urn:vim25\" xsi:type=\"xsd:string\">%s</password>
                                         </Login>" session-manager username password)))
        body (:body response)
        xml (xml/parse-str body)
        zipped (zip/xml-zip xml)]
    
    {:key (xml1-> zipped :Body :LoginResponse :returnval :key text)
     :username (xml1-> zipped :Body :LoginResponse :returnval :userName text)
     :fullname (xml1-> zipped :Body :LoginResponse :returnval :fullName text)
     :logintime (xml1-> zipped :Body :LoginResponse :returnval :loginTime text)
     :lastactivetime (xml1-> zipped :Body :LoginResponse :returnval :lastactivetime text)
     }))

(defn retrieve-properties
  [hostname property-collector spec-set]
  (let [msg (render "<RetrieveProperties xmlns=\"urn:vim25\">
                                      <_this xmlns=\"urn:vim25\" xsi:type=\"ManagedObjectReference\" type=\"PropertyCollector\">{{propertyCollector}}</_this>
                                      <specSet xmlns=\"urn:vim25\" xsi:type=\"{{specSet.type}}\">
                                        <propSet xmlns=\"urn:vim25\" xsi:type=\"PropertySpec\">
                                          <type xmlns=\"urn:vim25\" xsi:type=\"xsd:string\">{{specSet.propSet.type}}</type>
                                          {{#specSet.propSet.properties}}
                                          <pathSet xmlns=\"urn:vim25\" xsi:type=\"xsd:string\">{{.}}</pathSet>
                                          {{/specSet.propSet.properties}}
                                        </propSet>
                                        <objectSet xmlns=\"urn:vim25\" xsi:type=\"ObjectSpec\">
                                          <obj xmlns=\"urn:vim25\" xsi:type=\"ManagedObjectReference\" type=\"{{specSet.objectSet.type}}\">{{specSet.objectSet.name}}</obj>
                                          <skip xmlns=\"urn:vim25\" xsi:type=\"xsd:boolean\">{{specSet.objectSet.skip}}</skip>
                                          <selectSet xmlns=\"urn:vim25\" xsi:type=\"TraversalSpec\">
                                            <name xmlns=\"urn:vim25\" xsi:type=\"xsd:string\">{{specSet.objectSet.selectSet.name}}</name>
                                            <type xmlns=\"urn:vim25\" xsi:type=\"xsd:string\">{{specSet.objectSet.selectSet.type}}</type>
                                            <path xmlns=\"urn:vim25\" xsi:type=\"xsd:string\">{{specSet.objectSet.selectSet.path}}</path>
                                            <skip xmlns=\"urn:vim25\" xsi:type=\"xsd:boolean\">{{specSet.objectSet.selectSet.skip}}</skip>
                                          </selectSet>
                                        </objectSet>
                                      </specSet>
                                    </RetrieveProperties>"
                                   {:propertyCollector property-collector
                                    :specSet spec-set
                                    })
        response (request hostname (soap msg))
        body (:body response)
        xml (xml/parse-str body)
        zipped (zip/xml-zip xml)
        props (xml-> zipped :Body :RetrievePropertiesResponse :returnval :propSet)
        propsToMap (fn [prop] {(keyword (xml1-> prop :name text))
                               {:type (xml1-> prop :val (attr :type))
                                :value (xml1-> prop :val text)}})
        props1 (map propsToMap props)]    
    {:obj {:type  (xml1-> zipped :Body :RetrievePropertiesResponse :returnval :obj (attr :type))
           :value (xml1-> zipped :Body :RetrievePropertiesResponse :returnval :obj text)}
     :propSet props1}))