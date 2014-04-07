(ns cmis.check
  (:import [cmis.service.event AEventService])
  (:require [cmis.datastore.idempotent :as idempotent]
            [clj-ssh.ssh :refer [ssh ssh-sftp with-channel-connection sftp ssh-agent session add-identity with-connection]])
  (:gen-class))

(def NAGIOS_ARCHIVE_DIR "/var/log/nagios3/archives")

(defprotocol ACheck
  (perform [this ^AEventService es]
    "Perform the checks and enter the results in the datasource"))

(comment        
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
  (let [tarfile (-> (java.io.File/createTempFile "nagios" ".tgz") (.getPath))
        filenames (clojure.string/join " " files)]
    (-> session
        (ssh {:in (format "tar -pczvf %s %s" tarfile filenames)}))
    tarfile))
  
(defn- copy-tarfile
  [session dest tarfile]
  (let [channel (ssh-sftp session)]
    (with-channel-connection channel
      (sftp channel {} :get tarfile dest))
    tarfile))

(defn- remove-tar-file
  [session tarfile]
  (-> session
      (ssh {:in (format "rm -f %s" tarfile)}))
  tarfile)

(defn- untar-file
  [tarfile]
  (throw (AssertionError. "to implement")))
        
(defn copy-nagios-files
  "Copy all nagios files that haven't been processed yet

   Returns a list of all files that have been copied
  "
  [idempotent]
  (let [agent (ssh-agent {})
        session (session agent "nagios.elex.be" {:strict-host-key-checking :no})]
    (add-identity agent {:private-key-path "/home/brh/.ssh/id_rsa"})
    (with-connection session
      (some->> idempotent
               (list-files-in-dir session)
               (create-tar session)
               (copy-tarfile "/tmp")
               (remove-tar-file session)
               (untar-file))
      )))

(defn import-nagios-events
  [idempotent nagios-datastore]
  (some->> idempotent
           (copy-nagios-files)
           (map (partial nagios/perform-check nagios-datastore)))))