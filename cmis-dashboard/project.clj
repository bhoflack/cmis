(defproject cmis-dashboard "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.1.8"]
                 [org.clojure/data.json "0.2.5"]
                 [org.clojure/java.jdbc "0.3.5"]
                 [honeysql "0.4.3"]
                 [postgresql "9.1-901.jdbc4"]
                 [org.clojure/data.zip "0.1.1"]
                 ]
  :repositories [["snapshots" "http://nexus.colo.elex.be:8081/nexus/content/repositories/snapshots"]
                 ["releases" "http://nexus.colo.elex.be:8081/nexus/content/repositories/releases"]]

  :plugins [[lein-ring "0.8.11"]]
  :ring {:handler cmis-dashboard.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]}})
