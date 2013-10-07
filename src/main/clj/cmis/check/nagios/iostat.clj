(ns cmis.check.nagios.iostat
  (:require cmis.datastore
            [cmis.util :as u])
  
  )

(def iostat-re
  #"rsec/s=(\d+),wsec/s=(\d+),%util=(\d+)")

(defn extract
  [^cmis.datastore.ADataStore ds {:keys [timestamp hostname msg check] :as line}]
  (if (= check "IOSTAT")
    (let [hostid (.slowly-changing-dimension ds :dim_ci {:name hostname} [:name] [:name])]
      (doall 
       (for [[dev results] (partition 2 (clojure.string/split msg #":"))]
         (let [m (re-matches iostat-re results)]
           (if m
             (let [[_ rsec wsec util] m
                   devid (.slowly-changing-dimension ds
                                                     :dim_ci
                                                     {:name dev :parent_id hostid}
                                                     [:name :parent_id]
                                                     [:name :parent_id])]
               (doall
                (for [[value check unit] [[(u/int-value rsec) "reads" "r/sec"]
                                          [(u/int-value wsec) "writes" "w/sec"]
                                          [(u/int-value util) "cpu utilisation" "%"]]]
                  (.save ds
                         {:check check
                          :timestamp timestamp
                          :hostname hostname
                          :ci_id devid
                          :unit unit
                          :value value})))))))))))
          
       