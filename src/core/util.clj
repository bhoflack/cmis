(ns core.util)

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

(defmethod uri->stream "https"
  [^java.net.URI uri]
  (-> uri
      (.toURL)
      (.openStream)))

(defn random-uuid [] (java.util.UUID/randomUUID))
