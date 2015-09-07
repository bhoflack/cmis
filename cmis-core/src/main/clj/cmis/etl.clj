(ns cmis.etl
  (:require [cmis.util :as util]
            [cmis.etl.nagios :as nagios]
            [clojure.java.jdbc :as j]))

(defn parse-nagios-log
  [ds & files]
  (doall
   (for [file files]
     (let [measurements (nagios/parse file)]
       (map (partial to-star-schema ds) measurements)))))

(defn parse-nagios-directory
  [ds dir]
  (let [d (java.io.File. dir)]
    (apply parse-nagios-log ds (seq (.listFiles d)))))
