(ns cmis-dashboard.service
  (:require [clojure.java.jdbc :as j]
            [honeysql.core :as sql]
            [honeysql.helpers :refer :all]))

(defn transform
  [cis]
  (->> cis
       (group-by :application)
       (map (fn [[ci measurements]]

              {:name ci
               :description (->> measurements (first) (:description))
               :critical_level (->> measurements (first) (:critical_level))
               :instances (->> measurements (map :hostname) (into #{}))
               :measurements (->> measurements
                                  (group-by :check)
                                  (map (fn [[k v]] 
                                         {:name k
                                          :labels (->> v
                                                       (map (fn [{:keys [year month]}]
                                                              (format "%d/%02d" year month)))
                                                       (distinct)
                                                       (sort)
                                                       (into []))
                                          :data (->> v
                                                     (group-by :hostname)
                                                     (map (fn [[hostname mes]]
                                                            [hostname
                                                             (map :average mes)]))
                                                     (into {}))})))}))))
                                                  
(defn get-all-cis
  "Find all cis"
  [ds]
  (-> (select [:application_ci.name "application"]
              [:application_ci.background "description"]
              :application_ci.critical_level
              [:host_ci.name "hostname"]
              [:de.name "check"]
              [:de.unit "unit"]
              :dt.year
              :dt.month
              [(sql/call :avg :fm.value) "average"])
      (from [:fact_measurement :fm])
      (join [:dim_time :dt] [:= :dt.id :fm.time_id]
            [:dim_event :de] [:= :de.id :fm.event_id]
            [:fact_host_application :fha] [:= :fm.id :fha.measurement_id]
            [:dim_ci :application_ci] [:= :application_ci.id :fha.application_id]
            [:dim_ci :host_ci] [:= :host_ci.id :fha.ci_id])
      (group :de.name :application_ci.background :application_ci.critical_level :application_ci.name :host_ci.name :de.unit :dt.year :dt.month)
      (order-by :de.name :application_ci.background :application_ci.critical_level :application_ci.name :host_ci.name :de.unit :dt.year :dt.month)

      (sql/format)
      (->> (j/query ds))))