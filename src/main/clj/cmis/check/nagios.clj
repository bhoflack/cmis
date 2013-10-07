(ns cmis.check.nagios
  (:require [cmis.datastore :refer [ADataStore]]
            [cmis.check :refer [ACheck]]
            [clj-time.core :as time]
            [clj-time.coerce :as coerce]
            [cmis.util :as util]
            [cmis.check.nagios
             [iolatency :as iolatency]
             [cpu :as cpu]
             [iostat :as iostat]]
            ))

(def nagios-service-pattern #"\[(\d+)\] [\w ]*SERVICE [\w]*: (.+);(.+);(\w+);(\w+);(\d*);(.*)")

(defn each-service-line
  "Perform actions on each service line of a file"
  [file & fns]
  (util/each-line file
             (fn [line]
               (let [m (re-matches nagios-service-pattern line)]
                 (if m
                   (let [[_ timestamp hostname check status _ _ msg] m
                         timestamp* (coerce/from-long (* (Long/parseLong timestamp) 1000))
                         check {:timestamp timestamp*
                                :hostname hostname
                                :check check
                                :status status
                                :msg msg}]
                     (doall (map #(% check) fns))))))))


(defmulti perform-check
  (fn [^cmis.datastore.ADataStore ds
       ^java.io.File root] (.isDirectory root)))

(defmethod perform-check true
  [^cmis.datastore.ADataStore ds
   ^java.io.File directory]
  (map (partial perform-check ds)
       (.listFiles directory)))

(defmethod perform-check false
  [^cmis.datastore.ADataStore ds
   ^java.io.File root]
  (each-service-line root
                     (partial iolatency/extract ds)
                     (partial cpu/extract ds)
                     (partial iostat/extract ds)
                     ))

(defrecord NagiosCheck [#^java.io.File root]
  cmis.check.ACheck
  (perform [_ ds] (perform-check ds root)))