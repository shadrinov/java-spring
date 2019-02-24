CREATE TABLE users (
	id_user  BIGINT NOT NULL,
	username VARCHAR(256) NOT NULL,
	password VARCHAR(256) NOT NULL,

	PRIMARY KEY (id_user)
);

CREATE SEQUENCE gen_id_user;

CREATE TABLE manufacturers (
	id_manufacturer BIGINT NOT NULL,
	title           VARCHAR(1024) NOT NULL,

	PRIMARY KEY (id_manufacturer)
);

CREATE SEQUENCE gen_id_manufacturer;

CREATE TABLE models (
	id_model        BIGINT NOT NULL,
	id_manufacturer BIGINT NOT NULL,
	title           VARCHAR(1024) NOT NULL,
	yearstart       INT,
	yearend         INT,

	PRIMARY KEY (id_model),
	FOREIGN KEY (id_manufacturer) REFERENCES manufacturers(id_manufacturer)
);

CREATE SEQUENCE gen_id_model;
