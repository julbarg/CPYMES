-- =============================================
-- Author:  	Julian Barragan Verano
-- Create date: 13-MAY-2015
-- Description: ROLLBACK TABLES Project CPyMES - IVR Seguridad
-- Name SQL: 009_CPyMES.sql
-- =============================================


CREATE TABLE cpymes.nit_onix (
  id BIGINT NOT NULL,
  code_service VARCHAR(100) NOT NULL,
  nit VARCHAR(100) NOT NULL,
  PRIMARY KEY (id));
  
  
CREATE TABLE cpymes.parametro (
  name VARCHAR(45) NOT NULL,
  value VARCHAR(255) NOT NULL,
  PRIMARY KEY (name));
