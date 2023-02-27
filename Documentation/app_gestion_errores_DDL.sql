DROP SCHEMA IF EXISTS public CASCADE; 
CREATE SCHEMA public;

CREATE SEQUENCE seq_accion_usuario START WITH 1 MINVALUE 1 INCREMENT BY 1 CACHE 20;
CREATE SEQUENCE seq_aplicacion_error START WITH 1 MINVALUE 1 INCREMENT BY 1 CACHE 20;
CREATE SEQUENCE seq_nivel_error START WITH 1 MINVALUE 1 INCREMENT BY 1 CACHE 20;
CREATE SEQUENCE seq_origen_error START WITH 1 MINVALUE 1 INCREMENT BY 1 CACHE 20;
CREATE SEQUENCE seq_tipo_accion START WITH 1 MINVALUE 1 INCREMENT BY 1 CACHE 20;
CREATE SEQUENCE seq_trazabilidad_codigo START WITH 1 MINVALUE 1 INCREMENT BY 1 CACHE 20;

CREATE TABLE accion_usuario (
    id_accion_usuario   INTEGER NOT NULL DEFAULT nextval('seq_accion_usuario'),
    id_aplicacion_error INTEGER NOT NULL,
    id_nivel_error      INTEGER NOT NULL,
    id_tipo_accion      INTEGER NOT NULL,
    fecha_hora_accion   DATE NOT NULL,
    accion_usuario      VARCHAR(300)
);

CREATE INDEX ix_accion_usuario_ref_aplicacion_error ON
    accion_usuario (
        id_aplicacion_error
    ASC );

ALTER TABLE accion_usuario ADD CONSTRAINT pk_user_action PRIMARY KEY ( id_accion_usuario );

CREATE TABLE aplicacion_error (
    id_aplicacion_error INTEGER NOT NULL DEFAULT nextval('seq_aplicacion_error'),
    nombre_aplicacion  	VARCHAR(80) NOT NULL,
    titulo_error        VARCHAR(80),
    descripcion_error   VARCHAR(3500),
    correo_usuario      VARCHAR(50),
    hora_error 			DATE,
	navegador_usuario 	VARCHAR(150),
	ip_usuario 			VARCHAR(50)
);

ALTER TABLE aplicacion_error ADD CONSTRAINT pk_application_error PRIMARY KEY ( id_aplicacion_error );

CREATE TABLE nivel_error (
    id_nivel_error INTEGER NOT NULL DEFAULT nextval('seq_nivel_error'),
    nombre_nivel   VARCHAR(10) NOT NULL
);

ALTER TABLE nivel_error ADD CONSTRAINT pk_nivel_error PRIMARY KEY ( id_nivel_error );

CREATE TABLE origen_error (
    id_origen_error INTEGER NOT NULL DEFAULT nextval('seq_origen_error'),
    nombre_origen   VARCHAR(10) NOT NULL
);

ALTER TABLE origen_error ADD CONSTRAINT pk_origen_error PRIMARY KEY ( id_origen_error );

CREATE TABLE tipo_accion (
    id_tipo_accion INTEGER NOT NULL DEFAULT nextval('seq_tipo_accion'),
    nombre_accion  VARCHAR(12) NOT NULL
);

ALTER TABLE tipo_accion ADD CONSTRAINT pk_tipo_accion PRIMARY KEY ( id_tipo_accion );

CREATE TABLE trazabilidad_codigo (
    id_trazabilidad      INTEGER NOT NULL DEFAULT nextval('seq_trazabilidad_codigo'),
    id_applicacion_error INTEGER NOT NULL,
    id_origen_error      INTEGER NOT NULL,
    traza_error          TEXT NOT NULL
);

CREATE INDEX ix_trazabilidad_codigo_ref_aplicacion_error ON
    trazabilidad_codigo (
        id_applicacion_error
    ASC );

ALTER TABLE trazabilidad_codigo ADD CONSTRAINT pk_trazabilidad_codigo PRIMARY KEY ( id_trazabilidad );

ALTER TABLE accion_usuario
     ADD CONSTRAINT fk_accion_usuario_ref_ae FOREIGN KEY ( id_aplicacion_error )
         REFERENCES aplicacion_error ( id_aplicacion_error );

ALTER TABLE accion_usuario
    ADD CONSTRAINT fk_accion_usuario_ref_nivel_error FOREIGN KEY ( id_nivel_error )
        REFERENCES nivel_error ( id_nivel_error );
		
ALTER TABLE accion_usuario
     ADD CONSTRAINT fk_accion_usuario_ref_tipo_error FOREIGN KEY ( id_tipo_accion )
         REFERENCES tipo_accion ( id_tipo_accion );

ALTER TABLE trazabilidad_codigo
    ADD CONSTRAINT fk_trazabilidad_codigo_ref_origen_error FOREIGN KEY ( id_origen_error )
        REFERENCES origen_error ( id_origen_error );

