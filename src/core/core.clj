(ns core.core
  (:require
   [core.util :refer [uri->stream]]
   [core.cmdb :refer [list-products find-product-information]]
   [backtype.storm.clojure :refer [ack! bolt bolt-spec defbolt defspout spout spout-spec topology emit-bolt! emit-spout!]]
   [core.spouts.measurement :refer [measurement-spout]]
   [core.bolts.star :refer [to-star-schema]])
  (:import
   [backtype.storm LocalCluster])
  (:gen-class))

(defspout cmdb-spout ["ci"]
  [conf context collector]
  (let [products (atom (clojure.lang.PersistentQueue/EMPTY))]
    ; This thread feeds cmdb ci's into the products agent
    (.start
     (Thread.
      (fn []
        (loop []
          (->> "http://cmdb.elex.be/products"
               (java.net.URI.)
               (uri->stream)
               (list-products)
               (map (fn [[_ uri]] (find-product-information uri)))
               (map (fn [product-information] (swap! products conj product-information))))
          ; Sleep for one hour
          (Thread/sleep (* 3600 1))
          (recur)))))

    (spout
     (nextTuple []
                (when-let [product (-> @products peek)]
                  (swap! products pop)
                  (emit-spout! collector [product]))))))

; Send a new message for every installed instance
(defbolt ci-per-hostname ["type" "hostname" "ci"]
  [{ci "ci" :as tuple} collector]
  (doseq [installed-instance (:installed_instances ci)]
    (emit-bolt! collector [:ci-per-hostname installed-instance ci] :anchor tuple))
  (ack! collector tuple))

; Join the measurement with the ci info if we have more information
(defbolt join-ci-info ["type" "measurement" "ci"] {:prepare true}
  [conf context collector]
  (let [products (atom {})]
    (bolt
     (execute [{type "type" :as tuple}]
              (println "Got message" tuple)
              (cond
                (= :ci-per-hostname type)
                (let [{hostname "hostname"
                       ci "ci"} tuple]
                  (swap! products assoc hostname ci))
                (= :measurement type)
                (let [{hostname "hostname"
                       measurement "measurement"} tuple]
                  (if-let [ci (get @products hostname)]
                                        ; forward the measurement without the ci data
                    (emit-bolt! collector [:measurement-with-ci-info
                                           measurement
                                           ci])
                    (do
                      (println "Sending without ci info")
                      (emit-bolt! collector [:measurement-without-ci-info
                                             measurement
                                             nil])))))))))

(def cmis-topology
  (topology
   {"ci" (spout-spec cmdb-spout)
    "measurement" (spout-spec measurement-spout)}
   {"ci-per-hostname" (bolt-spec {"ci" :shuffle}
                                 ci-per-hostname)
    "measurement-with-ci" (bolt-spec {"ci-per-hostname" ["hostname"]
                                      "measurement" ["hostname"]}
                                     join-ci-info)
    "to-star-schema" (bolt-spec {"measurement-with-ci" :shuffle}
                                (to-star-schema {:subprotocol "postgresql"
                                                 :classname "org.postgresql.Driver"
                                                 :subname "//localhost/cmis"
                                                 :user "cmis"
                                                 :password "cmis"}))}))

(defn- never-returns
  []
  (loop []
    (Thread/sleep 100000)
    (recur)))

(defn -main
  []
  (-> (LocalCluster.)
      (.submitTopology "cmis" {} cmis-topology))

                                        ; Keep the cluster running
  (never-returns))
