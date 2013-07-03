(ns cmis.nagios
  (:require [cmis.nagios.parser :as p]))

(defprotocol ANagios
  (hosts [this] "List all host.cfg files in the nagios root")
  (config [this] "List all nagios config from a nagios config")
  (withHostGroup [n group] "Find all hosts with the given hostgroup"))

(defrecord Nagios [#^java.io.File root]
  ANagios
  (hosts [n] 
    (filter (fn [f] (= "host.cfg" (.getName f)))
            (file-seq root)))
  (config [this]
    (map (fn [f]
           (try
             (p/parse (slurp f))
             (catch RuntimeException e (str "caught exception while processing " f ": " (.getMessage e)))))       
         (.hosts this)))
  (withHostGroup [this group]
    (let [hs (.config this)]
      (filter (fn [h] (some #{group} (:hostgroups h)))
              hs))))              