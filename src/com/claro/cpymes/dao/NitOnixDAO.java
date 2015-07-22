package com.claro.cpymes.dao;

import java.util.ArrayList;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.claro.cpymes.entity.NitOnixEntity;


/**
 * DAO para NitOnixEntity (Table: Codigos de Servicios - NIT) 
 * @author jbarragan
 *
 */
@Stateless
@LocalBean
public class NitOnixDAO extends TemplateDAO<NitOnixEntity> implements NitOnixDAORemote {

   @Override
   public ArrayList<NitOnixEntity> findAll() throws Exception {
      EntityManager entityManager = entityManagerFactory.createEntityManager();
      TypedQuery<NitOnixEntity> query = entityManager.createNamedQuery("NitOnixEntity.findAll", NitOnixEntity.class);
      ArrayList<NitOnixEntity> results = (ArrayList<NitOnixEntity>) query.getResultList();
      entityManager.close();
      return results;

   }

   @Override
   @TransactionAttribute(TransactionAttributeType.REQUIRED)
   public void removeAll() throws Exception {
      EntityManager entityManager = entityManagerFactory.createEntityManager();
      entityManager.getTransaction().begin();
      Query query = entityManager.createQuery("DELETE FROM NitOnixEntity");
      query.executeUpdate();
      entityManager.getTransaction().commit();
      entityManager.close();

   }

   @Override
   @TransactionAttribute(TransactionAttributeType.REQUIRED)
   public void createList(ArrayList<NitOnixEntity> listNitOnix) throws Exception {
      EntityManager entityManager = entityManagerFactory.createEntityManager();
      EntityTransaction entityTransaction = entityManager.getTransaction();
      entityTransaction.begin();
      for (NitOnixEntity nitOnix : listNitOnix) {
         entityManager.persist(nitOnix);
      }
      entityTransaction.commit();
      entityManager.close();

   }

   public int findAllCount() throws Exception {
      EntityManager entityManager = entityManagerFactory.createEntityManager();
      int count = ((Number) entityManager.createNamedQuery("NitOnixEntity.findAllCount").getSingleResult()).intValue();
      entityManager.close();
      return count;
   }

}
