(defproject cmis "0.1.0-SNAPSHOT"
  :description "Capacity management information system"
  :url "http://github.com/bhoflack/cmis"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.0"]
                 [cascalog "1.10.1"]
                 [com.twitter/maple "0.2.7"]
                 [org.clojure/java.jdbc "0.3.0-alpha1"]
                 [clj-time "0.5.0"]
                 [postgresql "9.1-901.jdbc4"]
                 [compojure "1.1.5"]
                 [hiccup "1.0.3"]
                 [incanter "1.4.1"]
                 [org.slf4j/slf4j-simple "1.6.5"]
                 [org.clojure/data.json "0.2.2"]
                 [enlive "1.1.1"]
                 ]
  :exclusions [org.slf4j/slf4j-log4j12]
  :plugins [[lein-ring "0.8.5"]]
  :ring {:handler cmis.web/handler}
  :repositories {"conjars" "http://conjars.org/repo/"}
  :profiles {:dev {:dependencies [[org.apache.hadoop/hadoop-core "0.20.2-dev"]]}}
  :main cmis.parse)
