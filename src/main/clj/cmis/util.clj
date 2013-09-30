(ns cmis.util)

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

