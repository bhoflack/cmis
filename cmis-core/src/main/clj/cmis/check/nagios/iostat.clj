(ns cmis.check.nagios.iostat
  (:import [cmis.service.event AEventService])
  (:require [cmis.util :as u]
            [cmis.service.event :refer [insert slowly-changing-dimension]]))

(def iostat-re #"rsec/s=(\d+),wsec/s=(\d+),%util=(\d+)")

(defn extract
  [^AEventService es {:keys [timestamp hostname msg check] :as line}]
  (if (= check "IOSTAT")
    (let [hostid (slowly-changing-dimension es :dim_ci {:name hostname} [:name] [:name])]
      (doall 
       (for [[dev results] (partition 2 (clojure.string/split msg #":"))]
         (let [m (re-matches iostat-re results)]
           (if m
             (let [[_ rsec wsec util] m
                   devid (slowly-changing-dimension es
                                                    :dim_ci
                                                    {:name dev :parent_id hostid}
                                                    [:name :parent_id]
                                                    [:name :parent_id])]
               (doall
                (for [[value check unit] [[(u/int-value rsec) "reads" "r/sec"]
                                          [(u/int-value wsec) "writes" "w/sec"]
                                          [(u/int-value util) "cpu utilisation" "%"]]]
                  (insert es
                          {:event check
                           :timestamp timestamp
                           :name hostname
                           :ci_id devid
                           :unit unit
                           :value value
                           :labels []})))))))))))
          
       