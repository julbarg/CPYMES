package com.claro.cpymes.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.claro.cpymes.dto.AlarmaPymeIVRDTO;
import com.claro.cpymes.dto.DataDTO;
import com.claro.cpymes.dto.InfoTypeAlarmDTO;
import com.claro.cpymes.dto.ReportDTO;
import com.claro.cpymes.dto.RestoreEventAlarmDTO;
import com.claro.cpymes.entity.AlarmaPymeIVREntity;
import com.claro.cpymes.enums.StateEnum;


/**
 * DAO de AlarmaPymeIVREntity 
 * @author jbarragan
 *
 */
@Stateless
@LocalBean
public class AlarmaPymesIVRDAO extends TemplateIVRDAO<AlarmaPymeIVREntity> implements AlarmaPymesIVRDAORemote {

   private static Logger LOGGER = LogManager.getLogger(AlarmaPymesIVRDAO.class.getName());

   @Override
   public ArrayList<AlarmaPymeIVREntity> findByEstado(String estado) throws Exception {
      EntityManager entityManager = entityManagerFactory.createEntityManager();
      ArrayList<AlarmaPymeIVREntity> results = new ArrayList<AlarmaPymeIVREntity>();
      try {
         TypedQuery<AlarmaPymeIVREntity> query = entityManager.createNamedQuery("AlarmaPymeIVREntity.findByEstado",
            AlarmaPymeIVREntity.class);
         query.setParameter("estado", estado);
         results = (ArrayList<AlarmaPymeIVREntity>) query.getResultList();
      } catch (Exception e) {
         throw e;
      } finally {
         entityManager.close();
      }

      return results;
   }

   @Override
   public AlarmaPymeIVREntity findById(long id) throws Exception {
      EntityManager entityManager = entityManagerFactory.createEntityManager();
      AlarmaPymeIVREntity alarmaPymeIVR = entityManager.find(AlarmaPymeIVREntity.class, id);
      entityManager.close();

      return alarmaPymeIVR;
   }

   @Override
   public ArrayList<AlarmaPymeIVREntity> findByFilter(AlarmaPymeIVRDTO filterAlarm) throws Exception {
      ArrayList<AlarmaPymeIVREntity> result = new ArrayList<AlarmaPymeIVREntity>();
      EntityManager entityManager = entityManagerFactory.createEntityManager();
      try {
         StringBuffer comandQuery = new StringBuffer();
         comandQuery.append("SELECT a FROM AlarmaPymeIVREntity a WHERE ");
         comandQuery = validateString(comandQuery, filterAlarm.getTicketOnix(), "a.ticketOnix LIKE '%");
         comandQuery = validateString(comandQuery, filterAlarm.getIp(), "a.ip LIKE '%");
         comandQuery = validateString(comandQuery, filterAlarm.getClaseEquipo(), "a.claseEquipo LIKE '%");
         comandQuery = validateString(comandQuery, filterAlarm.getDescripcionAlarma(), "a.descripcionAlarma LIKE '%");
         comandQuery = validateString(comandQuery, filterAlarm.getTipoEvento(), "a.tipoEvento LIKE '%");
         comandQuery = validateString(comandQuery, filterAlarm.getCiudad(), "a.ciudad LIKE '%");
         comandQuery = validateString(comandQuery, filterAlarm.getDivision(), "a.division LIKE '%");
         comandQuery = validateDate(comandQuery, filterAlarm.getFechaInicio(), "a.fechaInicio = ");

         comandQuery.append(" a.estadoAlarma = 'A' ");

         TypedQuery<AlarmaPymeIVREntity> query = entityManager.createQuery(comandQuery.toString(),
            AlarmaPymeIVREntity.class);

         result = (ArrayList<AlarmaPymeIVREntity>) query.getResultList();
      } catch (Exception e) {
         throw e;
      } finally {
         entityManager.close();
      }

      return result;

   }

   /** 
    * Adiciona criterio de busqueda a la consulta si value no es nulo
    * @param comandQuery
    * @param value
    * @param name
    * @return Complemento a la consulta 
    */
   private StringBuffer validateString(StringBuffer comandQuery, String value, String name) {
      if (value != null && !value.isEmpty()) {
         comandQuery.append(name);
         comandQuery.append(value);
         comandQuery.append("%' AND ");

      }
      return comandQuery;
   }

   /**
    * Adiciona criterio de busqueda a la consulta si value no es nulo. Para fechas
    * @param comandQuery
    * @param value
    * @param name
    * @return
    */
   private StringBuffer validateDate(StringBuffer comandQuery, Date value, String name) {
      if (value != null) {
         comandQuery.append(name);
         comandQuery.append(value);
         comandQuery.append("%' AND ");

      }
      return comandQuery;
   }

   /**
    * Clarea las Alarmas que cumplan con los criterios
    * @param listRestore Lista de eventos de Restauracion a Clarear
    * @return Numero de registros clareados
    */
   @Override
   @TransactionAttribute(TransactionAttributeType.REQUIRED)
   public int clearAlarm(ArrayList<RestoreEventAlarmDTO> listRestore) throws Exception {
      int resultUpdate = 0;
      EntityManager entityManager = entityManagerFactory.createEntityManager();
      try {
         EntityTransaction entityTransaction = entityManager.getTransaction();
         entityTransaction.begin();

         for (RestoreEventAlarmDTO restore : listRestore) {
            resultUpdate = resultUpdate + clearAlarm(restore, entityManager);
         }

         LOGGER.info("RESTORE EVENT IVR- Alarmas Restauradas: " + resultUpdate);

         entityTransaction.commit();
      } catch (Exception e) {
         throw e;
      } finally {
         entityManager.close();
      }
      return resultUpdate;

   }

   /**
    * Ejecuta el SQL para clareo de Alarmas
    * @param restore
    * @param entityManager
    * @return
    * @throws Exception
    */
   private int clearAlarm(RestoreEventAlarmDTO restore, EntityManager entityManager) throws Exception {
      String[] eventNames = restore.getEventTrigger();
      Query query;
      if (eventNames.length == 1) {
         query = entityManager.createQuery(getQuery());
         query.setParameter("eventName", eventNames[0]);
      } else if (eventNames.length > 1) {
         query = entityManager.createQuery(getQuery(eventNames));
      } else {
         return 0;
      }

      query.setParameter("estado", StateEnum.INACTIVO.getValue());
      query.setParameter("ip", restore.getIp());
      query.setParameter("dateEnd", new Date());

      return query.executeUpdate();

   }

   private String getQuery() {
      String query = "UPDATE AlarmaPymeIVREntity a SET a.estadoAlarma=:estado, a.fechaFin=:dateEnd,"
         + "a.tiempoTotalFalla = EXTRACT(HOUR FROM :dateEnd - a.fechaInicio) "
         + " WHERE a.ip=:ip and a.claseEquipo = :eventName  and a.estadoAlarma != 'I'";
      return query;
   }

   private String getQuery(String[] eventNames) {
      String query = "UPDATE AlarmaPymeIVREntity a SET a.estadoAlarma=:estado, a.fechaFin=:dateEnd, "
         + "a.tiempoTotalFalla = EXTRACT(HOUR FROM :dateEnd - a.fechaInicio) " + " WHERE a.ip=:ip and ("
         + getEventNamesStr(eventNames) + ")" + " and a.estadoAlarma != 'I'";
      return query;
   }

   /**
    * Clarea las Alarmas que cumplan con los criterios
    * @param restore Evento a Restauracion a Clarear
    * @return Numero de registros clareados
    */
   @Override
   @TransactionAttribute(TransactionAttributeType.REQUIRED)
   public int clearAlarm(RestoreEventAlarmDTO restore) throws Exception {
      int resultUpdate = 0;
      EntityManager entityManager = entityManagerFactory.createEntityManager();
      try {
         EntityTransaction entityTransaction = entityManager.getTransaction();
         entityTransaction.begin();

         resultUpdate = resultUpdate + clearAlarm(restore, entityManager);

         entityTransaction.commit();
      } catch (Exception e) {
         throw e;
      } finally {
         entityManager.close();
      }

      return resultUpdate;

   }

   private String getEventNamesStr(String[] eventNames) {
      String eventNamesStr = "";
      for (int i = 0; i < eventNames.length - 1; i++) {
         eventNamesStr = eventNamesStr + " a.claseEquipo = '" + eventNames[i] + "' OR ";
      }
      eventNamesStr = eventNamesStr + " a.claseEquipo = '" + eventNames[eventNames.length - 1] + "' ";

      return eventNamesStr;
   }

   @Override
   @TransactionAttribute(TransactionAttributeType.REQUIRED)
   public AlarmaPymeIVREntity updateAlarm(AlarmaPymeIVREntity alarmaIVR) throws Exception {
      EntityManager entityManager = entityManagerFactory.createEntityManager();
      try {
         EntityTransaction entityTransaction = entityManager.getTransaction();
         entityTransaction.begin();
         if (!(existSimilar(alarmaIVR, entityManager))) {
            alarmaIVR = entityManager.merge(alarmaIVR);
            entityTransaction.commit();
            entityManager.close();
            return alarmaIVR;
         }
         entityTransaction.commit();
      } catch (Exception e) {
         throw e;
      } finally {
         entityManager.close();
      }

      return null;
   }

   /**
    * Valida si ta existe una alarma similar
    * @param alarm
    * @param entityManager
    * @return true si existe un registro similar
    */
   private boolean existSimilar(AlarmaPymeIVREntity alarm, EntityManager entityManager) {
      boolean exist = false;
      try {
         TypedQuery<AlarmaPymeIVREntity> query = entityManager.createNamedQuery("AlarmaPymeIVREntity.findSimiliar",
            AlarmaPymeIVREntity.class);
         query.setParameter("estado", StateEnum.ACTIVO.getValue());
         query.setParameter("eventName", alarm.getClaseEquipo());
         query.setParameter("ip", alarm.getIp());
         exist = query.setFirstResult(1).setMaxResults(1).getResultList().size() > 0;

      } catch (Exception e) {
         LOGGER.error("Error buscando registros similares en IVR", e);
         return exist;
      }

      return exist;
   }

   @SuppressWarnings("unchecked")
   @Override
   public ArrayList<DataDTO> findDataByFilter(ReportDTO report) throws Exception {
      ArrayList<DataDTO> results = new ArrayList<DataDTO>();
      EntityManager entityManager = entityManagerFactory.createEntityManager();
      try {
         StringBuffer sql = getSQLFindByFilter(report);
         Query query = entityManager.createNativeQuery(sql.toString());
         query = getParameterFindByFilter(query, report);
         List<Object[]> listResults = query.getResultList();
         for (Object[] object : listResults) {
            DataDTO dataDTO = createData(object);
            results.add(dataDTO);
         }
         LOGGER.info("RESULT FIND DATA: " + results.size());

      } catch (Exception e) {
         throw e;
      } finally {
         entityManager.close();
      }

      return results;

   }

   private StringBuffer getSQLFindByFilter(ReportDTO report) {
      StringBuffer sql = new StringBuffer();

      sql.append("SELECT ");
      sql.append("A.ID_ALARMA_PYMES,  ");
      sql.append("A.TICKET_ONIX, ");
      sql.append("A.IP, ");
      sql.append("A.CLASE_EQUIPO, ");

      sql.append("A.DESCRIPCION_ALARMA, ");

      sql.append("A.TIPO_EVENTO, ");
      sql.append("A.CIUDAD, ");
      sql.append("A.DIVISION, ");
      sql.append("TO_CHAR(A.FECHA_INICIO, 'DD-MON-YYYY HH24:MI:SS'), ");
      sql.append("TO_CHAR(A.FECHA_ESPERANZA, 'DD-MON-YYYY HH24:MI:SS'), ");
      sql.append("TO_CHAR(A.FECHA_FIN, 'DD-MON-YYYY HH24:MI:SS'), ");
      sql.append("A.TIEMPO_TOTAL_FALLA, ");
      sql.append("TO_CHAR(A.FECHA_MODIFICACION, 'DD-MON-YYYY HH24:MI:SS'), ");
      sql.append("A.USUARIO_MODIFICACION, ");
      sql.append("A.ESTADO_ALARMA, ");
      sql.append("N.CODIGO_SERVICIO, ");
      sql.append("N.NIT ");
      sql.append("FROM ALARMA_PYMES A  ");
      sql.append("LEFT JOIN ALARMA_PYMES_SERVICIO_NIT N ON (A.ID_ALARMA_PYMES = N.ID_ALARMA_PYMES) ");
      sql.append("WHERE 1 = 1 ");

      sql = getFiltersFindByFilters(sql, report);

      LOGGER.info("SQL: " + sql.toString());

      return sql;

   }

   private StringBuffer getFiltersFindByFilters(StringBuffer sql, ReportDTO report) {
      String codigoServicio = report.getCodigoServicio();
      if (codigoServicio != null && codigoServicio.length() > 0) {
         sql.append("AND N.CODIGO_SERVICIO = '" + codigoServicio + "' ");
      }

      String estadoAlarma = report.getEstadoAlarma();
      if (estadoAlarma != null && estadoAlarma.length() > 0) {
         sql.append("AND A.ESTADO_ALARMA = '" + estadoAlarma + "' ");
      }

      String ip = report.getIp();
      if (ip != null && ip.length() > 0) {
         sql.append("AND A.IP = '" + ip + "' ");
      }

      String nit = report.getNit();
      if (nit != null && nit.length() > 0) {
         sql.append("AND N.NIT = '" + nit + "' ");
      }

      String ciudad = report.getCiudad();
      if (ciudad != null && ciudad.length() > 0) {
         sql.append("AND A.CIUDAD LIKE '%" + ciudad + "%' ");
      }

      String claseEquipo = report.getClaseEquipo();
      if (claseEquipo != null && claseEquipo.length() > 0) {
         sql.append("AND A.CLASE_EQUIPO LIKE '%" + claseEquipo + "%' ");
      }

      String region = report.getRegion();
      if (region != null && region.length() > 0) {
         sql.append("AND A.DIVISION LIKE '" + report.getRegion() + "%' ");
      }

      String division = report.getDivision();
      if (division != null && division.length() > 0) {
         sql.append("AND A.DIVISION LIKE '%" + division + "%' ");
      }

      String ticketOnix = report.getTicketOnix();
      if (ticketOnix != null && ticketOnix.length() > 0) {
         sql.append("AND A.TICKET_ONIX = '" + ticketOnix + "' ");
      }

      BigDecimal tiempoTotalFalla = report.getTiempoTotalFalla();
      if (tiempoTotalFalla != null && tiempoTotalFalla.intValueExact() > 0) {
         sql.append("AND A.TIEMPO_TOTAL_FALLA =" + tiempoTotalFalla.intValueExact() + " ");
      }

      String tipoEvento = report.getTipoEvento();
      if (tipoEvento != null && tipoEvento.length() > 0) {
         sql.append("AND A.TIPO_EVENTO = '" + tipoEvento + "' ");
      }

      String usuarioModificacion = report.getUsuarioModificacion();
      if (usuarioModificacion != null && usuarioModificacion.length() > 0) {
         sql.append("AND A.USUARIO_MODIFICACION = '" + usuarioModificacion + "' ");
      }

      Date fechaEsperanzaDesde = report.getFechaEsperanzaDesde();
      if (fechaEsperanzaDesde != null) {
         sql.append("AND A.FECHA_ESPERANZA >= :fechaEsperanzaDesde AND A.FECHA_ESPERANZA < :fechaEsperanzaHasta ");
      }

      Date fechaFinDesde = report.getFechaFinDesde();
      if (fechaFinDesde != null) {
         sql.append("AND A.FECHA_FIN >= :fechaFinDesde AND A.FECHA_FIN < :fechaFinHasta ");
      }

      Date fechaInicioDesde = report.getFechaInicioDesde();
      if (fechaInicioDesde != null) {
         sql.append("AND A.FECHA_INICIO >= :fechaInicioDesde AND A.FECHA_INICIO < :fechaInicioHasta ");
      }

      Date fechaModificacionDesde = report.getFechaModificacionDesde();
      if (fechaModificacionDesde != null) {
         sql.append("AND A.FECHA_MODIFICACION >= :fechaModificacionDesde AND A.FECHA_MODIFICACION < :fechaModificacionHasta ");
      }

      return sql;
   }

   private Query getParameterFindByFilter(Query query, ReportDTO report) {
      Date fechaEsperanzaDesde = report.getFechaEsperanzaDesde();
      Date fechaEsperanzaHasta = report.getFechaEsperanzaHasta();
      if (fechaEsperanzaDesde != null) {
         query.setParameter("fechaEsperanzaDesde", fechaEsperanzaDesde);
         query.setParameter("fechaEsperanzaHasta", fechaEsperanzaHasta);
      }

      Date fechaFinDesde = report.getFechaFinDesde();
      Date fechaFinHasta = report.getFechaFinHasta();
      if (fechaFinDesde != null) {
         query.setParameter("fechaFinDesde", fechaFinDesde);
         query.setParameter("fechaFinHasta", fechaFinHasta);
      }

      Date fechaInicioDesde = report.getFechaInicioDesde();
      Date fechaInicioHasta = report.getFechaInicioHasta();
      if (fechaInicioDesde != null) {
         query.setParameter("fechaInicioDesde", fechaInicioDesde);
         query.setParameter("fechaInicioHasta", fechaInicioHasta);
      }

      Date fechaModificacionDesde = report.getFechaModificacionDesde();
      Date fechaModificacionHasta = report.getFechaModificacionHasta();
      if (fechaModificacionDesde != null) {
         query.setParameter("fechaModificacionDesde", fechaModificacionDesde);
         query.setParameter("fechaModificacionHasta", fechaModificacionHasta);

      }
      return query;
   }

   private DataDTO createData(Object[] object) {
      int numCol = 0;
      DataDTO dataDTO = new DataDTO();
      dataDTO.setIdAlarmaPymes(setValue(object[numCol]));
      dataDTO.setTicketOnix(setValue(object[++numCol]));
      dataDTO.setIp(setValue(object[++numCol]));
      dataDTO.setClaseEquipo(setValue(object[++numCol]));

      dataDTO.setDescripcionAlarma(setValue(object[++numCol]));

      dataDTO.setTipoEvento(setValue(object[++numCol]));
      dataDTO.setCiudad(setValue(object[++numCol]));
      dataDTO.setDivision(setValue(object[++numCol]));

      dataDTO.setFechaInicio(setValue(object[++numCol]));
      dataDTO.setFechaEsperanza(setValue(object[++numCol]));
      dataDTO.setFechaFin(setValue(object[++numCol]));

      dataDTO.setTiempoTotalFalla(setValue(object[++numCol]));
      dataDTO.setFechaModificacion(setValue(object[++numCol]));
      dataDTO.setUsuarioModificacion(setValue(object[++numCol]));
      dataDTO.setEstadoAlarma(setValue(object[++numCol]));
      dataDTO.setCodigoServicio(setValue(object[++numCol]));
      dataDTO.setNit(setValue(object[++numCol]));

      dataDTO = getDivisionAndRegion(dataDTO);

      return dataDTO;
   }

   private DataDTO getDivisionAndRegion(DataDTO dataDTO) {
      try {
         String divisionRegion = dataDTO.getDivision();
         if (divisionRegion.length() > 0) {
            StringTokenizer token = new StringTokenizer(divisionRegion, "|");
            if (token.hasMoreTokens()) {
               String region = token.nextToken();
               dataDTO.setRegion(region.trim());
            }

            if (token.hasMoreTokens()) {
               String division = token.nextToken();
               dataDTO.setDivision(division.trim());
            }
         }

      } catch (Exception e) {

      }
      return dataDTO;
   }

   private String setValue(Object value) {
      return value != null ? value.toString() : "";
   }

   @SuppressWarnings("unchecked")
   @Override
   public ArrayList<InfoTypeAlarmDTO> findReportByRegion(String nameRegion) throws Exception {
      ArrayList<InfoTypeAlarmDTO> results = new ArrayList<InfoTypeAlarmDTO>();
      EntityManager entityManager = entityManagerFactory.createEntityManager();
      try {
         StringBuffer sql = new StringBuffer();
         sql.append("SELECT A.TIPO_EVENTO, COUNT(*) ");
         sql.append("FROM ALARMA_PYMES A WHERE A.ESTADO_ALARMA = 'A' ");
         sql.append("AND A.DIVISION LIKE '%" + nameRegion + "%' ");
         sql.append("GROUP BY A.TIPO_EVENTO ");

         Query query = entityManager.createNativeQuery(sql.toString());
         List<Object[]> listResults = query.getResultList();
         for (Object[] object : listResults) {
            InfoTypeAlarmDTO infoTypeAlarmDTO = createInfoTypeAlarm(object);
            results.add(infoTypeAlarmDTO);
         }

         StringBuffer sqlNit = new StringBuffer();
         sqlNit.append("SELECT A.TIPO_EVENTO, COUNT(DISTINCT(N.NIT)) AS NO_NITS, ");
         sqlNit.append("COUNT(DISTINCT(N.CODIGO_SERVICIO)) AS NO_ENLACES ");
         sqlNit.append("FROM ALARMA_PYMES_SERVICIO_NIT N ");
         sqlNit.append("INNER JOIN ALARMA_PYMES A ON (N.ID_ALARMA_PYMES = A.ID_ALARMA_PYMES) ");
         sqlNit.append("WHERE A.ESTADO_ALARMA = 'I' AND A.DIVISION LIKE '%" + nameRegion + "%'");
         sqlNit.append("GROUP BY A.TIPO_EVENTO ");

         Query queryNit = entityManager.createNativeQuery(sqlNit.toString());
         List<Object[]> listResultsNit = queryNit.getResultList();
         for (Object[] object : listResultsNit) {
            results = addInformationReport(object, results);
         }
      } catch (Exception e) {
         throw e;
      } finally {
         entityManager.close();
      }

      return results;

   }

   private InfoTypeAlarmDTO createInfoTypeAlarm(Object[] object) {
      int numCol = 0;
      InfoTypeAlarmDTO infoTypeAlarmDTO = new InfoTypeAlarmDTO();

      infoTypeAlarmDTO.setNameTypeAlarm(setValue(object[numCol]));
      infoTypeAlarmDTO.setNoAlarms(setValueInt(object[++numCol]));

      return infoTypeAlarmDTO;
   }

   private int setValueInt(Object value) {
      String valueStr = value != null ? value.toString() : "0";
      return Integer.parseInt(valueStr);
   }

   private ArrayList<InfoTypeAlarmDTO> addInformationReport(Object[] object, ArrayList<InfoTypeAlarmDTO> results) {
      for (InfoTypeAlarmDTO infoTypeAlarmDTO : results) {
         if (infoTypeAlarmDTO.getNameTypeAlarm().equals(object[0])) {
            infoTypeAlarmDTO.setNoNitsAffected(setValueInt(object[1]));
            infoTypeAlarmDTO.setNoCodesAffected(setValueInt(object[2]));
         }
      }
      return results;
   }

   @Override
   public int validateSizeData(ReportDTO report) throws Exception {
      int result = 0;
      EntityManager entityManager = entityManagerFactory.createEntityManager();
      try {
         StringBuffer sql = getSQLValidateSizeData(report);
         Query query = entityManager.createNativeQuery(sql.toString());
         query = getParameterFindByFilter(query, report);
         BigDecimal sizeData = (BigDecimal) query.getSingleResult();
         LOGGER.info("SIZE DATA: " + sizeData);
         result = Integer.parseInt(sizeData.toString());

      } catch (Exception e) {
         throw e;
      } finally {
         entityManager.close();
      }
      return result;
   }

   private StringBuffer getSQLValidateSizeData(ReportDTO report) {
      StringBuffer sql = new StringBuffer();

      sql.append("SELECT ");
      sql.append("COUNT(*) AS SIZE_DATA ");
      sql.append("FROM ALARMA_PYMES A  ");
      sql.append("LEFT JOIN ALARMA_PYMES_SERVICIO_NIT N ON (A.ID_ALARMA_PYMES = N.ID_ALARMA_PYMES) ");
      sql.append("WHERE 1 = 1 ");

      sql = getFiltersFindByFilters(sql, report);

      LOGGER.info("SQL: " + sql.toString());

      return sql;

   }
}
