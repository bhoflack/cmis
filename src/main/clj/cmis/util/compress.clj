(ns cmis.util.compress
  ^{:doc "Utilities for (de-)compressing files"}

  (:import [org.apache.commons.compress.archivers.tar TarArchiveInputStream]
           [org.apache.commons.compress.compressors.gzip GzipCompressorInputStream])
  )

(defn- list-entries
  [tais]
  (lazy-seq
   (when-let [entry (.getNextEntry tais)]
     (cons [(.getName entry)
            tais]
           (list-entries tais)))))

(defn decompress-tgz
  [^java.io.InputStream stream]
  (some-> stream
          (java.io.BufferedInputStream.)
          (GzipCompressorInputStream.)
          (TarArchiveInputStream.)
          (list-entries)))