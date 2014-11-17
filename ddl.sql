create table dim_ci
(
	id 		uuid PRIMARY KEY,
	created_at 	timestamp,
	last_update 	timestamp,
	parent 		uuid,	
	name 		varchar(50),
	type		varchar(50),
	state		varchar(50),
	cpu_model 	varchar(50),
	num_cpu_cores 	int,
	memory		bigint,
	environment	varchar(50)
);

create table dim_parameter
(
	id		uuid PRIMARY KEY,
	name		varchar(50),
	unit		varchar(50)
);

create table dim_time
(
	id		uuid PRIMARY KEY,
	hour		integer,
	minute		integer,
	second		integer,	
	day_of_week	integer,
	day_of_month	integer,
	week		integer,
	month		integer,
	quarter		integer,
	year		integer	
);

create table dim_site
(
	id		uuid PRIMARY KEY,
	site		varchar(50)
);
	

create table fact_measurement
(
	id		uuid PRIMARY KEY,
	ci_id		uuid references dim_ci(id),
	parameter_id	uuid references dim_parameter(id),
	time_id		uuid references dim_time(id),
	site_id		uuid references dim_site(id),
	value		integer
);