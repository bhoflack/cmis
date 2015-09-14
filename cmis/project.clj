(defproject cmis "1.4.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :maintainer {:email "brh@melexis.com"}
  :dependencies [[org.clojure/clojure "_"]
                 [cmis-core :version]
                 [cmis-dashboard :version]
                 [ring-server "0.3.1"]
                 [com.mchange/c3p0 "0.9.2.1"]]
  :plugins [[lein-ring "0.8.11"]
            [lein-modules "0.3.11"]]
  :ring {:handler cmis-dashboard.handler/app}
  :aot [cmis.bin]
  :main cmis.bin)
