(ns cmis.db
  ^{:doc "Module to create the dataschema"}
  (:require [clojure.java.jdbc :as j]))

(defmulti clear!
  "Clear the data scheme on the given datasource"
  (fn [ds] (:subprotocol ds)))

(defmethod clear! "hsqldb"
  [ds]
  (j/db-do-commands ds
   "DROP TABLE TEMP_APPS_FOR_HOST IF EXISTS"
   "DROP TABLE FACT_HOST_APPLICATION IF EXISTS"
   "DROP TABLE FACT_MEASUREMENT IF EXISTS"
   "DROP TABLE DIM_TIME IF EXISTS"
   "DROP TABLE DIM_CI IF EXISTS"
   "DROP TABLE DIM_EVENT IF EXISTS"
   "DROP TABLE DIM_ENVIRONMENT IF EXISTS"
   "DROP TABLE IDEMPOTENT_NAGIOS_ARCHIVES IF EXISTS"
   ))

(defmethod clear! :default
  [ds]
  (j/db-do-commands
   "DROP TABLE IF EXISTS TEMP_APPS_FOR_HOST CASCADE"
   "DROP TABLE IF EXISTS FACT_HOST_APPLICATION CASCADE"
   "DROP TABLE IF EXISTS FACT_MEASUREMENT CASCADE"
   "DROP TABLE IF EXISTS DIM_TIME CASCADE"
   "DROP TABLE IF EXISTS DIM_CI CASCADE"
   "DROP TABLE IF EXISTS DIM_EVENT CASCADE"
   "DROP TABLE IF EXISTS DIM_ENVIRONMENT CASCADE"
   "DROP TABLE IF EXISTS IDEMPOTENT_NAGIOS_ARCHIVES CASCADE"
   ))

(defmulti create!
  "Create the data scheme on the given datasource"
  (fn [ds] (:subprotocol ds)))

(defmethod create! "hsqldb"
  ^{:doc "Create the data scheme for a hypersonic database"}
  [ds]
  (j/db-do-commands ds
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
                       [:name           "varchar(255)"]
                       [:cpu            "varchar(255)"]
                       [:num_cpus       :integer]
                       [:memory         :integer]
                       [:parent         "varchar(36)"      "references dim_ci (id)"]
                       [:background     "VARCHAR(1000000)"]
                       [:functionality  "VARCHAR(1000000)"]
                       [:critical_level :integer]
                       [:business_owner "varchar(255)"]
                       [:development    "varchar(255)"]
                       [:ops_support    "varchar(255)"]
                       [:users          "varchar(255)"]
                       [:version        "varchar(255)"]
                       [:cfengine_classes "varchar(255)"]
                       [:nagios_classes "varchar(255)"]
                       [:created_at     :timestamp]
                       [:stopped_at     :timestamp])
   (j/create-table-ddl :dim_event
                       [:id             "varchar(36)"      "PRIMARY KEY"]
                       [:name           "varchar(255)"]
                       [:unit           "varchar(255)"]
                       [:created_at     :timestamp]
                       [:stopped_at     :timestamp])
   (j/create-table-ddl :dim_environment
                       [:id             "varchar(36)"      "PRIMARY KEY"]
                       [:environment    "varchar(255)"]
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
                       [:hostname       "varchar(255)"]
                       [:application_id "varchar(36)"      "references dim_ci (id)"]
                       [:created_at     :timestamp]
                       [:stopped_at     :timestamp])
   (j/create-table-ddl :idempotent_nagios_archives
                       [:id             "varchar(36)"      "PRIMARY KEY"]
                       [:key            "varchar(255)"]
                       [:created_at     :timestamp])
   
   ))

(defmethod create! "postgresql"  
  ^{:doc "Create the data scheme for a postgresql database"}
  [ds]
  (j/db-do-commands ds
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
                       [:name           "varchar(255)"]
                       [:cpu            "varchar(255)"]
                       [:num_cpus       :integer]
                       [:memory         :integer]
                       [:parent         :uuid      "references dim_ci (id)"]
                       [:background     :text]
                       [:functionality  :text]
                       [:critical_level :integer]
                       [:business_owner "varchar(255)"]
                       [:development    "varchar(255)"]
                       [:ops_support    "varchar(255)"]
                       [:users          "varchar(255)"]
                       [:version        "varchar(255)"]
                       [:cfengine_classes "varchar(255)"]
                       [:nagios_classes "varchar(255)"]
                       [:created_at     :timestamp]
                       [:stopped_at     :timestamp])
   (j/create-table-ddl :dim_event
                       [:id             :uuid      "PRIMARY KEY"]
                       [:name           "varchar(255)"]
                       [:unit           "varchar(255)"]
                       [:created_at     :timestamp]
                       [:stopped_at     :timestamp])
   (j/create-table-ddl :dim_environment
                       [:id             :uuid      "PRIMARY KEY"]
                       [:environment    "varchar(255)"]
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
                       [:hostname       "varchar(255)"]
                       [:application_id :uuid      "references dim_ci (id)"]
                       [:created_at     :timestamp]
                       [:stopped_at     :timestamp])
   (j/create-table-ddl :idempotent_nagios_archives
                       [:id             :uuid      "PRIMARY KEY"]
                       [:key            "varchar(255)"]
                       [:created_at     :timestamp])
   ))

(defmacro with-database!
  "Execute a body with as context a database with the datascheme"
  [ds & body]
  `(let [ds# ~ds]
     (clear! ds#)
     (create! ds#)
     ~@body
     (clear! ds#)))
  
   
