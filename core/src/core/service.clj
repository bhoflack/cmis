(ns cmis.service
  (:require [clojure.core.typed :refer [ann-record defalias Option]]
            [clojure.core.typed.macros :refer [defprotocol]])
  (:refer-clojure :exclude [defprotocol]))

(defalias ServiceName Str)
(ann-record Service
            [name :- ServiceName,
             unit :- Str])

(defprotocol AServiceRepository
  (add [this service :- Service] :- Service)
  (find-by-name [this name :- ServiceName] :- (Option Service)))

(defrecord PostgresServiceRepository [ds]
  AServiceRepository
  (add [this service])
  (find-by-name [this name]))
