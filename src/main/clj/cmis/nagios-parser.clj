(ns cmis.nagios-parser
  (:refer-clojure :exclude [char])
  (:use [the.parsatron]))

(def open-group (char \{))
(def close-group (char \{))

(def whitespace-char (token #{\space \newline \tab}))
(def optional-whitespace (many whitespace-char))

(def key-value (>> (many1 (any-char))
                   optional-whitespace
                   (many1 (any-char))))

(defn parse
  [hostdef]
  (run (between (>> open-group optional-whitespace)
                (>> optional-whitespace close-group)
                (many key-value))))