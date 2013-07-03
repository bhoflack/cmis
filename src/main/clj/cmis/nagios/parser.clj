(ns cmis.nagios.parser
  (:refer-clojure :exclude [char])
  (:use [the.parsatron]))

(defparser define-host [] (string "define host"))

(defparser open-group [] (char \{))
(defparser close-group [] (char \}))

(defparser whitespace-char []
  (token #{\space \newline \tab}))
(defparser optional-whitespace []
  (many (whitespace-char)))

(defparser key-value-contents []
  (choice (letter)
          (digit)
          (token #{\. \_ \- \,})))

(defparser key-value-parser []
  (let->> [space-before (optional-whitespace)
           name (many1 (key-value-contents))
           space (optional-whitespace)
           value (many1 (key-value-contents))
           space-after (optional-whitespace)]
          (let [n (keyword (apply str name))
                v (apply str value)]
            (if (= :hostgroups n)
              (always [n (seq (.split v ","))])
              (always [n v])))))

(defn to-hashmap
  [pl]
  (reduce (fn [hm [k v]] (assoc hm k v)) {} pl))
  

(defparser host-config-parser []
  (let->> [type (define-host)
           space-before (optional-whitespace)
           group-start (open-group)
           values (many (key-value-parser))
           group-end (close-group)]
          (always (to-hashmap values))))

(defn parse
  "Parse a nagios config file to a clojure map"
  [hostdef]
  (run (host-config-parser) hostdef))