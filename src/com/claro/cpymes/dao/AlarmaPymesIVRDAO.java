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

import com.claro.cpymes.dto.AlarmaPymeIVRDTO;
import com.claro.cpymes.dto.RestoreEventAlarmDTO;
import com.claro.cpymes.entity.AlarmaPymeIVREntity;
import com.claro.cpymes.enums.StateEnum;


@Stateless
@LocalBean
public class AlarmaPymesIVRDAO extends TemplateIVRDAO<AlarmaPymeIVREntity> implements AlarmaPymesIVRDAORemote {

   private static Logger LOGGER = LogManager.getLogger(AlarmaPymesIVRDAO.class.getName());

   @Override
   public ArrayList<AlarmaPymeIVREntity> findByEstado(String estado) throws Exception{
      EntityManager entityManager = entityManagerFactory.createEntityManager();
      TypedQuery<AlarmaPymeIVREntity> query = entityManager.createNamedQuery("AlarmaPymeIVREntity.findByEstado",
         AlarmaPymeIVREntity.class);
      query.setParameter("estado", estado);
      ArrayList<AlarmaPymeIVREntity> results = (ArrayList<AlarmaPymeIVREntity>) query.getResultList();

      entityManager.close();

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
      EntityManager entityManager = entityManagerFactory.createEntityManager();
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

      comandQuery.append(" 1 = 1");

      TypedQuery<AlarmaPymeIVREntity> query = entityManager.createQuery(comandQuery.toString(),
         AlarmaPymeIVREntity.class);

      return (ArrayList<AlarmaPymeIVREntity>) query.getResultList();

   }

   private StringBuffer validateString(StringBuffer comandQuery, String value, String name) {
      if (value != null && !value.isEmpty()) {
         comandQuery.append(name);
         comandQuery.append(value);
         comandQuery.append("%' AND ");

      }
      return comandQuery;
   }

   private StringBuffer validateDate(StringBuffer comandQuery, Date value, String name) {
      if (value != null) {
         comandQuery.append(name);
         comandQuery.append(value);
         comandQuery.append("%' AND ");

      }
      return comandQuery;
   }

   @Override
   @TransactionAttribute(TransactionAttributeType.REQUIRED)
   public void clearAlarm(ArrayList<RestoreEventAlarmDTO> listRestore) throws Exception {
      int resultUpdate = 0;
      EntityManager entityManager = entityManagerFactory.createEntityManager();
      EntityTransaction entityTransaction = entityManager.getTransaction();
      entityTransaction.begin();

      for (RestoreEventAlarmDTO restore : listRestore) {
         resultUpdate = resultUpdate + clearAlarm(restore, entityManager);
      }

      entityTransaction.commit();
      entityManager.close();

      LOGGER.info("RESTORE EVENT IVR- Alarmas Restauradas: " + resultUpdate);

   }

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
      String query = "UPDATE AlarmaPymeIVREntity a SET a.estadoAlarma=:estado, a.fechaFin=:dateEnd "
         + " WHERE a.ip=:ip and a.claseEquipo = :eventName  and a.estadoAlarma != 'I'";
      return query;
   }

   private String getQuery(String[] eventNames) {
      String query = "UPDATE AlarmaPymeIVREntity a SET a.estadoAlarma=:estado, a.fechaFin=:dateEnd "
         + " WHERE a.ip=:ip and (" + getEventNamesStr(eventNames) + ")" + " and a.estadoAlarma != 'I'";
      return query;
   }

   @Override
   @TransactionAttribute(TransactionAttributeType.REQUIRED)
   public int clearAlarm(RestoreEventAlarmDTO restore) throws Exception {
      int resultUpdate = 0;
      EntityManager entityManager = entityManagerFactory.createEntityManager();
      EntityTransaction entityTransaction = entityManager.getTransaction();
      entityTransaction.begin();

      resultUpdate = resultUpdate + clearAlarm(restore, entityManager);

      entityTransaction.commit();
      entityManager.close();

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
      EntityTransaction entityTransaction = entityManager.getTransaction();
      entityTransaction.begin();
      if (!(existSimilar(alarmaIVR, entityManager))) {
         alarmaIVR = update(alarmaIVR);
         entityTransaction.commit();
         entityManager.close();
         return alarmaIVR;
      }
      entityTransaction.commit();
      entityManager.close();
      return null;
   }

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
}
