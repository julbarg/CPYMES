package com.claro.cpymes.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.claro.cpymes.dto.HistoricalRecordsDTO;
import com.claro.cpymes.entity.ParametroEntity;
import com.claro.cpymes.util.Constant;


@Stateless
@LocalBean
public class ParameterDAO extends TemplateDAO<ParametroEntity> implements ParameterDAORemote {

   private static Logger LOGGER = LogManager.getLogger(ParameterDAO.class.getName());

   public String findByName(String name) throws Exception {
      EntityManager entityManager = entityManagerFactory.createEntityManager();
      Query query = entityManager.createNamedQuery("ParametroEntity.findByName");
      query.setParameter("name", name);
      String value = (String) query.getSingleResult();
      entityManager.close();
      return value;
   }

   @Override
   public void updateParameter(String parameter, String value) throws Exception {
      ParametroEntity parametro = findById(parameter);
      parametro.setValue(value);
      update(parametro);
   }

   private ParametroEntity findById(String id) throws Exception {
      ParametroEntity parametro;
      EntityManager entityManager = entityManagerFactory.createEntityManager();
      entityManager.getTransaction().begin();
      parametro = entityManager.find(ParametroEntity.class, id);
      entityManager.getTransaction().commit();
      entityManager.close();
      return parametro;
   }

   @Override
   public void addCountResgister(String name, int value) {
      try {
         String initialValue = findByName(name);
         int finalValue = Integer.parseInt(initialValue) + value;
         updateParameter(name, "" + finalValue);
      } catch (Exception e) {
         LOGGER.error("Ha ocurrido un error al sumar registros");
      }
   }

   @Override
   public HistoricalRecordsDTO getHistoricalRecords() throws Exception {
      HistoricalRecordsDTO historicalRecords = new HistoricalRecordsDTO();
      EntityManager entityManager = entityManagerFactory.createEntityManager();
      historicalRecords.setSendFromKou(getValue(Constant.SEND_FROM_KOU, entityManager));
      historicalRecords.setSendToCPYMESNoActive(getValue(Constant.SEND_TO_CPYMS_NO_ACTIVE, entityManager));
      historicalRecords.setSendToCPYMESActive(getValue(Constant.SEND_TO_CPYMES_ACTIVE, entityManager));
      historicalRecords.setSendToIVR(getValue(Constant.SEND_TO_IVR, entityManager));
      historicalRecords.setAlarmRestoreCPYMES(getValue(Constant.ALARM_RESTORE_CPYMES, entityManager));
      historicalRecords.setAlarmRestoreIVR(getValue(Constant.ALARM_RESTORE_IVR, entityManager));

      entityManager.close();
      return historicalRecords;

   }

   private String getValue(String name, EntityManager entityManager) {
      Query query = entityManager.createNamedQuery("ParametroEntity.findByName");
      query.setParameter("name", name);
      String value = (String) query.getSingleResult();

      return value;

   }

}
