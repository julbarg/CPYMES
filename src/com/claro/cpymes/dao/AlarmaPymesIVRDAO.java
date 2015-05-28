package com.claro.cpymes.dao;

import java.util.ArrayList;
import java.util.Date;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.claro.cpymes.dto.AlarmaPymeIVRDTO;
import com.claro.cpymes.entity.AlarmaPymeIVREntity;


@Stateless
@LocalBean
public class AlarmaPymesIVRDAO extends TemplateIVRDAO<AlarmaPymeIVREntity> implements AlarmaPymesIVRDAORemote {

   @Override
   @TransactionAttribute(TransactionAttributeType.REQUIRED)
   public ArrayList<AlarmaPymeIVREntity> findByEstado(String estado) {
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

}
