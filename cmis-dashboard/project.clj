(defproject cmis-dashboard "1.4.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "_"]
                 [compojure "_"]
                 [org.clojure/data.json "_"]
                 [org.clojure/java.jdbc "_"]
                 [honeysql "_"]
                 [postgresql "_"]
                 [org.clojure/data.zip "_"]
                 [cmis-statistics :version]
                 [hiccup "_"]]
  :plugins [[lein-ring "0.8.11"]
            [lein-modules "0.3.11"]]
  :ring {:handler cmis-dashboard.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]}})

