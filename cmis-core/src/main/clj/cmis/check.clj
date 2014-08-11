(ns cmis.check
  (:import [cmis.service.event AEventService])
  (:require [cmis.datastore.idempotent :as idempotent]
            [clj-ssh.ssh :refer [ssh ssh-sftp with-channel-connection sftp ssh-agent session add-identity with-connection]])
  (:gen-class))

(defprotocol ACheck
  (perform [this ^AEventService es]
    "Perform the checks and enter the results in the datasource"))