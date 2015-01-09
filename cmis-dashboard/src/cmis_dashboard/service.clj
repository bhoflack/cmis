(ns cmis-dashboard.service
  (:require [clojure.java.jdbc :as j]
            [honeysql.core :as sql]
            [honeysql.helpers :refer :all]))

(defn transform-measurements
  [measurements]
  (if (empty? measurements)
    nil
    {:name (->> measurements (first) (:application))
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
                                           (into {}))})))}))

(defn transform
  [cis]
  (->> cis
       (group-by :application)
       (map transform-measurements)))
                                                  
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

(defn find-all-products
  "Find all the products"
  [ds]
  (-> (select [:ci.name "name"])
      (modifiers :distinct)
      (from [:dim_ci :ci])
      (join [:fact_host_application :fha] [:= :ci.id :fha.application_id])
      (order-by :ci.name)

      (sql/format)
      (->> (j/query ds))))
              
(defn find-ci-with-name
  "Find the ci with the given name"
  [ds name]
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
      (where [:= :application_ci.name name])
      (group :de.name :application_ci.background :application_ci.critical_level :application_ci.name :host_ci.name :de.unit :dt.year :dt.month)
      (order-by :de.name :application_ci.background :application_ci.critical_level :application_ci.name :host_ci.name :de.unit :dt.year :dt.month)

      (sql/format)
      (->> (j/query ds))))

(defn find-all-measurements
  "Find all measurements for the given ci and check name"
  [ds ci-name event-name hostname]
  (-> (select [:host_ci.name "hostname"]
              :dt.timestamp
              :fm.value)
      (from [:fact_measurement :fm])
      (join [:dim_time :dt] [:= :dt.id :fm.time_id]
            [:dim_event :de] [:= :de.id :fm.event_id]
            [:fact_host_application :fha] [:= :fm.id :fha.measurement_id]
            [:dim_ci :application_ci] [:= :application_ci.id :fha.application_id]
            [:dim_ci :host_ci] [:= :host_ci.id :fha.ci_id])
      (where [:and 
              [:= :application_ci.name ci-name]
              [:= :de.name event-name]
              [:= :host_ci.name hostname]])
      (sql/format)
     (->> (j/query ds))))

(defn events-for-ci
  "Find all events that are applicable for a ci"
  [ds ci-name]
  (-> (select [:de.name :event-name])
      (modifiers :distinct)
      (from [:fact_measurement :fm])
      (join [:dim_event :de] [:= :de.id :fm.event_id]
            [:fact_host_application :fha] [:= :fha.measurement_id :fm.id]
            [:dim_ci :application_ci] [:= :application_ci.id :fha.application_id])
      (where [:= :application_ci.name ci-name])
      (sql/format)
      (->> (j/query ds)
           (map :event_name))))

(defn instances-for-ci
  "Find all instances that are applicable for a ci"
  [ds ci-name]
  (-> (select [:host_ci.name :hostname])
      (modifiers :distinct)
      (from [:fact_measurement :fm])
      (join [:dim_event :de] [:= :de.id :fm.event_id]
            [:fact_host_application :fha] [:= :fha.measurement_id :fm.id]
            [:dim_ci :application_ci] [:= :application_ci.id :fha.application_id]
            [:dim_ci :host_ci] [:= :host_ci.id :fha.ci_id])
      (where [:= :application_ci.name ci-name])
      (sql/format)
      (->> (j/query ds)
           (map :hostname))))
