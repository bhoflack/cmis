(ns core.spouts.measurement
  (:use org.httpkit.server)
  (:require [clojure.edn :refer [read]]
            [backtype.storm.clojure :refer [defspout spout emit-spout!]]
            [clj-time.coerce :refer [from-long]]))

(defn parse-message
  [{hostname "hostname" event "event" timestamp "timestamp" unit "unit" measurement "measurement" :as msg}]
  (println "Got message" msg)
  (if (and hostname timestamp measurement event)
    (let [timestamp* (long (* 1000 timestamp))]
      [:measurement hostname {"hostname" hostname
                              "timestamp" (from-long timestamp*)
                              "unit" unit
                              "event" event
                              "measurement" measurement}])
    nil))

(defspout measurement-spout ["type" "hostname" "measurement"]
  [conf context collector]
  
  (let [measurement-queue (atom (clojure.lang.PersistentQueue/EMPTY))
        measurement-handler (fn [req]
                              (if-let [new-queue (some->> req
                                                          :body
                                                          clojure.java.io/reader
                                                          (java.io.PushbackReader.)
                                                          read
                                                          parse-message
                                                          (swap! measurement-queue conj))]
                                {:status 200
                                 :headers {"Content-Type" "text/plain"}
                                 :body (str "added to queue " new-queue)}
                                {:status 406
                                 :headers {"Content-Type" "text/plain"}
                                 :body "Could not parse message"}))
        measurement-handler1 (fn [req]
                              (with-channel req channel    
                                (on-close channel (fn [status]
                                                    (println "Channel closed with status " status)))
                                
                                (on-receive channel (fn [data]
                                                      (some->> data
                                                               read-string
                                                               parse-message
                                                               (swap! measurement-queue conj))))))]


    (run-server measurement-handler {:port 8080})
    
    (spout
     (nextTuple []
                (when-let [measurement (peek @measurement-queue)]
                  (emit-spout! collector measurement)
                  (swap! measurement-queue pop))))))
