(ns cmis-statistics.core
  (:use incanter.core
        incanter.charts
        incanter.stats))

(defn data-to-matrix
  "Convert a list of events to a matrix

   Parameters
     - data - A seq of hashmaps with at least the following fields: [:timestamp :value]

   Returns a seq of vectors with as first field the timestamp ( as a long ) and the value"
  [data]
  (->> data
       (map (fn [r] (assoc r :timestamp (-> r (:timestamp) (.getTime)))))
       (map (fn [r] [(:timestamp r) (:value r)]))))

(defn ^:private buffered-image->byte-array
  [img]
  (let [baos (java.io.ByteArrayOutputStream.)]
    (javax.imageio.ImageIO/write img "png" baos)
    (.flush baos)
    (.toByteArray baos)))

(defn linear-model-graph
  "Build a linear model graph for a dataset

   Parameters
     - matrix - The input matrix

   Named parameters
     - width - an integer with the requested width of the image
     - height - an integer with the requested height of the image

   Returns a BufferedImage"
  [data &{:keys [width height] :or {width 640 height 480}}]
  (let [x (sel data :cols 0)
        y (sel data :cols 1)
        plot (time-series-plot x y :x-label "Time" :legend true)
        lm (linear-model y x)
        baos (java.io.ByteArrayOutputStream.)]
    
    ; Write the image with the regression to the outputstream
    (-> plot
        (add-points x (:fitted lm))
        (.createBufferedImage width height)
        (buffered-image->byte-array))))
