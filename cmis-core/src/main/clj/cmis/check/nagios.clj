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
            [cmis.util.compress :refer [with-decompressed-tgz]]
            [clojure.tools.logging :as log]
            [cmis.service.idempotent :as idempotent])
  (:import [cmis.service.event AEventService StarService])
  (:use clj-ssh.ssh))

(def NAGIOS_ARCHIVE_DIR "/var/log/nagios3/archives")
(def nagios-service-pattern #"\[(\d+)\] [\w ]*SERVICE [\w]*: (.+);(.+);(\w+);(\w+);(\d*);(.*)")

; The configuration where to get the nagios events from and the path to the private key.
(def config (atom nil))

(defn each-service-line
  "Perform actions on each service line of a file"
  [file-or-stream & fns]
  (util/each-line-stream file-or-stream
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
      (ssh {:in (str "openssl sha1 " NAGIOS_ARCHIVE_DIR "/nagios-*")})
      (:out)
      (->> (re-seq #"SHA1\((.*)\)= (\w{40})"))
      (->> (map (fn [[_ path sha]] {:path path :sha sha})))
      (->> (filter (fn [{:keys [path]}] (not (.endsWith path ".gz")))))
      (->> (filter #(not (idempotent/contains-entry? idempotent %))))
      (->> (map :path))))
                
(defn- create-tar
  [session files]
  (log/info "Creating tar for nagios logs " files)
  (let [tarfile (-> (java.io.File/createTempFile "nagios" ".tgz") (.getPath))
        filenames (clojure.string/join " " files)
        cmd (format "tar -pczvf %s %s" tarfile filenames)]
    (log/info "Sending command " cmd)
    (let [response (-> session (ssh {:in cmd}))]
      (log/info "Got response " response)
      tarfile)))
  
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

   Returns the input stream to the tar file"
  [idempotent]
  (log/info "Copying nagios files from nagios.elex.be")
  (let [agent (ssh-agent {})
        session (session agent (:hostname @config) {:strict-host-key-checking :no
                                                    :username (:username @config)})]
    (add-identity agent @config)

    (with-connection session
      (->> idempotent
           (list-files-in-dir session)
           (create-tar session)
           (copy-tarfile session "/tmp")
           (remove-tar-file session)
           (java.io.FileInputStream.)))))

(defn import-events
  "Import the nagios events from the nagios server"
  [idempotent event-service]
  (log/info "Importing events from nagios")
  (some-> idempotent
          (copy-nagios-files)
          (with-decompressed-tgz
            (fn [f s]
              (let [filename (-> f (java.io.File.) (.getName))
                    tempfile (java.io.File/createTempFile "nagios" ".log")]
                (log/info "Copying file " f " to temporary file " tempfile)

                ; Save the file to a temporary file so that we can have multiple streams.
                (clojure.java.io/copy s tempfile)

                (let [checkis (java.io.FileInputStream. tempfile)
                      shais   (java.io.FileInputStream. tempfile)
                      sha     (org.apache.commons.codec.digest.DigestUtils/sha1Hex shais)]
                  (log/info "Start processing file " f " (" sha ")")
                  (try 
                    (perform-check event-service checkis)
                    (idempotent/put idempotent {:path filename :sha sha})
                    (catch Exception e
                      (log/warn "Exception while processing file " f ": " e))
                    (finally
                     (.delete tempfile))
                    )))))
           (doall)))