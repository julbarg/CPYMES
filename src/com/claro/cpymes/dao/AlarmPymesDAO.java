package com.claro.cpymes.dao;

import java.util.ArrayList;
import java.util.Date;

import javax.ejb.EJB;
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

import com.claro.cpymes.dto.LogDTO;
import com.claro.cpymes.dto.RestoreEventAlarmDTO;
import com.claro.cpymes.entity.AlarmPymesEntity;
import com.claro.cpymes.enums.ProcessEnum;
import com.claro.cpymes.enums.StateEnum;
import com.claro.cpymes.enums.TypeEventEnum;
import com.claro.cpymes.util.Constant;
import com.claro.cpymes.util.Util;


/**
 * DAO para AlarmPymesEntity
 * @author jbarragan
 *
 */
@Stateless
@LocalBean
public class AlarmPymesDAO extends TemplateDAO<AlarmPymesEntity> implements AlarmPymesDAORemote {

   private static Logger LOGGER = LogManager.getLogger(AlarmPymesDAO.class.getName());

   @EJB
   private ParameterDAORemote parametroDAO;

   @Override
   public ArrayList<AlarmPymesEntity> findByEstado(String estado) throws Exception {
      EntityManager entityManager = entityManagerFactory.createEntityManager();

      TypedQuery<AlarmPymesEntity> query = entityManager.createNamedQuery("AlarmPymesEntity.findByEstado",
         AlarmPymesEntity.class);
      Date dateLater = Util.restarFecha(new Date(), Constant.TIME_SEND_CPYMES);
      query.setParameter("estado", estado);
      query.setParameter("date", dateLater);
      ArrayList<AlarmPymesEntity> results = (ArrayList<AlarmPymesEntity>) query.setMaxResults(
         Constant.MAXIME_RESULT_ALARM).getResultList();

      entityManager.close();

      return results;
   }

   @Override
   public ArrayList<AlarmPymesEntity> findByPriority(ArrayList<String> listPrioritySelect) throws Exception {
      EntityManager entityManager = entityManagerFactory.createEntityManager();
      TypedQuery<AlarmPymesEntity> query = entityManager.createNamedQuery("AlarmPymesEntity.findByPriority",
         AlarmPymesEntity.class);
      query.setParameter("listPriority", listPrioritySelect);
      query.setParameter("estado", ProcessEnum.ACTIVO.getValue());
      ArrayList<AlarmPymesEntity> results = (ArrayList<AlarmPymesEntity>) query.getResultList();

      entityManager.close();

      return results;
   }

   @Override
   @TransactionAttribute(TransactionAttributeType.REQUIRED)
   public void create(AlarmPymesEntity entity) {
      try {
         super.create(entity);
      } catch (Exception e) {
         LOGGER.error("Error creando registro", e);
      }

   }

   /**
    * Obtiene las entidades AlarmPymesEntity por criterios de busqueda, con estado ACTIVO
    * @param nodo Nodo
    * @param nameCorrelation Nombre de la Correlacion
    * @param startDate Rango de fecha inicial
    * @param endDate Rango de fecha final
    * @return ArrayList<AlarmPymesEntity> Lista de entidades encontradas
    */
   @Override
   public ArrayList<AlarmPymesEntity> findSimiliarCEP(String nodo, String nameCorrelation, Date startDate, Date endDate) {
      ArrayList<AlarmPymesEntity> results = new ArrayList<AlarmPymesEntity>();
      try {
         EntityManager entityManager = entityManagerFactory.createEntityManager();
         TypedQuery<AlarmPymesEntity> query = entityManager.createNamedQuery("AlarmPymesEntity.findSimiliarCEP",
            AlarmPymesEntity.class);
         query.setParameter("nodo", nodo);
         query.setParameter("nameCorrelation", nameCorrelation);
         query.setParameter("startDate", startDate);
         query.setParameter("endDate", endDate);
         query.setParameter("estado", StateEnum.ACTIVO.getValue());
         results = (ArrayList<AlarmPymesEntity>) query.getResultList();

         entityManager.close();
      } catch (Exception e) {
         LOGGER.error("Error buscando registros similares", e);
         return results;
      }

      return results;
   }

   /**
    * Obtiene las entidades AlarmPymesEntity por criterios de busqueda, con estado ACTIVO
    * @param nodo Nodo
    * @param nameCorrelation Nombre de la Correlacion
    * @param date Date
    * @return ArrayList<AlarmPymesEntity> Lista de entidades encontradas
    */
   @Override
   public ArrayList<AlarmPymesEntity> findSimiliarCEP(String nodo, String nameCorrelation, Date date) {
      ArrayList<AlarmPymesEntity> results = new ArrayList<AlarmPymesEntity>();
      try {
         EntityManager entityManager = entityManagerFactory.createEntityManager();
         TypedQuery<AlarmPymesEntity> query = entityManager.createNamedQuery("AlarmPymesEntity.findSimiliarCEPByDate",
            AlarmPymesEntity.class);
         query.setParameter("nodo", nodo);
         query.setParameter("nameCorrelation", nameCorrelation);
         query.setParameter("date", date);
         query.setParameter("estado", StateEnum.ACTIVO.getValue());
         results = (ArrayList<AlarmPymesEntity>) query.setFirstResult(1).setMaxResults(1).getResultList();

         entityManager.close();
      } catch (Exception e) {
         LOGGER.error("Error buscando registros similares", e);
         return results;
      }

      return results;
   }

   /**
    * Obtiene las entidades AlarmPymesEntity reconocidas. Estado RECONOCIDO
    * @param startDate Rango de fecha inicial
    * @param endDate Rango de fecha final
    * @return ArrayList<AlarmPymesEntity> Lista de entidades encontradas
    */
   @Override
   public ArrayList<AlarmPymesEntity> findSimiliarCEPReconocidas(Date startDate, Date endDate) {
      ArrayList<AlarmPymesEntity> results = new ArrayList<AlarmPymesEntity>();
      try {
         EntityManager entityManager = entityManagerFactory.createEntityManager();
         entityManager.getTransaction().begin();
         TypedQuery<AlarmPymesEntity> query = entityManager.createNamedQuery(
            "AlarmPymesEntity.findSimiliarCEPReconocidas", AlarmPymesEntity.class);
         query.setParameter("startDate", startDate);
         query.setParameter("endDate", endDate);
         query.setParameter("estado", ProcessEnum.RECONOCIDO.getValue());
         results = (ArrayList<AlarmPymesEntity>) query.getResultList();
         entityManager.getTransaction().commit();
         entityManager.close();
      } catch (Exception e) {
         LOGGER.error("Error buscando registros correlacionados reconocidos", e);
         return results;
      }
      return results;
   }

   /**
    * Persiste los registros entregados en la Lista
    * @param listLog Lista de regitrosa almacenar
    * @return Registros almacenados
    */
   @Override
   @TransactionAttribute(TransactionAttributeType.REQUIRED)
   public ArrayList<LogDTO> createList(ArrayList<LogDTO> listLog) throws Exception {
      int numberRegisterCreate = 0;
      int numberRegisterActive = 0;
      int numberOfRegister = 0;
      EntityManager entityManager = entityManagerFactory.createEntityManager();
      EntityTransaction entityTransaction = entityManager.getTransaction();
      entityTransaction.begin();

      for (LogDTO logDTO : listLog) {
         if (logDTO.getSeverity() != null && logDTO.isRelevant()) {
            try {
               AlarmPymesEntity alarmEntity = getAlarmPymesEntity(logDTO);
               if (!existSimilar(alarmEntity, entityManager)) {
                  entityManager.persist(alarmEntity);
                  numberRegisterCreate++;
                  if (StateEnum.ACTIVO.getValue().equals(alarmEntity.getEstado())) {
                     numberRegisterActive++;
                  }
               } else {
                  logDTO.setRelevant(false);
               }
            } catch (Exception e) {
               LOGGER.error("Error guardando Alarma", e);
            }
         }
         numberOfRegister++;
         if (numberOfRegister == 100) {
            entityManager.flush();
            entityManager.clear();
            numberOfRegister = 0;
         }
      }
      entityTransaction.commit();
      entityManager.close();
      LOGGER.info("FILTRADO - Alarmas Filtradas Guardadas: " + numberRegisterCreate);
      LOGGER.info("FILTRADO - Alarmas Activas Guardadas: " + numberRegisterActive);

      parametroDAO.addCountResgister(Constant.SEND_TO_CPYMS_NO_ACTIVE, numberRegisterCreate);
      parametroDAO.addCountResgister(Constant.SEND_TO_CPYMES_ACTIVE, numberRegisterActive);

      return listLog;
   }

   /**
    * Valida si existe un registro similar.
    * Valida nombre del evento, nombre del equipo, estado y un periodo de tiempo
    * @param alarm Alamra a validad
    * @param entityManager
    * @return true si existe registro similar
    */
   private boolean existSimilar(AlarmPymesEntity alarm, EntityManager entityManager) {
      boolean exist = false;
      Date today = new Date();
      Date startDate = Util.restarFecha(today, Integer.parseInt(Constant.TIMER_SIMILAR_REGISTERS));
      try {
         Query query = entityManager.createNamedQuery("AlarmPymesEntity.findSimiliar");
         query.setParameter("eventName", alarm.getEventName());
         query.setParameter("name", alarm.getName());
         query.setParameter("startDate", startDate);
         query.setParameter("endDate", today);
         query.setParameter("estado", StateEnum.ACTIVO.getValue());
         query.setParameter("interFace", alarm.getInterFace());
         exist = query.setMaxResults(1).getResultList().size() > 0;

      } catch (Exception e) {
         LOGGER.error("Error buscando registros similares", e);
         return exist;
      }

      return exist;
   }

   /**
    * Mapear de LogDTO a AlarmPymesEntity
    * @param logDTO
    * @return
    */
   private AlarmPymesEntity getAlarmPymesEntity(LogDTO logDTO) {
      AlarmPymesEntity alarmEntity = new AlarmPymesEntity();
      alarmEntity.setIp(logDTO.getIp());
      alarmEntity.setOid(logDTO.getOID());
      alarmEntity.setName(logDTO.getName());
      alarmEntity.setNodo(logDTO.getNodo());
      alarmEntity.setEventName(logDTO.getNameEvent());
      alarmEntity.setPriority(logDTO.getPriority());
      alarmEntity.setMessage(logDTO.getMessageDRL());
      // TODO No se deben mostrar todas las alarmas
      // Falta definir que alarmas se van a mostrar inmediatemente en pantalla
      /* String severity = logDTO.getSeverity();
       * if (SeverityEnum.AS.getValue().equals(severity) || SeverityEnum.NAS.getValue().equals(severity)
       * || SeverityEnum.PAS.getValue().equals(severity)) {
       * alarmEntity.setEstado(StateEnum.ACTIVO.getValue());
       * } else {
       * alarmEntity.setEstado(StateEnum.NO_SAVE.getValue());
       * } */
      alarmEntity.setEstado(logDTO.getState());
      alarmEntity.setSeverity(logDTO.getSeverity());
      alarmEntity.setInterFace(logDTO.getInterFace());
      Date today = new Date();
      alarmEntity.setDate(today);
      alarmEntity.setSendIVR(logDTO.isSendIVR() ? ProcessEnum.SI.getValue() : null);
      if (logDTO.getTypeEvent() != null) {
         alarmEntity.setTypeEvent(logDTO.getTypeEvent().getValue());
      }

      return alarmEntity;
   }

   /**
    * Clarear alarmas en CPYMES segun criterios de los eventos de restauracion
    * @param listRestore Lista de eventos de restauracion
    * @return Numeros de registros restaurados
    */
   @Override
   @TransactionAttribute(TransactionAttributeType.REQUIRED)
   public int clearAlarm(ArrayList<RestoreEventAlarmDTO> listRestore) throws Exception {
      int resultUpdate = 0;
      EntityManager entityManager = entityManagerFactory.createEntityManager();
      EntityTransaction entityTransaction = entityManager.getTransaction();
      entityTransaction.begin();

      for (RestoreEventAlarmDTO restore : listRestore) {
         resultUpdate = resultUpdate + clearAlarm(restore, entityManager);
      }

      entityTransaction.commit();
      entityManager.close();

      LOGGER.info("RESTORE EVENT CPYMES - Alarmas Restauradas: " + resultUpdate);

      return resultUpdate;
   }

   /**
    * Ejecuta el SQL de clareo de registros
    * @param restore
    * @param entityManager
    * @return Numero de registros clareados
    * @throws Exception
    */
   private int clearAlarm(RestoreEventAlarmDTO restore, EntityManager entityManager) throws Exception {
      int resultUpdate = 0;

      Query query = entityManager.createQuery(getQuery(restore.getEventTrigger()));
      query.setParameter("estado", StateEnum.INACTIVO.getValue());
      query.setParameter("ip", restore.getIp());
      query.setParameter("dateRestore", new Date());
      query.setParameter("interFace", restore.getInterFace());
      resultUpdate = query.executeUpdate();

      return resultUpdate;
   }

   private String getQuery(String[] eventNames) {
      String query = "UPDATE AlarmPymesEntity a SET a.estado=:estado, a.datetimeAcknowledge=:dateRestore "
         + " WHERE a.ip=:ip and (" + getEventNamesStr(eventNames) + ")" + " and a.estado != 'I'"
         + " and a.interFace=:interFace";
      return query;
   }

   private String getEventNamesStr(String[] eventNames) {
      String eventNamesStr = "";
      for (int i = 0; i < eventNames.length - 1; i++) {
         eventNamesStr = eventNamesStr + " a.eventName = '" + eventNames[i] + "' OR ";
      }
      eventNamesStr = eventNamesStr + " a.eventName = '" + eventNames[eventNames.length - 1] + "' ";

      return eventNamesStr;
   }

   /**
    * Busca alarmas marcadas para envio al IVR. (Que se hallan guardado en un tiempo previo establecido)
    * @return Alarmas encontradas que cumplan los criterios
    */
   @Override
   public ArrayList<LogDTO> findSendIVR() throws Exception {
      EntityManager entityManager = entityManagerFactory.createEntityManager();
      TypedQuery<AlarmPymesEntity> query = entityManager.createNamedQuery("AlarmPymesEntity.findSendIVR",
         AlarmPymesEntity.class);
      Date dateLeter = Util.restarFecha(new Date(), Constant.TIME_SEND_IVR);
      query.setParameter("date", dateLeter);
      query.setParameter("estado", ProcessEnum.ACTIVO.getValue());
      query.setParameter("sendIVR", ProcessEnum.SI.getValue());
      ArrayList<AlarmPymesEntity> results = (ArrayList<AlarmPymesEntity>) query.getResultList();
      entityManager.close();
      ArrayList<LogDTO> listLogDTO = getListLogsSendIVR(results);

      return listLogDTO;
   }

   /**
    * Mapea de ArrayList<AlarmPymesEntity> a ArrayList<LogDTO> 
    * @param results
    * @return Lista mapeada
    */
   private ArrayList<LogDTO> getListLogsSendIVR(ArrayList<AlarmPymesEntity> results) {
      LogDTO log;
      ArrayList<LogDTO> listLogDTO = new ArrayList<LogDTO>();
      for (AlarmPymesEntity alarmEntity : results) {
         log = new LogDTO();

         log.setSeq(alarmEntity.getId());
         log.setIp(alarmEntity.getIp());
         log.setOID(alarmEntity.getOid());
         log.setName(alarmEntity.getName());
         log.setNodo(alarmEntity.getNodo());
         log.setNameEvent(alarmEntity.getEventName());
         log.setPriority(alarmEntity.getPriority());
         log.setMessageDRL(alarmEntity.getMessage());
         log.setState(alarmEntity.getEstado());
         log.setSeverity(alarmEntity.getSeverity());
         log.setInterFace(alarmEntity.getInterFace());
         log.setDate(alarmEntity.getDate());
         log.setSendIVR(true);
         log.setRelevant(true);

         TypeEventEnum typeEnum = TypeEventEnum.getTypeEnum(alarmEntity.getTypeEvent());
         log.setTypeEvent(typeEnum);

         if (alarmEntity.getNameCorrelation() != null) {
            log.setCorrelation(true);
         }

         listLogDTO.add(log);

      }
      return listLogDTO;

   }

   /**
    * Actualiza las alarmas enviadas al IVR
    * @param listLogSendIVR Lista de alarmas enviadas al IVR
    */
   @Override
   public void updateAlarmSendIVR(ArrayList<LogDTO> listLogSendIVR) throws Exception {

      if (listLogSendIVR.size() == 0) {
         return;
      }
      ArrayList<Long> ids = new ArrayList<Long>();
      for (LogDTO log : listLogSendIVR) {
         ids.add(log.getSeq());
      }
      EntityManager entityManager = entityManagerFactory.createEntityManager();
      entityManager.getTransaction().begin();

      Query query = entityManager.createQuery(getQuery());
      query.setParameter("sendIVR", null);
      query.setParameter("ids", ids);

      query.executeUpdate();

      entityManager.getTransaction().commit();
      entityManager.close();

   }

   private String getQuery() {
      String query = "UPDATE AlarmPymesEntity l SET l.sendIVR=:sendIVR WHERE l.id IN :ids";
      return query;
   }

}
