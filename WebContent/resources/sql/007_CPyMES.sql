-- =============================================
-- Author:  	Julian Barragan Verano
-- Create date: 07-MAY-2015
-- Description: CREATE TABLES Project CPyMES - IVR Seguridad
-- Name SQL: 007_CPyMES.sql
-- =============================================

GRANT SELECT,INSERT, DELETE, UPDATE ON KOU_ADM.ALARMA_PYMES TO KOU_PROCESS;
GRANT SELECT ON KOU_ADM.ALARMA_PYMES TO usrtmxivr2;
GRANT EXECUTE  ON KOU_ADM."TRUNCAR_ALARMA_PYMES"  TO KOU_PROCESS;

GRANT SELECT,INSERT, DELETE, UPDATE ON KOU_ADM.ALARMA_PYMES_SERVICIO_NIT TO KOU_PROCESS;
GRANT SELECT ON KOU_ADM.ALARMA_PYMES_SERVICIO_NIT to usrtmxivr2;
GRANT EXECUTE  ON KOU_ADM."TRUNCAR_ALARMA_SERVICIO_NIT"  TO KOU_PROCESS;

GRANT SELECT ON KOU_ADM.ALARMA_PYMES_VIEW TO KOU_PROCESS;
GRANT SELECT ON KOU_ADM.ALARMA_PYMES_VIEW TO usrtmxivr2;
