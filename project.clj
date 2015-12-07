(defproject cmis "2.0.0-SNAPSHOT"
  :description "CMIS core:  Link all events with the CI's."
  :url "http://github.com/bhoflack/cmis"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/core.typed "0.3.11"]
                 [org.clojure/tools.logging "0.3.1"]
                 [org.apache.storm/storm-core "0.10.0"]
                 [enlive "1.1.6"]
                 [http-kit "2.1.19"]
                 [star "0.1.0"]
                 [clj-time "0.11.0"]
                 [postgresql "9.1-901.jdbc4"]]
  :main core.core)
