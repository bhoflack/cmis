(ns cmis.util
  (:require [clojure.tools.logging :as log]))

(defn random-uuid [] (java.util.UUID/randomUUID))

(defn each-line
  "Perform actions on each line of a file"
  [file & fns]
  (with-open [reader (clojure.java.io/reader file)]
    (doseq [line (line-seq reader)]
      (doall
       (map #(% line) fns)))))

(defn reverse-cons [seq x] (cons x seq))

(defmulti int-value type)
(defmethod int-value java.lang.Double
  [d] (if (nil? d) nil (.intValue d)))
(defmethod int-value java.lang.String
  [d] (if (nil? d) nil (Integer/parseInt d)))
(defmethod int-value :default
  [d] (throw (Exception. (str "No method for type " d " type: " (type d)))))

(defmulti ^{:doc "Function used to support uuid's for different databases"}
  convert-uuid (fn [ds _] (:subprotocol ds)))

(defmethod convert-uuid "hsqldb"
  [ds values]
  (log/debug "Converting uuid's for " values)
  (into {}
   (map (fn [[k v]]
          (if (= java.util.UUID (type v))
            [k (.toString v)]
            [k v])) values)))

(defmethod convert-uuid :default
  [ds values]
  values)
