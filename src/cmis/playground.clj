(ns cmis.playground
  (:use cascalog.api)
  (:require [cascalog.conf :as conf])
  (:import [java.io PrintStream]
           [cascalog WriterOutputStream]
           [org.apache.log4j Logger WriterAppender SimpleLayout]))

(defn bootstrap []
  (use 'cascalog.api
       '[jackknife.seq :only (find-first)])
  (conf/set-job-conf! {"io.sort.mb" 1})
  (require '(cascalog [workflow :as w]
                      [ops :as c]
                      [vars :as v])))

(defn bootstrap-emacs []
  (bootstrap)
  (-> (Logger/getRootLogger)
      (.addAppender (WriterAppender. (SimpleLayout.) *out*)))
  (System/setOut (PrintStream. (WriterOutputStream. *out*))))

(def nagios
  [
   ["[1364079600] CURRENT HOST STATE: ascp-app-test.apps.elex.be;UP;HARD;1;Server alive"]
   ["[1364079600] CURRENT HOST STATE: ascp-db-test.apps.elex.be;UP;HARD;1;Server alive"]
   ["[1364079600] CURRENT HOST STATE: ategen-test.erfurt.elex.be;UP;HARD;1;Server alive"]
   ["[1364079600] CURRENT HOST STATE: atlan.erfurt.elex.be;UP;HARD;1;Server alive"]
   ["[1364079600] CURRENT HOST STATE: autobuild-test.elex.be;UP;HARD;1;Server alive"]
   ["[1364079600] CURRENT HOST STATE: azhira.printers.erfurt.elex.be;UP;HARD;1;PING OK - Packet loss = 0%, RTA = 34.11 ms"]
   ["[1364079600] CURRENT HOST STATE: ascp-db-test.apps.elex.be;UP;HARD;1;Server alive"]
   ["[1364079600] CURRENT SERVICE STATE: ascp-db-test.apps.elex.be;ARCHIVELOG;WARNING;HARD;3;NRPE: Unable to read output"]
   ["[1364079600] CURRENT SERVICE STATE: ascp-db-test.apps.elex.be;AT;OK;HARD;1;PROCS OK: 1 process with UID = 0 (root), args '/usr/sbin/atd'"]
   ["[1364079600] CURRENT SERVICE STATE: ascp-db-test.apps.elex.be;CFAGENT;OK;HARD;1;OK: Connection to cfengine master OK"]
   ["[1364079600] CURRENT SERVICE STATE: ascp-db-test.apps.elex.be;CFENGINE;OK;HARD;1;PROCS OK: 1 process with UID = 0 (root), args 'cfservd'"]
   ["[1364079600] CURRENT SERVICE STATE: ascp-db-test.apps.elex.be;CPU_STATS;OK;HARD;1;CPU STATISTICS OK : user=0.00% system=0.00% iowait=1.20% idle=98.80%"]
   ["[1364079600] CURRENT SERVICE STATE: ascp-db-test.apps.elex.be;CRON;OK;HARD;1;PROCS OK: 1 process with UID = 0 (root), args 'cron'"]
   ["[1364079600] CURRENT SERVICE STATE: ascp-db-test.apps.elex.be;DATA_BACKUP;CRITICAL;HARD;3;/usr/local/etc/data_backup is missing!"]
   ["[1364079600] CURRENT SERVICE STATE: ascp-db-test.apps.elex.be;DISK;OK;HARD;1;OK - all disk"]
   ["[1364079600] CURRENT SERVICE STATE: ascp-db-test.apps.elex.be;EM_ALERTS;WARNING;HARD;3;NRPE: Unable to read output"]
   ["[1364079600] CURRENT SERVICE STATE: ascp-db-test.apps.elex.be;INODES;OK;HARD;1;OK - all disk"]
   ["[1364079600] CURRENT SERVICE STATE: ascp-db-test.apps.elex.be;IOSTAT;OK;HARD;1;SDA:rsec/s=0,wsec/s=0,%util=0:SDB:rsec/s=0,wsec/s=0,%util=0"]
   ["[1364079600] CURRENT SERVICE STATE: ascp-db-test.apps.elex.be;LOAD;OK;HARD;1;OK - load average: 0.00, 0.00, 0.00"]
   ["[1364079600] CURRENT SERVICE STATE: ascp-db-test.apps.elex.be;MEMORY;OK;HARD;1;Memory OK - 93.2% (7622656 kB) free"]
   ["[1364079600] CURRENT SERVICE STATE: ascp-db-test.apps.elex.be;NRPE;OK;HARD;1;PROCS OK: 3 processes with UID = 18018 (nrpe), args '/usr/sbin/nrpe'"]
   ["[1364079600] CURRENT SERVICE STATE: ascp-db-test.apps.elex.be;PING;OK;HARD;1;PING OK - Packet loss = 0%, RTA = 0.28 ms"]
   ["[1364079600] CURRENT SERVICE STATE: ascp-db-test.apps.elex.be;POSTFIX;OK;HARD;1;PROCS OK: 1 process with UID = 0 (root), args '/postfix/master'"]
   ["[1364079600] CURRENT SERVICE STATE: ascp-db-test.apps.elex.be;SSH;OK;HARD;1;SSH OK - OpenSSH_4.3 (protocol 2.0)"]
   ["[1364079600] CURRENT SERVICE STATE: ascp-db-test.apps.elex.be;SWAP;OK;HARD;1;SWAP OK - 100% free (996 MB out of 996 MB)"]
   ["[1364079600] CURRENT SERVICE STATE: ascp-db-test.apps.elex.be;SYSLOG;OK;HARD;1;PROCS OK: 1 process with UID = 0 (root), args 'syslogd'"]
   ["[1364079600] CURRENT SERVICE STATE: ascp-db-test.apps.elex.be;TNS;CRITICAL;HARD;3;Connection refused"]
   ["[1364079600] CURRENT SERVICE STATE: ascp-db-test.apps.elex.be;TNSLSNR;CRITICAL;HARD;3;PROCS CRITICAL: 0 processes with UID = 700 (oracle), args 'tnslsnr'"]
   ["[1364195735] SERVICE ALERT: storm.colo.elex.be;ESXi IO write;OK;SOFT;2;P1.PL OK - io write latency=33 ms"]
   ["[1364195808] SERVICE ALERT: enzo.kiev.elex.be;ESXi IO write;WARNING;SOFT;2;P1.PL WARNING - io write latency=69 ms"]
   ["[1364195846] SERVICE ALERT: domestos.sofia.elex.be;ESXi IO write;WARNING;SOFT;1;P1.PL WARNING - io write latency=47 ms"]
   ["[1364195869] SERVICE ALERT: shadowcat.colo.elex.be;ESXi IO write;WARNING;SOFT;2;P1.PL WARNING - io write latency=51 ms"]
   ["[1364195916] SERVICE ALERT: bizet.colo.elex.be;ESXi IO write;OK;SOFT;2;P1.PL OK - io write latency=32 ms"]
   ["[1364195966] SERVICE ALERT: starscream.erfurt.elex.be;ESXi IO write;WARNING;SOFT;1;P1.PL WARNING - io write latency=41 ms"]
   ["[1364196027] SERVICE ALERT: riddler.colo.elex.be;ESXi IO write;WARNING;SOFT;1;P1.PL WARNING - io write latency=49 ms"]
   ["[1364196086] SERVICE ALERT: joker.colo.elex.be;ESXi IO write;OK;SOFT;2;P1.PL OK - io write latency=35 ms"]
   ["[1364196347] SERVICE ALERT: enzo.kiev.elex.be;ESXi IO write;WARNING;HARD;3;P1.PL WARNING - io write latency=42 ms"]
   ["[1364196380] SERVICE ALERT: domestos.sofia.elex.be;ESXi IO write;WARNING;SOFT;2;P1.PL WARNING - io write latency=41 ms"]
   ["[1364196407] SERVICE ALERT: shadowcat.colo.elex.be;ESXi IO write;OK;SOFT;3;P1.PL OK - io write latency=24 ms"]
   ["[1364196419] SERVICE ALERT: magneto.colo.elex.be;ESXi IO write;WARNING;SOFT;1;P1.PL WARNING - io write latency=43 ms"]
   ["[1364196467] SERVICE ALERT: linbev07.bevaix.elex.be;POSTFIX;CRITICAL;SOFT;1;(Service Check Timed Out)"]
   ["[1364196507] SERVICE ALERT: starscream.erfurt.elex.be;ESXi IO write;WARNING;SOFT;2;P1.PL WARNING - io write latency=41 ms"]
   ["[1364196516] SERVICE ALERT: bizet.colo.elex.be;ESXi IO write;WARNING;SOFT;1;P1.PL WARNING - io write latency=45 ms"]
   ["[1364196565] SERVICE ALERT: riddler.colo.elex.be;ESXi IO write;OK;SOFT;2;P1.PL OK - io write latency=27 ms"]
   ["[1364196584] SERVICE ALERT: temp1.bevaix.elex.be;PING;WARNING;SOFT;1;PING WARNING - Packet loss = 81%, RTA = 29.65 ms"]
   ["[1364166000] CURRENT SERVICE STATE: ewaf.colo.elex.be;ACTIVEMQ;OK;HARD;1;TCP OK - 0.000 second response time on port 61616"]
   ["[1364166000] CURRENT SERVICE STATE: ewaf.colo.elex.be;AT;OK;HARD;1;PROCS OK: 1 process with UID = 1 (daemon), args '/usr/sbin/atd'"]
   ["[1364166000] CURRENT SERVICE STATE: ewaf.colo.elex.be;CFAGENT;OK;HARD;1;OK: Connection to cfengine master OK"]
   ["[1364166000] CURRENT SERVICE STATE: ewaf.colo.elex.be;CFENGINE;OK;HARD;1;PROCS OK: 1 process with UID = 0 (root), args 'cfservd'"]
   ["[1364166000] CURRENT SERVICE STATE: ewaf.colo.elex.be;CPU_STATS;OK;HARD;1;CPU STATISTICS OK : user=5.21% system=0.65% iowait=0.94% idle=93.20%"]
   ["[1364166000] CURRENT SERVICE STATE: ewaf.colo.elex.be;CRON;OK;HARD;1;PROCS OK: 1 process with UID = 0 (root), args 'cron'"]
   ["[1364166000] CURRENT SERVICE STATE: ewaf.colo.elex.be;DISK;OK;HARD;1;OK - all disk"]
   ["[1364166000] CURRENT SERVICE STATE: ewaf.colo.elex.be;EWAF_POLLING_FTP;CRITICAL;HARD;3;CRITICAL -  senodia.confirm - nothing found in the logfile. unisem.confirm - nothing found in the logfile."]
   ["[1364166000] CURRENT SERVICE STATE: ewaf.colo.elex.be;INODES;OK;HARD;1;OK - all disk"]
   ["[1364166000] CURRENT SERVICE STATE: ewaf.colo.elex.be;IOSTAT;OK;HARD;1;SDA:rsec/s=0,wsec/s=0,%util=0:SDB:rsec/s=0,wsec/s=0,%util=0"]
   ["[1364166000] CURRENT SERVICE STATE: ewaf.colo.elex.be;JETTY;OK;HARD;1;TCP OK - 0.000 second response time on port 8082"]
   ["[1364166000] CURRENT SERVICE STATE: esb-a.colo.elex.be;CFAGENT;WARNING;HARD;3;WARNING: cfengine:esb-a: Can't stat /var/lib/cfengine2/masterfiles/files/esb/eventstore.cfg in copy"]
   ["[1364166000] CURRENT SERVICE STATE: esb-a.colo.elex.be;CFENGINE;OK;HARD;1;PROCS OK: 1 process with UID = 0 (root), args 'cfservd'"]
   ["[1364166000] CURRENT SERVICE STATE: esb-a.colo.elex.be;CPU_STATS;OK;HARD;1;CPU STATISTICS OK : user=0.30% system=0.00% iowait=0.35% idle=99.35%"]
   ["[1364166000] CURRENT SERVICE STATE: esb-a.colo.elex.be;CRON;OK;HARD;1;PROCS OK: 1 process with UID = 0 (root), args 'cron'"]
   ["[1364166000] CURRENT SERVICE STATE: esb-a.colo.elex.be;DISK;OK;HARD;1;OK - all disk"]
   ["[1364166000] CURRENT SERVICE STATE: esb-a.colo.elex.be;INODES;OK;HARD;1;OK - all disk"]
   ["[1364166000] CURRENT SERVICE STATE: esb-b.colo.elex.be;CASSANDRA;OK;HARD;1;PROCS OK: 1 process with UID = 107 (cassandra), args 'jsvc.exec'"]
   ["[1364166000] CURRENT SERVICE STATE: esb-b.colo.elex.be;CFAGENT;WARNING;HARD;3;WARNING: cfengine:esb-b: Can't stat /var/lib/cfengine2/masterfiles/files/esb/eventstore.cfg in copy"]
   ["[1364166000] CURRENT SERVICE STATE: esb-b.colo.elex.be;CFENGINE;OK;HARD;1;PROCS OK: 1 process with UID = 0 (root), args 'cfservd'"]
   ["[1364166000] CURRENT SERVICE STATE: esb-b.colo.elex.be;CPU_STATS;OK;HARD;1;CPU STATISTICS OK : user=0.50% system=0.25% iowait=0.99% idle=98.26%"]
   ["[1364166000] CURRENT SERVICE STATE: esb-b.colo.elex.be;CRON;OK;HARD;1;PROCS OK: 1 process with UID = 0 (root), args 'cron'"]
   ["[1364166000] CURRENT SERVICE STATE: esb-b.colo.elex.be;DISK;OK;HARD;1;OK - all disk"]
   ["[1364166000] CURRENT SERVICE STATE: esb-b.colo.elex.be;INODES;OK;HARD;1;OK - all disk"]
   ])

(def application-servers
  [
   ["ewafermap" "prod" "ewaf.colo.elex.be"]
   ["ewafermap" "prod" "esb-a.colo.elex.be"]
   ["ewafermap" "prod" "esb-b.colo.elex.be"]
   ["ewafermap" "test" "ewaf-test.colo.elex.be"]
   ["ewafermap" "test" "esb-b-test.colo.elex.be"]
   ])
  

(def nagios-host-pattern #"\[(\d+)\] CURRENT HOST STATE: (.+);(\w+);(\w+);(\d*);(.*)")
(def nagios-service-pattern #"\[(\d+)\] CURRENT SERVICE STATE: (.+);(.+);(\w+);(\w+);(\d*);(.*)")
(def nagios-service-alert-pattern #"\[(\d+)\] SERVICE ALERT: (.+);(.+);(\w+);(\w+);(\d*);(.*)")

(defn- format-nagios-log [line pattern]
  (let [fields (rest (first (re-seq pattern line)))]    
    (vec
     (cons 
      (java.util.Date. (* (Long/parseLong (first fields)) 1000))
      (rest fields)))))  

(defmapop nagios-host-checks [line]
  (format-nagios-log line nagios-host-pattern))
   
(defmapop nagios-service-checks [line]
  (format-nagios-log line nagios-service-pattern))

(defmapop nagios-service-alert [line]
  (format-nagios-log line nagios-service-alert-pattern))  

(deffilterop is-nagios-host-line? [l]
  (re-matches nagios-host-pattern l))
  
(deffilterop is-nagios-service-line? [l]
  (re-matches nagios-service-pattern l))

(deffilterop is-nagios-service-alert? [l]
  (re-matches nagios-service-alert-pattern l))
