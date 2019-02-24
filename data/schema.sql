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
	year_start      INT,
	year_end        INT,

	PRIMARY KEY (id_model),
	FOREIGN KEY (id_manufacturer) REFERENCES manufacturers(id_manufacturer)
);

CREATE SEQUENCE gen_id_model;
