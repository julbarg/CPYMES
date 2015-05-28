package com.claro.cpymes.dao;

import java.util.ArrayList;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.claro.cpymes.entity.AlarmaPymeIVREntity;
import com.claro.cpymes.entity.AlarmaPymesServicioNitIVREntity;


@Stateless
@LocalBean
public class AlarmaPymesServicioNitIVRDAO extends TemplateIVRDAO<AlarmaPymesServicioNitIVREntity> implements
   AlarmaPymesServicioNitIVRDAORemote {

   @Override
   @TransactionAttribute(TransactionAttributeType.REQUIRED)
   public ArrayList<AlarmaPymesServicioNitIVREntity> findByAlarm(AlarmaPymeIVREntity alarm) throws Exception {
      EntityManager entityManager = entityManagerFactory.createEntityManager();
      TypedQuery<AlarmaPymesServicioNitIVREntity> query = entityManager.createNamedQuery(
         "AlarmaPymesServicioNitIVREntity.findByAlarm", AlarmaPymesServicioNitIVREntity.class);
      query.setParameter("alarm", alarm);
      ArrayList<AlarmaPymesServicioNitIVREntity> results = (ArrayList<AlarmaPymesServicioNitIVREntity>) query
         .getResultList();

      entityManager.close();

      return results;
   }

}
