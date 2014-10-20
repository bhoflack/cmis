(defproject cmis-core "1.1.2"
  :description "Capacity management information system"
  :url "http://github.com/bhoflack/cmis"
  :maintainer {:email "brh@melexis.com"}
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/java.jdbc "0.3.5"]
                 [java-jdbc/dsl "0.1.0"]
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
                 [clojurewerkz/quartzite "1.2.0"]
                 [clj-ssh "0.5.7"]
                 [org.apache.commons/commons-compress "1.8.1"]
                 [org.slf4j/slf4j-log4j12 "1.6.5"]
                 [com.mchange/c3p0 "0.9.2.1"]
                 [feedparser-clj "0.2"]
                 ]
  :repositories [["snapshots" {:url "http://nexus.colo.elex.be:8081/nexus/content/repositories/snapshots"
                               :sign-releases false}]
                 ["releases" {:url "http://nexus.colo.elex.be:8081/nexus/content/repositories/releases"
                              :sign-releases false}]]
  :source-paths ["src/main/clj"]
  :java-source-paths ["src/main/java"]
  :aot [cmis.core]
  :main cmis.core)
