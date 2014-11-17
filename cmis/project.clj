(defproject cmis "1.2.0"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :maintainer {:email "brh@melexis.com"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [cmis-core "1.2.0"]
                 [cmis-dashboard "1.2.0"]
                 [ring-server "0.3.1"]
                 [com.mchange/c3p0 "0.9.2.1"]]
  :repositories [["snapshots" {:url "http://nexus.colo.elex.be:8081/nexus/content/repositories/snapshots"
                               :sign-releases false}]
                 ["releases" {:url "http://nexus.colo.elex.be:8081/nexus/content/repositories/releases"
                              :sign-releases false}]]
  :plugins [[lein-ring "0.8.11"]]
  :ring {:handler cmis-dashboard.handler/app}
  :aot [cmis.bin]
  :main cmis.bin)
