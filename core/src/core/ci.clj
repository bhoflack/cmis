(ns core.ci
  (:require [clojure.core.typed :as t :refer [defalias ann-record Str Int]]
            [clojure.core.typed.macros :refer [defprotocol]])
  (:refer-clojure :exclude [defprotocol]))

(defalias Hostname Str)
(ann-record Ci
            [hostname :- Hostname,
             memory :- Int,
             cpus :- Int])
(defrecord Ci [hostname memory cpus])

(defprotocol ACiService
  (find-for-hostname [this hostname :- Hostname] :- (t/Seq Ci))
  (add [this ci :- Ci] :- Ci))
