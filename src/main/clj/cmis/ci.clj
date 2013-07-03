(ns cmis.ci
  (:use cmis.nagios)
  (:import cmis.nagios.Nagios)
  (:require [cmis.libvirt :as libvirt])
  )

(defprotocol ACi
  (build-tree [this] "Build a CI tree"))

(defrecord Ci [nagios esxusername esxpassword]
  ACi
  (build-tree [this]
    (let [hosts (.withHostGroup nagios "vmesx_hosts")]
      (map (fn [vmware-server]
             (let [uri (str "esx://" esxusername "@" (:host_name vmware-server) "/?no_verify=1")]
               (try 
                 (let [
                       conn (libvirt/connect uri :password esxpassword)
                       with-node-info (merge vmware-server (libvirt/node-info conn))]
                   (assoc with-node-info :children (libvirt/list-domains conn)))
                 (catch Exception e (str "caught exception while fetching hosts info from " uri " : " (.getMessage e))))))
           (take 2 hosts)))))