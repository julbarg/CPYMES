-- =============================================
-- Author:  	Julian Barragan Verano
-- Create date: 13-MAY-2015
-- Description: ROLLBACK TABLES Project CPyMES - IVR Seguridad
-- Name SQL: 008_CPyMES.sql
-- =============================================



UPDATE `cpymes`.`alarm_catalog` SET `criticality`='warning' WHERE `id`='73';
UPDATE `cpymes`.`alarm_catalog` SET `criticality`='alert' WHERE `id`='100';
UPDATE `cpymes`.`alarm_catalog` SET `criticality`='notice' WHERE `id`='105';
UPDATE `cpymes`.`alarm_catalog` SET `criticality`='notice' WHERE `id`='107';
UPDATE `cpymes`.`alarm_catalog` SET `criticality`='warning' WHERE `id`='210';
UPDATE `cpymes`.`alarm_catalog` SET `criticality`='info' WHERE `id`='234';


CREATE TABLE cpymes.nit_onix (
  id BIGINT NOT NULL,
  code_service VARCHAR(100) NOT NULL,
  nit VARCHAR(100) NOT NULL,
  PRIMARY KEY (id));
  
  
CREATE TABLE cpymes.parametro (
  name VARCHAR(45) NOT NULL,
  value VARCHAR(255) NOT NULL,
  PRIMARY KEY (name));
