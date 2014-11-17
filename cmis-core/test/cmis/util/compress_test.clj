(ns cmis.util.compress-test
  (:use clojure.test
        cmis.util.compress))

(defn to-byte-array [is]
  (let [buf (make-array Byte/TYPE (.available is))]
    (.read is buf)
    buf))

(deftest decompress-tgz-test
  (testing "Decompressing a tgz file"
    (is (= '(["blub.txt" "blub ik ben een vis\n"]
             ["mnt/categorymaps/blaat.txt" "blaat ik ben een koe\n"])
           (some-> "test-resources/test.tgz"
                   (java.io.FileInputStream.)
                   (decompress-tgz)
                   (->> (map (fn [[k v]] [k (to-byte-array v)])))
                   (->> (map (fn [[k v]] [k (String. v)]))))))))

