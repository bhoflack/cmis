(ns cmis.util
  (:require [clojure.tools.logging :as log]))

(defn random-uuid [] (java.util.UUID/randomUUID))

(defmulti string->uuid type)
(defmethod string->uuid String [s] (java.util.UUID/fromString s))
(defmethod string->uuid java.util.UUID [s] s)

(defn each-line
  "Perform actions on each line of a file / stream"
  [file-or-stream & fns]
  (with-open [reader (clojure.java.io/reader file-or-stream)]
    (doseq [line (line-seq reader)]
      (doall
       (map #(% line) fns)))))

(defn each-line-stream
  [stream & fns]
  (let [reader (clojure.java.io/reader stream)]
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

(defmulti convert-uuid (fn [ds _] (:subprotocol ds)))
(defmethod convert-uuid "hsqldb" [ds value] (.toString value))
(defmethod convert-uuid :default [ds value] value)

(defn convert-uuids "hsqldb"
  [ds values]
  (log/debug "Converting uuid's for " values)  
  (into {}
   (map (fn [[k v]]
          (if (= java.util.UUID (type v))
            [k (convert-uuid ds v)]
            [k v])) values)))

(defmulti uri->stream "Create an inputstream for a path" #(.getScheme %))

(defmethod uri->stream nil
  [^java.net.URI uri]
  (-> uri
      (.getPath)
      (java.io.File.)
      (java.io.FileInputStream.)))

(defmethod uri->stream "http"
  [^java.net.URI uri]
  (-> uri
      (.toURL)
      (.openStream)))
