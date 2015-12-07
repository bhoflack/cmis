(ns core.event
  (:require [clojure.core.typed :refer [ann-record defalias]]
            [clojure.core.typed.macros :refer [defprotocol]])
  (:refer-clojure :exclude [defprotocol]))

(defprotocol AEventService
  ())
