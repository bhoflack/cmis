(ns cmis.nagios-parser
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

(def kv (>> (many1 key-value-contents)
            optional-whitespace
            (many1 key-value-contents)))

(def key-values (many
                 (>> optional-whitespace
                     key-value
                     optional-whitespace)))

(defparser key-value-parser []
  (let->> [space-before (optional-whitespace)
           name (many1 (key-value-contents))
           space (optional-whitespace)
           value (many1 (key-value-contents))
           space-after (optional-whitespace)]
          (always [(keyword (apply str name)) (apply str value)])))

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
  [hostdef]
  (run (between (>> define-host optional-whitespace open-group optional-whitespace)
                (>> optional-whitespace close-group optional-whitespace)
                key-values)
       hostdef))