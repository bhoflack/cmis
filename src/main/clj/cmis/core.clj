(ns cmis.core
  (:require [clojure.tools.cli :refer [parse-opts]]
            [aleph.tcp :as aleph]
            [lamina.core :as lamina])  
  (:gen-class))

(defn echo-handler
  [channel client-info]
  (lamina/siphon channel channel)
  )
  
(def cli-options
  [["-p" "--port PORT" "Port number"
    :default 1234
    :parse-fn #(Integer/parseInt %)
    :validate [#(< 0 % 0x10000) "Must be a number between 0 and 65536"]]
   ["-h" "--help"]])

(defn start
  [opts]
  (->> opts
       (aleph/start-tcp-server echo-handler)))


(defn -main
  [& args]
  (->> (parse-opts args cli-options)
       :options
       start))