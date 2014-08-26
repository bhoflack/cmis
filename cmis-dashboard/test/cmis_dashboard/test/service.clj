(ns cmis-dashboard.test.service
  (:require [clojure.test :refer :all]
            [cmis-dashboard.service :refer :all]))

(deftest get-all-cis-test
  (testing "Transformations"
    (let [ci [{:application "dlog"
               :hostname "dlog1.sensors.elex.be"
               :description "bla bla bla"
               :critical_level 1
               :check "cpu idle"
               :unit "%"
               :year 2014
               :month 7
               :average 99}
              {:application "dlog"
               :hostname "dlog2.sensors.elex.be"
               :description "bla bla bla"
               :critical_level 1
               :check "cpu idle"
               :unit "%"
               :year 2014
               :month 7
               :average 95}
              {:application "dlog"
               :hostname "dlog1.sensors.elex.be"
               :description "bla bla bla"
               :critical_level 1
               :check "cpu idle"
               :unit "%"
               :year 2014
               :month 8
               :average 30}
              {:application "dlog"
               :hostname "dlog2.sensors.elex.be"
               :description "bla bla bla"
               :critical_level 1
               :check "cpu idle"
               :unit "%"
               :year 2014
               :month 8
               :average 65}]]
      (is (= [{:name "dlog"
               :description "bla bla bla"
               :critical_level 1
               :instances #{"dlog1.sensors.elex.be"
                            "dlog2.sensors.elex.be"}               
               :measurements [{:name "cpu idle"
                               :labels ["07/2014" "08/2014"]
                               :data {"dlog1.sensors.elex.be" '(99 30)
                                      "dlog2.sensors.elex.be" '(95 65)}}]}]
             (transform ci)
             
             )))))
                                
                                                                 
                               
                             
            
