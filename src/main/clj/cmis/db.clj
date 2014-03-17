(ns cmis.db
  ^{:doc "Module to create the dataschema"}
  (:require [clojure.java.jdbc :as j]))

(defmulti clear!
  "Clear the data scheme on the given datasource"
  (fn [ds] (:subprotocol ds)))

(defmethod clear! "hsqldb"
  [ds]
  (j/with-connection ds
    (j/do-commands
     "DROP TABLE TEMP_APPS_FOR_HOST IF EXISTS"
     "DROP TABLE FACT_HOST_APPLICATION IF EXISTS"
     "DROP TABLE FACT_MEASUREMENT IF EXISTS"
     "DROP TABLE DIM_TIME IF EXISTS"
     "DROP TABLE DIM_CI IF EXISTS"
     "DROP TABLE DIM_EVENT IF EXISTS"
     "DROP TABLE DIM_ENVIRONMENT IF EXISTS"
     )))

(defmulti create!
  "Create the data scheme on the given datasource"
  (fn [ds] (:subprotocol ds)))

(defmethod create! "hsqldb"
  ^{:doc "Create the data scheme for a hypersonic database"}
  [ds]
  (j/with-connection ds
    (j/do-commands
      (j/create-table-ddl :dim_time
                          [:id             "varchar(36)"      "PRIMARY KEY"]
                          [:timestamp      :timestamp]
                          [:minute         :integer]
                          [:hour           :integer]
                          [:second         :integer]
                          [:day_of_month   :integer]
                          [:day_of_week    :integer]
                          [:week           :integer]
                          [:month          :integer]
                          [:quarter        :integer]
                          [:year           :integer]
                          [:created_at     :timestamp]
                          [:stopped_at     :timestamp])
      (j/create-table-ddl :dim_ci
                          [:id             "varchar(36)"      "PRIMARY KEY"]
                          [:name           "varchar(50)"]
                          [:cpu            "varchar(50)"]
                          [:num_cpus       :integer]
                          [:memory         :integer]
                          [:parent         "varchar(36)"      "references dim_ci (id)"]
                          [:background     "VARCHAR(1000000)"]
                          [:functionality  "VARCHAR(1000000)"]
                          [:critical_level :integer]
                          [:business_owner "varchar(50)"]
                          [:development    "varchar(50)"]
                          [:ops_support    "varchar(50)"]
                          [:users          "varchar(50)"]
                          [:version        "varchar(50)"]
                          [:cfengine_classes "varchar(50)"]
                          [:nagios_classes "varchar(50)"]
                          [:created_at     :timestamp]
                          [:stopped_at     :timestamp])
      (j/create-table-ddl :dim_event
                          [:id             "varchar(36)"      "PRIMARY KEY"]
                          [:name           "varchar(50)"]
                          [:unit           "varchar(50)"]
                          [:created_at     :timestamp]
                          [:stopped_at     :timestamp])
      (j/create-table-ddl :dim_environment
                          [:id             "varchar(36)"      "PRIMARY KEY"]
                          [:environment    "varchar(50)"]
                          [:created_at     :timestamp]
                          [:stopped_at     :timestamp])      
      (j/create-table-ddl :fact_measurement
                          [:id             "varchar(36)"      "PRIMARY KEY"]
                          [:time_id        "varchar(36)"      "references dim_time (id)"]
                          [:ci_id          "varchar(36)"      "references dim_ci (id)"]
                          [:event_id       "varchar(36)"      "references dim_event (id)"]
                          [:environment_id "varchar(36)"      "references dim_environment (id)"]
                          [:application_id "varchar(36)"      "references dim_ci (id)"]
                          [:value          :integer     "not null"])
      (j/create-table-ddl :fact_host_application
                          [:id             "varchar(36)"      "PRIMARY KEY"]
                          [:measurement_id "varchar(36)"      "references fact_measurement (id)"]
                          [:ci_id          "varchar(36)"      "references dim_ci (id)"]
                          [:application_id "varchar(36)"      "references dim_ci (id)"]
                          [:time_id        "varchar(36)"      "references dim_time (id)"])
      (j/create-table-ddl :temp_apps_for_host
                          [:id             "varchar(36)"      "PRIMARY KEY"]
                          [:hostname       "varchar(50)"]
                          [:application_id "varchar(36)"      "references dim_ci (id)"]
                          [:created_at     :timestamp]
                          [:stopped_at     :timestamp])
      )))

(defmethod create! "postgresql"  
  ^{:doc "Create the data scheme for a postgresql database"}
  [ds]
  (j/with-connection ds
    (j/do-commands
      (j/create-table-ddl :dim_time
                          [:id             :uuid      "PRIMARY KEY"]
                          [:timestamp      :timestamp]
                          [:minute         :integer]
                          [:hour           :integer]
                          [:second         :integer]
                          [:day_of_month   :integer]
                          [:day_of_week    :integer]
                          [:week           :integer]
                          [:month          :integer]
                          [:quarter        :integer]
                          [:year           :integer]
                          [:created_at     :timestamp]
                          [:stopped_at     :timestamp])
      (j/create-table-ddl :dim_ci
                          [:id             :uuid      "PRIMARY KEY"]
                          [:name           "varchar(50)"]
                          [:cpu            "varchar(50)"]
                          [:num_cpus       :integer]
                          [:memory         :integer]
                          [:parent         :uuid      "references dim_ci (id)"]
                          [:background     :text]
                          [:functionality  :text]
                          [:critical_level :integer]
                          [:business_owner "varchar(50)"]
                          [:development    "varchar(50)"]
                          [:ops_support    "varchar(50)"]
                          [:users          "varchar(50)"]
                          [:version        "varchar(50)"]
                          [:cfengine_classes "varchar(50)"]
                          [:nagios_classes "varchar(50)"]
                          [:created_at     :timestamp]
                          [:stopped_at     :timestamp])
      (j/create-table-ddl :dim_event
                          [:id             :uuid      "PRIMARY KEY"]
                          [:name           "varchar(50)"]
                          [:unit           "varchar(50)"]
                          [:created_at     :timestamp]
                          [:stopped_at     :timestamp])
      (j/create-table-ddl :dim_environment
                          [:id             :uuid      "PRIMARY KEY"]
                          [:environment    "varchar(50)"]
                          [:created_at     :timestamp]
                          [:stopped_at     :timestamp])
      (j/create-table-ddl :fact_measurement
                          [:id             :uuid      "PRIMARY KEY"]
                          [:time_id        :uuid      "references dim_time (id)"]
                          [:ci_id          :uuid      "references dim_ci (id)"]
                          [:event_id       :uuid      "references dim_event (id)"]
                          [:environment_id :uuid      "references dim_environment (id)"]
                          [:value          :integer   "not null"])
      (j/create-table-ddl :fact_host_application
                          [:id             :uuid      "PRIMARY KEY"]
                          [:measurement_id :uuid      "references fact_measurement (id)"]
                          [:ci_id          :uuid      "references dim_ci (id)"]
                          [:application_id :uuid      "references dim_ci (id)"]
                          [:time_id        :uuid      "references dim_time (id)"])
      (j/create-table-ddl :temp_apps_for_host
                          [:id             :uuid      "PRIMARY KEY"]
                          [:hostname       "varchar(50)"]
                          [:application_id :uuid      "references dim_ci (id)"]
                          [:created_at     :timestamp]
                          [:stopped_at     :timestamp])
      )))

(defmacro with-database!
  "Execute a body with as context a database with the datascheme"
  [ds & body]
  `(let [ds# ~ds]
     (clear! ds#)
     (create! ds#)
     ~@body
     (clear! ds#)))
  
   