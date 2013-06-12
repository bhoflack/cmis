(ns cmis.transform
  (:use cascalog.api)
  )

(def nagios-host-pattern #"\[(\d+)\] CURRENT HOST STATE: (.+);(\w+);(\w+);(\d*);(.*)")
(def nagios-service-pattern #"\[(\d+)\] [\w ]*SERVICE [\w]*: (.+);(.+);(\w+);(\w+);(\d*);(.*)")
(def nagios-service-alert-pattern #"\[(\d+)\] SERVICE ALERT: (.+);(.+);(\w+);(\w+);(\d*);(.*)")

(defn- format-nagios-log [line pattern]
  (let [fields (rest (first (re-seq pattern line)))]    
    (vec
     (cons 
      (java.util.Date. (* (Long/parseLong (first fields)) 1000))
      (rest fields)))))  

(deffilterop is-nagios-service-line? [l]
  (re-matches nagios-service-pattern l))

(defmapop nagios-service-line [line]
  (format-nagios-log line nagios-service-pattern))  
