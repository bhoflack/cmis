(defproject cmis-core "1.3.0-SNAPSHOT"
  :description "Capacity management information system"
  :url "http://github.com/bhoflack/cmis"
  :maintainer {:email "brh@melexis.com"}
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [clj-time "0.11.0"]
                 [postgresql "9.1-901.jdbc4"]
                 [compojure "1.4.0"]
                 [hiccup "1.0.5"]
                 [org.clojure/data.json "0.2.6"]
                 [enlive "1.1.6"]
                 [org.clojars.lstoll/libvirt "0.4.9.1"]
                 [net.java.dev.jna/jna "3.3.0"]
                 [the/parsatron "0.0.4"]
                 [clj-http "2.0.0"]
                 [com.github.kyleburton/clj-xpath "1.4.5"]
                 [org.clojure/data.xml "0.0.8"]
                 [org.clojure/data.zip "0.1.1"]
                 [de.ubercode.clostache/clostache "1.4.0"]
                 [org.clojure/core.typed "0.3.11"]
                 [org.hsqldb/hsqldb "2.3.3" :scope "test"]
                 [org.clojure/tools.logging "0.3.1"]
                 [honeysql "0.6.1"]
                 [log4j "1.2.17"]
                 [org.clojure/tools.cli "0.3.3"]
                 [clojurewerkz/quartzite "2.0.0"]
                 [clj-ssh "0.5.11"]
                 [org.apache.commons/commons-compress "1.10"]
                 [org.slf4j/slf4j-log4j12 "1.6.5"]
                 [com.mchange/c3p0 "0.9.5.1"]
                 [feedparser-clj "0.2"]
                 [star "0.1.0"]]
  :repositories [["snapshots" {:url "http://nexus.colo.elex.be:8081/nexus/content/repositories/snapshots"
                               :sign-releases false}]
                 ["releases" {:url "http://nexus.colo.elex.be:8081/nexus/content/repositories/releases"
                              :sign-releases false}]]
  :source-paths ["src/main/clj"]
  :java-source-paths ["src/main/java"]
  :aot [cmis.core]
  :main cmis.core)
