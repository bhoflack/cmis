(ns core.types
  (:require [clojure.core.typed :as t :refer [Str Int]]
            [clojure.core.typed.macros :refer [defprotocol]]))

(t/defalias Hostname Str)

(t/ann-record Ci [name :- Str,
                  background :- Str,
                  functionality :- Str,
                  critical_level :- Int,
                  business_owner :- Str,
                  ops_support :- Str,
                  users :- Str,
                  version :- Str,
                  cfengine_classes :- Str,
                  nagios_classes :- Str,
                  installed_instances :- (t/Seq Str)])
(defrecord Ci [name background functionality critical_level business_owner ops_support users version cfengine_classes nagios_classes installed_instances])
