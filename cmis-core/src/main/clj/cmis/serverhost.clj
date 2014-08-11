(ns cmis.serverhost)

(defrecord Domain
    [name uuid type cpus max-memory used-memory])

(defprotocol P
  (list-domains [x]))  