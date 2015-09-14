(defproject cmis-core "1.4.0-SNAPSHOT"
  :description "Capacity management information system"
  :url "http://github.com/bhoflack/cmis"
  :maintainer {:email "brh@melexis.com"}
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :plugins [[lein-modules "0.3.11"]]
;  :parent [parent _ :relative-path "../pom.xml"]
  :dependencies [[org.clojure/clojure "_"]
                 [clj-time "_"]
                 [postgresql "_"]
                 [compojure "_"]
                 [hiccup "_"]
                 [org.clojure/data.json "_"]
                 [enlive "1.1.6"]
                 [org.clojars.lstoll/libvirt "0.4.9.1"]
                 [net.java.dev.jna/jna "3.3.0"]
                 [the/parsatron "_"]
                 [clj-http "_"]
                 [com.github.kyleburton/clj-xpath "1.4.5"]
                 [org.clojure/data.xml "_"]
                 [org.clojure/data.zip "_"]
                 [de.ubercode.clostache/clostache "1.4.0"]
                 [org.clojure/core.typed "_"]
                 [org.hsqldb/hsqldb "2.3.3" :scope "test"]
                 [org.clojure/tools.logging "_"]
                 [honeysql "0.6.1"]
                 [log4j "1.2.17"]
                 [org.clojure/tools.cli "_"]
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
  :aot [cmis.core])
