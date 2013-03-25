(defproject cmis "0.1.0-SNAPSHOT"
  :description "Capacity management information system"
  :url "http://github.com/bhoflack/cmis"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [cascalog "1.10.1"]
                 [com.senseidb/sensei-java-client "1.0.0"]]
  :profiles {:dev {:dependencies [[org.apache.hadoop/hadoop-core "0.20.2-dev"]]}})
