package com.claro.cpymes.dao;

import java.util.ArrayList;
import java.util.Date;

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
import com.claro.cpymes.entity.AlarmPymesEntity;
import com.claro.cpymes.enums.ProcessEnum;
import com.claro.cpymes.enums.StateEnum;
import com.claro.cpymes.util.Constant;
import com.claro.cpymes.util.Util;


/**
 * AlarmPymesDAO - DAO que controla las transaciones a base 
 * de datos de la entidad AlarmPymesEntity
 * @author jbarragan
 *
 */
@Stateless
@LocalBean
public class AlarmPymesDAO extends TemplateDAO<AlarmPymesEntity> implements AlarmPymesDAORemote {

   private static Logger LOGGER = LogManager.getLogger(AlarmPymesDAO.class.getName());

   /**
    * Obtiene las entidades AlarmPymesEntity por estado
    * @param estado Con el que se realiza la consulta
    * @return ArrayList<AlarmPymesEntity> Lista de entidades encontradas
    */
   @Override
   @TransactionAttribute(TransactionAttributeType.REQUIRED)
   public ArrayList<AlarmPymesEntity> findByEstado(String estado) {
      EntityManager entityManager = entityManagerFactory.createEntityManager();
      entityManager.getTransaction().begin();
      TypedQuery<AlarmPymesEntity> query = entityManager.createNamedQuery("AlarmPymesEntity.findByEstado",
         AlarmPymesEntity.class);
      query.setParameter("estado", estado);
      ArrayList<AlarmPymesEntity> results = (ArrayList<AlarmPymesEntity>) query.setMaxResults(
         Constant.MAXIME_RESULT_ALARM).getResultList();
      entityManager.getTransaction().commit();
      entityManager.close();

      return results;
   }

   /**
    * Busqueda de alarmas pot prioridad
    * @param listPrioritySelect Lista de prioridades a consultar
    * @return ArrayList<AlarmPymesEntity>  Lista de alarmas encontradas
    */
   @Override
   @TransactionAttribute(TransactionAttributeType.REQUIRED)
   public ArrayList<AlarmPymesEntity> findByPriority(ArrayList<String> listPrioritySelect) {
      EntityManager entityManager = entityManagerFactory.createEntityManager();
      entityManager.getTransaction().begin();
      TypedQuery<AlarmPymesEntity> query = entityManager.createNamedQuery("AlarmPymesEntity.findByPriority",
         AlarmPymesEntity.class);
      query.setParameter("listPriority", listPrioritySelect);
      query.setParameter("estado", ProcessEnum.ACTIVO.getValue());
      ArrayList<AlarmPymesEntity> results = (ArrayList<AlarmPymesEntity>) query.getResultList();
      entityManager.getTransaction().commit();
      entityManager.close();

      return results;
   }

   /**
    * Persiste la entidad
    * @param entity Entidad a persistir
    */
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
    * Obtiene las entidades AlarmPymesEntity por criterios de busqueda
    * @param entityManager 
    * @param eventName Nombre del evento
    * @param name Nombre del dispositivo
    * @param startDate Rango de fecha inicial
    * @param endDate Rango de fecha final
    * @return ArrayList<AlarmPymesEntity> Lista de entidades encontradas
    */
   private boolean existSimilar(AlarmPymesEntity alarm, EntityManager entityManager) {
      boolean exist = false;
      Date today = new Date();
      Date startDate = Util.restarFecha(today, Integer.parseInt(Constant.TIMER_SIMILAR_REGISTERS));
      try {
         TypedQuery<AlarmPymesEntity> query = entityManager.createNamedQuery("AlarmPymesEntity.findSimiliar",
            AlarmPymesEntity.class);
         query.setParameter("eventName", alarm.getEventName());
         query.setParameter("name", alarm.getName());
         query.setParameter("startDate", startDate);
         query.setParameter("endDate", today);
         exist = query.setFirstResult(1).setMaxResults(1).getResultList().size() > 0;

      } catch (Exception e) {
         LOGGER.error("Error buscando registros similares", e);
         return exist;
      }

      return exist;
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

   public ArrayList<LogDTO> createList(ArrayList<LogDTO> listLog) {
      int numberRegisterCreate = 0;
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
               } else {
                  logDTO.setRelevant(false);
               }
            } catch (Exception e) {
               LOGGER.error("Error guardando Alarma", e);
            }
         }
      }
      entityTransaction.commit();
      entityManager.close();
      LOGGER.info("FILTRADO - Alarmas Filtradas: " + numberRegisterCreate);
      return listLog;
   }

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
      Date today = new Date();
      alarmEntity.setDate(today);

      return alarmEntity;
   }

   @Override
   public int clearAlarm(String[] eventNames, String ip, Date date) throws Exception {
      int resultUpdate = 0;
      EntityManager entityManager = entityManagerFactory.createEntityManager();
      EntityTransaction entityTransaction = entityManager.getTransaction();
      entityTransaction.begin();
      Date endDate = date;
      Date startDate = Util.restarFecha(endDate, Integer.parseInt(Constant.TIMER_SIMILAR_REGISTERS));

      Query query = entityManager.createQuery(getQuery(eventNames));
      query.setParameter("estado", StateEnum.INACTIVO.getValue());
      query.setParameter("ip", ip);
      query.setParameter("startDate", startDate);
      query.setParameter("endDate", endDate);
      resultUpdate = query.executeUpdate();

      entityTransaction.commit();
      entityManager.close();

      return resultUpdate;

   }

   private String getQuery(String[] eventNames) {
      String query = "UPDATE AlarmPymesEntity a SET a.estado=:estado" + " WHERE a.ip=:ip and a.eventName in ("
         + getEventNamesStr(eventNames) + ")" + " and a.date BETWEEN :startDate AND :endDate ";
      return query;
   }

   private String getEventNamesStr(String[] eventNames) {
      String eventNamesStr = "";
      for (int i = 0; i < eventNames.length - 1; i++) {
         eventNamesStr = eventNamesStr + "'" + eventNames[i] + "',";
      }
      eventNamesStr = eventNamesStr + "'" + eventNames[eventNames.length - 1] + "'";

      return eventNamesStr;
   }

}
