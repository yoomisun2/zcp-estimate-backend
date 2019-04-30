DROP TABLE IF EXISTS generals;
DROP TABLE IF EXISTS iks_vm_versions;
DROP TABLE IF EXISTS iks_vms;
DROP TABLE IF EXISTS iks_storage_versions;
DROP TABLE IF EXISTS iks_file_storages;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS addons;
DROP TABLE IF EXISTS templates;
DROP TABLE IF EXISTS msp_cost_versions;
DROP TABLE IF EXISTS msp_costs;
DROP TABLE IF EXISTS projects;
DROP TABLE IF EXISTS environments;
DROP TABLE IF EXISTS volumes;
DROP TABLE IF EXISTS estimates;
DROP TABLE IF EXISTS estimate_items;

CREATE TABLE generals (
	id 						INT NOT NULL AUTO_INCREMENT,
	version 				INT NOT NULL,
	ibm_dc_rate 			FLOAT NOT NULL,
	platform_cpu_per_worker INT NOT NULL,
	platform_memory_per_worker INT NOT NULL,
	exchange_rate 			INT NOT NULL,
	ip_allocation			INT NOT NULL,
	description 			VARCHAR(200) NULL,
	created 				VARCHAR(20) NULL,
	created_dt  			TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT pk_generals_id PRIMARY KEY (id)
);
CREATE INDEX ix_generals_version on generals (version);

CREATE TABLE iks_vm_versions (
	id				INT NOT NULL AUTO_INCREMENT,			
	version			INT NOT NULL,
	description		VARCHAR(200) NULL,
	created			VARCHAR(20) NULL,
	created_dt		TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT pk_iks_vm_versions_id PRIMARY KEY (id)
);
CREATE INDEX ix_iks_vm_versions_version on iks_vm_versions (version);

CREATE TABLE iks_vms (
	id							INT NOT NULL AUTO_INCREMENT,
	name						VARCHAR(100) NOT NULL,
	core						INT NOT NULL,
	memory						INT NOT NULL,
	nw_speed					INT NOT NULL,
	shared_price_per_hour		INT NOT NULL,
	dedicated_price_per_hour	INT NOT NULL,
	iks_vm_version_id			INT NOT NULL,
	CONSTRAINT pk_iks_vms_id PRIMARY KEY (id)
);
CREATE INDEX ix_iks_vms_iks_vm_version_id on iks_vms (iks_vm_version_id);

CREATE TABLE iks_storage_versions (
	id				INT NOT NULL AUTO_INCREMENT,			
	version			INT NOT NULL,
	object_storage_price_per_day	INT NOT NULL,
	description		VARCHAR(200) NULL,
	created			VARCHAR(20) NULL,
	created_dt		TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT pk_iks_storage_versions_id PRIMARY KEY (id)
);
CREATE INDEX ix_iks_storage_versions_version on iks_storage_versions (version);

CREATE TABLE iks_file_storages (
	id							INT NOT NULL AUTO_INCREMENT,
	disk						INT NOT NULL,
	iops1_price_per_hour		FLOAT NOT NULL,
	iops2_price_per_hour		FLOAT NOT NULL,
	iops3_price_per_hour		FLOAT NOT NULL,
	iops4_price_per_hour		FLOAT NOT NULL,
	iks_storage_version_id		INT NOT NULL,
	CONSTRAINT pk_iks_file_storages_id PRIMARY KEY (id)
);
CREATE INDEX ix_iks_file_storages_iks_storage_version_id on iks_file_storages (iks_storage_version_id);

CREATE TABLE products (
	id						INT NOT NULL AUTO_INCREMENT,
	name					VARCHAR(100) NOT NULL,
	description 			VARCHAR(200) NULL,
	created 				VARCHAR(20) NULL,
	created_dt  			TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT pk_products_id PRIMARY KEY (id)
);

CREATE TABLE addons (
	id						INT NOT NULL AUTO_INCREMENT,
	service_name			VARCHAR(100) NOT NULL,
	application_name		VARCHAR(100) NOT NULL,
	cpu						INT,
	memory					INT,
	disk					INT,
	storage_type			VARCHAR(10),
	backup_yn				CHAR(1),
	description 			VARCHAR(200) NULL,
	product_id				INT NOT NULL,
	created 				VARCHAR(20) NULL,
	created_dt  			TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT pk_addons_id PRIMARY KEY (id)
);
CREATE INDEX ix_addons_product_id on addons (product_id);

CREATE TABLE templates (
	id						INT NOT NULL AUTO_INCREMENT,
	product_id				INT NOT NULL,
	estimate_type			VARCHAR(20) NOT NULL,
	service_name			VARCHAR(100) NOT NULL,
	classification_name 	VARCHAR(100) NOT NULL,
	classification_type		VARCHAR(20) NOT NULL,
	sort					INT NOT NULL,
	created 				VARCHAR(20),
	created_dt  			TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT pk_templates_id PRIMARY KEY (id)
);
CREATE INDEX ix_templates_product_id_estimate_type on templates (product_id, estimate_type);

CREATE TABLE msp_cost_versions (
	id				INT NOT NULL AUTO_INCREMENT,			
	version			INT NOT NULL,
	description		VARCHAR(200) NULL,
	created			VARCHAR(20) NULL,
	created_dt		TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT pk_msp_cost_versions_id PRIMARY KEY (id)
);
CREATE INDEX ix_msp_cost_versions_version on msp_cost_versions (version);

CREATE TABLE msp_costs (
	id							INT NOT NULL AUTO_INCREMENT,
	product_id					INT NOT NULL,
	alias						VARCHAR(20),
	memory						INT NOT NULL,
	cost						INT NOT NULL,
	msp_cost_version_id	INT NOT NULL,
	CONSTRAINT pk_msp_costs_id PRIMARY KEY (id)
);
CREATE INDEX ix_msp_costs_product_id on msp_costs (product_id);
CREATE INDEX ix_msp_costs_msp_cost_version_id on msp_costs (msp_cost_version_id);

CREATE TABLE projects (
	id				INT NOT NULL AUTO_INCREMENT,
	name			VARCHAR(100) NOT NULL,
	description		VARCHAR(200),
	created			VARCHAR(20),
	created_dt		TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT pk_projects_id PRIMARY KEY (id)
);

CREATE TABLE environments (
	id				INT NOT NULL AUTO_INCREMENT,
	name			VARCHAR(20) NOT NULL,
	project_id		INT NOT NULL,
	created			VARCHAR(20),
	created_dt		TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT pk_environments_id PRIMARY KEY (id)
);
CREATE INDEX ix_environments_project_id on environments (project_id);

CREATE TABLE volumes (
	id						INT NOT NULL AUTO_INCREMENT,
	environment_id			INT NOT NULL,
	app_name				VARCHAR(100) NOT NULL,
	app_memory_min			INT,
	app_memory_max			INT,
	replica_count			INT,
	pod_memory_request		INT,
	pod_memory_limit		INT,
	pod_cpu_request			INT,
	pod_cpu_limit			INT,
	pod_memory_request_sum	FLOAT,
	pod_memory_limit_sum	FLOAT,
	pod_cpu_request_sum		FLOAT,
	pod_cpu_limit_sum		FLOAT,
	created					VARCHAR(20),
	created_dt				TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT pk_volumes_id PRIMARY KEY (id)
);
CREATE INDEX ix_volumes_environment_id on volumes (environment_id);

CREATE TABLE estimates (
	id					INT NOT NULL AUTO_INCREMENT,
	project_id			INT NOT NULL,
	version				INT NOT NULL,
	label				VARCHAR(100),
	description			VARCHAR(200),
	general_id			INT NOT NULL,
	iks_vm_version_id			INT NOT NULL,
	iks_storage_version_id		INT NOT NULL,
	msp_cost_version_id		INT NOT NULL,
	created				VARCHAR(20),
	created_dt			TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT pk_estimates_id PRIMARY KEY (id)
);
CREATE INDEX ix_estimates_project_id on estimates (project_id);

CREATE TABLE estimate_items (
	id						INT NOT NULL AUTO_INCREMENT,
	estimate_id				INT NOT NULL,
	estimate_type			VARCHAR(20) NOT NULL,
	environment_id			INT NOT NULL,
	environment_name		VARCHAR(20) NOT NULL,
	product_id				INT NOT NULL,
	service_name			VARCHAR(100),
	classification_name		VARCHAR(100) NOT NULL,
	classification_type		VARCHAR(20) NOT NULL,
	addon_id				INT,
	addon_application_name VARCHAR(100),
	iks_vm_id				INT,
	hardware_type			VARCHAR(100),
	storage_type			VARCHAR(100),
	endurance_iops			INT,
	iks_file_storage_id		INT,
	number					INT,
	cores					INT,
	memory					INT,
	price_per_monthly		INT,
	price_per_yearly		INT,
	sort					INT NOT NULL,
	created					VARCHAR(20),
	created_dt				TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT pk_estimate_items_id PRIMARY KEY (id)
);
CREATE INDEX ix_estimate_items_estimate_id_environment_id_product_id on estimate_items (estimate_id, environment_id, product_id);

