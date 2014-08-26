--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: dim_ci; Type: TABLE; Schema: public; Owner: cmis; Tablespace: 
--

CREATE TABLE dim_ci (
    id uuid NOT NULL,
    name character varying(255),
    cpu character varying(255),
    num_cpus integer,
    memory integer,
    parent uuid,
    background text,
    functionality text,
    critical_level integer,
    business_owner character varying(255),
    development character varying(255),
    ops_support character varying(255),
    users character varying(255),
    version character varying(255),
    cfengine_classes character varying(255),
    nagios_classes character varying(255),
    created_at timestamp without time zone,
    stopped_at timestamp without time zone,
    parent_id uuid
);


ALTER TABLE public.dim_ci OWNER TO cmis;

--
-- Name: dim_environment; Type: TABLE; Schema: public; Owner: cmis; Tablespace: 
--

CREATE TABLE dim_environment (
    id uuid NOT NULL,
    environment character varying(255),
    created_at timestamp without time zone,
    stopped_at timestamp without time zone
);


ALTER TABLE public.dim_environment OWNER TO cmis;

--
-- Name: dim_event; Type: TABLE; Schema: public; Owner: cmis; Tablespace: 
--

CREATE TABLE dim_event (
    id uuid NOT NULL,
    name character varying(255),
    unit character varying(255),
    created_at timestamp without time zone,
    stopped_at timestamp without time zone
);


ALTER TABLE public.dim_event OWNER TO cmis;

--
-- Name: dim_parameter; Type: TABLE; Schema: public; Owner: cmis; Tablespace: 
--

CREATE TABLE dim_parameter (
    id uuid NOT NULL,
    name character varying(255),
    unit character varying(255),
    created_at timestamp without time zone,
    stopped_at timestamp without time zone
);


ALTER TABLE public.dim_parameter OWNER TO cmis;

--
-- Name: dim_site; Type: TABLE; Schema: public; Owner: cmis; Tablespace: 
--

CREATE TABLE dim_site (
    id uuid NOT NULL,
    site character varying(255),
    created_at timestamp without time zone,
    stopped_at timestamp without time zone
);


ALTER TABLE public.dim_site OWNER TO cmis;

--
-- Name: dim_time; Type: TABLE; Schema: public; Owner: cmis; Tablespace: 
--

CREATE TABLE dim_time (
    id uuid NOT NULL,
    "timestamp" timestamp without time zone,
    minute integer,
    hour integer,
    second integer,
    day_of_month integer,
    day_of_week integer,
    week integer,
    month integer,
    quarter integer,
    year integer,
    created_at timestamp without time zone,
    stopped_at timestamp without time zone
);


ALTER TABLE public.dim_time OWNER TO cmis;

--
-- Name: fact_host_application; Type: TABLE; Schema: public; Owner: cmis; Tablespace: 
--

CREATE TABLE fact_host_application (
    id uuid NOT NULL,
    measurement_id uuid,
    ci_id uuid,
    application_id uuid,
    time_id uuid
);


ALTER TABLE public.fact_host_application OWNER TO cmis;

--
-- Name: fact_measurement; Type: TABLE; Schema: public; Owner: cmis; Tablespace: 
--

CREATE TABLE fact_measurement (
    id uuid NOT NULL,
    time_id uuid,
    ci_id uuid,
    event_id uuid,
    environment_id uuid,
    value integer NOT NULL,
    parameter_id uuid,
    site_id uuid
);


ALTER TABLE public.fact_measurement OWNER TO cmis;

--
-- Name: idempotent_nagios_archives; Type: TABLE; Schema: public; Owner: cmis; Tablespace: 
--

CREATE TABLE idempotent_nagios_archives (
    id uuid NOT NULL,
    key character varying(255),
    created_at timestamp without time zone
);


ALTER TABLE public.idempotent_nagios_archives OWNER TO cmis;

--
-- Name: temp_apps_for_host; Type: TABLE; Schema: public; Owner: cmis; Tablespace: 
--

CREATE TABLE temp_apps_for_host (
    id uuid NOT NULL,
    hostname character varying(255),
    application_id uuid,
    created_at timestamp without time zone,
    stopped_at timestamp without time zone
);


ALTER TABLE public.temp_apps_for_host OWNER TO cmis;

--
-- Name: dim_ci_pkey; Type: CONSTRAINT; Schema: public; Owner: cmis; Tablespace: 
--

ALTER TABLE ONLY dim_ci
    ADD CONSTRAINT dim_ci_pkey PRIMARY KEY (id);


--
-- Name: dim_environment_pkey; Type: CONSTRAINT; Schema: public; Owner: cmis; Tablespace: 
--

ALTER TABLE ONLY dim_environment
    ADD CONSTRAINT dim_environment_pkey PRIMARY KEY (id);


--
-- Name: dim_event_pkey; Type: CONSTRAINT; Schema: public; Owner: cmis; Tablespace: 
--

ALTER TABLE ONLY dim_event
    ADD CONSTRAINT dim_event_pkey PRIMARY KEY (id);


--
-- Name: dim_parameter_pkey; Type: CONSTRAINT; Schema: public; Owner: cmis; Tablespace: 
--

ALTER TABLE ONLY dim_parameter
    ADD CONSTRAINT dim_parameter_pkey PRIMARY KEY (id);


--
-- Name: dim_site_pkey; Type: CONSTRAINT; Schema: public; Owner: cmis; Tablespace: 
--

ALTER TABLE ONLY dim_site
    ADD CONSTRAINT dim_site_pkey PRIMARY KEY (id);


--
-- Name: dim_time_pkey; Type: CONSTRAINT; Schema: public; Owner: cmis; Tablespace: 
--

ALTER TABLE ONLY dim_time
    ADD CONSTRAINT dim_time_pkey PRIMARY KEY (id);


--
-- Name: fact_host_application_pkey; Type: CONSTRAINT; Schema: public; Owner: cmis; Tablespace: 
--

ALTER TABLE ONLY fact_host_application
    ADD CONSTRAINT fact_host_application_pkey PRIMARY KEY (id);


--
-- Name: fact_measurement_pkey; Type: CONSTRAINT; Schema: public; Owner: cmis; Tablespace: 
--

ALTER TABLE ONLY fact_measurement
    ADD CONSTRAINT fact_measurement_pkey PRIMARY KEY (id);


--
-- Name: idempotent_nagios_archives_pkey; Type: CONSTRAINT; Schema: public; Owner: cmis; Tablespace: 
--

ALTER TABLE ONLY idempotent_nagios_archives
    ADD CONSTRAINT idempotent_nagios_archives_pkey PRIMARY KEY (id);


--
-- Name: temp_apps_for_host_pkey; Type: CONSTRAINT; Schema: public; Owner: cmis; Tablespace: 
--

ALTER TABLE ONLY temp_apps_for_host
    ADD CONSTRAINT temp_apps_for_host_pkey PRIMARY KEY (id);


--
-- Name: idx_fact_host_application_application_id; Type: INDEX; Schema: public; Owner: cmis; Tablespace: 
--

CREATE INDEX idx_fact_host_application_application_id ON fact_host_application USING btree (application_id);


--
-- Name: idx_fact_host_application_ci_id; Type: INDEX; Schema: public; Owner: cmis; Tablespace: 
--

CREATE INDEX idx_fact_host_application_ci_id ON fact_host_application USING btree (ci_id);


--
-- Name: idx_fact_host_application_measurement_id; Type: INDEX; Schema: public; Owner: cmis; Tablespace: 
--

CREATE INDEX idx_fact_host_application_measurement_id ON fact_host_application USING btree (measurement_id);


--
-- Name: idx_fact_measurement_event_id; Type: INDEX; Schema: public; Owner: cmis; Tablespace: 
--

CREATE INDEX idx_fact_measurement_event_id ON fact_measurement USING btree (event_id);


--
-- Name: idx_fact_measurement_time_id; Type: INDEX; Schema: public; Owner: cmis; Tablespace: 
--

CREATE INDEX idx_fact_measurement_time_id ON fact_measurement USING btree (time_id);


--
-- Name: dim_ci_parent_fkey; Type: FK CONSTRAINT; Schema: public; Owner: cmis
--

ALTER TABLE ONLY dim_ci
    ADD CONSTRAINT dim_ci_parent_fkey FOREIGN KEY (parent) REFERENCES dim_ci(id);


--
-- Name: fact_host_application_application_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: cmis
--

ALTER TABLE ONLY fact_host_application
    ADD CONSTRAINT fact_host_application_application_id_fkey FOREIGN KEY (application_id) REFERENCES dim_ci(id);


--
-- Name: fact_host_application_ci_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: cmis
--

ALTER TABLE ONLY fact_host_application
    ADD CONSTRAINT fact_host_application_ci_id_fkey FOREIGN KEY (ci_id) REFERENCES dim_ci(id);


--
-- Name: fact_host_application_measurement_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: cmis
--

ALTER TABLE ONLY fact_host_application
    ADD CONSTRAINT fact_host_application_measurement_id_fkey FOREIGN KEY (measurement_id) REFERENCES fact_measurement(id);


--
-- Name: fact_host_application_time_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: cmis
--

ALTER TABLE ONLY fact_host_application
    ADD CONSTRAINT fact_host_application_time_id_fkey FOREIGN KEY (time_id) REFERENCES dim_time(id);


--
-- Name: fact_measurement_ci_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: cmis
--

ALTER TABLE ONLY fact_measurement
    ADD CONSTRAINT fact_measurement_ci_id_fkey FOREIGN KEY (ci_id) REFERENCES dim_ci(id);


--
-- Name: fact_measurement_environment_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: cmis
--

ALTER TABLE ONLY fact_measurement
    ADD CONSTRAINT fact_measurement_environment_id_fkey FOREIGN KEY (environment_id) REFERENCES dim_environment(id);


--
-- Name: fact_measurement_event_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: cmis
--

ALTER TABLE ONLY fact_measurement
    ADD CONSTRAINT fact_measurement_event_id_fkey FOREIGN KEY (event_id) REFERENCES dim_event(id);


--
-- Name: fact_measurement_time_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: cmis
--

ALTER TABLE ONLY fact_measurement
    ADD CONSTRAINT fact_measurement_time_id_fkey FOREIGN KEY (time_id) REFERENCES dim_time(id);


--
-- Name: temp_apps_for_host_application_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: cmis
--

ALTER TABLE ONLY temp_apps_for_host
    ADD CONSTRAINT temp_apps_for_host_application_id_fkey FOREIGN KEY (application_id) REFERENCES dim_ci(id);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

