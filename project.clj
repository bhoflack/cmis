(defproject cmis "0.1.0-SNAPSHOT"
  :description "Capacity management information system"
  :url "http://github.com/bhoflack/cmis"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/java.jdbc "0.3.0-alpha1"]
                 [clj-time "0.5.0"]
                 [postgresql "9.1-901.jdbc4"]
                 [compojure "1.1.5"]
                 [hiccup "1.0.3"]
                 [org.clojure/data.json "0.2.2"]
                 [enlive "1.1.5"]
                 [org.clojars.lstoll/libvirt "0.4.9.1"]
                 [net.java.dev.jna/jna "3.3.0"]
                 [the/parsatron "0.0.4"]
                 [clj-http "0.7.6"]
                 [com.github.kyleburton/clj-xpath "1.4.1"]
                 [org.clojure/data.xml "0.0.7"]
                 [org.clojure/data.zip "0.1.1"]
                 [de.ubercode.clostache/clostache "1.3.1"]
                 [org.clojure/core.typed "0.2.26"]
                 [org.hsqldb/hsqldb "2.3.1" :scope "test"]
                 [org.clojure/tools.logging "0.2.6"]
                 [honeysql "0.4.3"]
                 [log4j "1.2.17"]
                 [org.clojure/tools.cli "0.3.1"]
                 [aleph "0.3.2"]]
  :plugins [[lein-ring "0.8.5"]]
  :ring {:handler cmis.web/handler}
  :repositories {"conjars" "http://conjars.org/repo/"}
  :profiles {:dev {:dependencies [[org.apache.hadoop/hadoop-core "0.20.2-dev"]]}}
  :source-paths ["src/main/clj"]
  :java-source-paths ["src/main/java"]
  :main cmis.core)
