(ns core.core-test
  (:require [clojure.test :refer :all]
            [core.core :refer :all]
            [backtype.storm.clojure :refer :all])
  (:import [backtype.storm LocalCluster]))

(defspout mock-cmdb-spout ["ci"]
  [conf context collector]
  (let [cis [{:name "electronic wafermapping" :background "" :functionality "" :critical_level 1 :business_owner "lal" :development "brh" :ops_support "alh" :users "" :version "" :installed_instances ["ewaf.colo.elex.be"]}
             {:name "postprocessing2" :background "" :functionality "" :critical_level 1 :business_owner "hha" :development "brh" :ops_support "alh" :users "" :version "" :installed_instances ["esb-a.sensors.elex.be" "esb-b.sensors.elex.be"]}]]
    (spout
     (nextTuple []
                (Thread/sleep 100)
                (emit-spout! collector [(rand-nth cis)])))))

(defspout mock-measurement-spout ["type" "hostname" "measurement"]
  [conf context collector]
  (let [hostnames ["ewaf.colo.elex.be" "esb-a.sensors.elex.be" "esb-b.sensors.elex.be"]]
    (spout
     (nextTuple []
                (Thread/sleep 10)
                (let [hostname (rand-nth hostnames)]
                  (emit-spout! collector [:measurement
                                          hostname
                                          {:hostname hostname
                                           :event "cpu user"
                                           :value 10}]))))))

(defbolt print-measurement []
  [{measurement "measurement" ci "ci" :as tuple} collector]
  (println "Got measurement " measurement " for ci " ci)
  (ack! collector tuple))

(defbolt print-tuple []
  [tuple collector]
  (println "Got tuple" tuple)
  (ack! collector tuple))


(deftest with-ci-info-test
  (let [measurements (agent [])]

    (defbolt send-to-agent []
      [{ci "ci" measurement "measurement" :as tuple} collector]
      (send measurements conj {:ci ci :measurement measurement})
      (ack! collector tuple))

    
    (let [test-topology      
          (topology
           {"ci" (spout-spec mock-cmdb-spout)
            "measurement" (spout-spec mock-measurement-spout)}
           {"ci-per-hostname" (bolt-spec {"ci" :shuffle}
                                         ci-per-hostname)                  
            "measurement-with-ci" (bolt-spec {"ci-per-hostname" ["hostname"]
                                              "measurement" ["hostname"]}
                                             join-ci-info)
            "collect-measurements" (bolt-spec {"measurement-with-ci" :shuffle}
                                              send-to-agent)})]
      
      (-> (LocalCluster.)
          (.submitTopology "cmis-test" {} test-topology))
      (Thread/sleep 5000)
      (is (not (empty? @measurements)))
      (is (some (fn [{ci :ci}] (not (nil? ci))) @measurements)))))

