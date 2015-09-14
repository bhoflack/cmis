(defproject cmis-statistics "1.4.0-SNAPSHOT"
  :description "Statistics on top of CMDB data"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :plugins [[lein-modules "0.3.11"]]
  :dependencies [[org.clojure/clojure "_"]
                 [org.clojure/java.jdbc "_"]
                 [postgresql "_"]
                 [incanter/incanter-core "_"]
                 [incanter/incanter-charts "_"]])
