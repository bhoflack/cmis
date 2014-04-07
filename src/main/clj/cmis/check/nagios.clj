(ns cmis.check.nagios
  (:require [cmis.service.event :refer [insert]]
            [cmis.check :refer [ACheck]]
            [clj-time.core :as time]
            [clj-time.coerce :as coerce]
            [cmis.util :as util]
            [cmis.check.nagios
             [iolatency :as iolatency]
             [cpu :as cpu]
             [iostat :as iostat]]
            [cmis.util.compress :refer [decompress-tgz]]
            [clojure.tools.logging :as log]
            [cmis.datastore.idempotent :as idempotent])
  (:import [cmis.service.event AEventService StarService])
  (:use clj-ssh.ssh))

(def NAGIOS_ARCHIVE_DIR "/var/log/nagios3/archives")
(def nagios-service-pattern #"\[(\d+)\] [\w ]*SERVICE [\w]*: (.+);(.+);(\w+);(\w+);(\d*);(.*)")

(defn each-service-line
  "Perform actions on each service line of a file"
  [file-or-stream & fns]
  (util/each-line file-or-stream
             (fn [line]
               (let [m (re-matches nagios-service-pattern line)]
                 (if m
                   (let [[_ timestamp hostname check status _ _ msg] m
                         timestamp* (coerce/from-long (* (Long/parseLong timestamp) 1000))
                         check {:timestamp timestamp*
                                :hostname hostname
                                :check check
                                :status status
                                :msg msg}]
                     (doall (map #(% check) fns))))))))

(defmulti perform-check
  (fn [^AEventService es root]
    (and (= java.io.File (type root)) (.isDirectory root))))

(defmethod perform-check true
  [^AEventService es directory]
  (map (partial perform-check es)
       (.listFiles directory)))

(defmethod perform-check false
  [^AEventService es file-or-stream]
  (each-service-line file-or-stream
                     (partial iolatency/extract es)
                     (partial cpu/extract es)
                     (partial iostat/extract es)))

(defrecord NagiosCheck [#^java.io.File root]
  cmis.check.ACheck
  (perform [_ ds] (perform-check ds root)))


(defn- list-files-in-dir
  [session idempotent]
  (-> session
      (ssh {:in (str "ls " NAGIOS_ARCHIVE_DIR)})
      (:out)
      (->> (re-seq #"nagios-[\w\-\.]+"))
      (->> (filter #(not (.endsWith % ".gz"))))
      (->> (filter #(not (idempotent/contains-entry? idempotent %))))
      (->> (map #(.getPath (java.io.File. NAGIOS_ARCHIVE_DIR %))))))

(defn- create-tar
  [session files]
  (log/info "Creating tar for nagios logs")
  (let [tarfile (-> (java.io.File/createTempFile "nagios" ".tgz") (.getPath))
        filenames (clojure.string/join " " files)]
    (-> session
        (ssh {:in (format "tar -pczvf %s %s" tarfile filenames)}))
    tarfile))
  
(defn- copy-tarfile
  [session dest tarfile]
  (log/info "Copying the tarfile")
  (let [channel (ssh-sftp session)]
    (with-channel-connection channel
      (sftp channel {} :get tarfile dest))
    tarfile))

(defn- remove-tar-file
  [session tarfile]
  (log/info "Removing remote tar file")
  (-> session
      (ssh {:in (format "rm -f %s" tarfile)}))
  tarfile)

(defn copy-nagios-files
  "Copy all nagios files that haven't been processed yet

   Returns a list of pairs containing the filename and the stream"
  [idempotent]
  (let [agent (ssh-agent {})
        session (session agent "nagios.elex.be" {:strict-host-key-checking :no})]
    (add-identity agent {:private-key-path "/home/brh/.ssh/id_rsa"})
    (with-connection session
      (some->> idempotent
               (list-files-in-dir session)
               (create-tar session)
               (copy-tarfile session "/tmp")
               (remove-tar-file session)
               (java.io.FileInputStream.)
               (decompress-tgz))
      )))

(defn import-events
  "Import the nagios events from the nagios server"
  [idempotent event-service]
  (some->> idempotent
           (copy-nagios-files)
           (map (fn [[f s]]
                  (log/info "Start processing file " f)
                  (perform-check event-service s)))
           (doall)))