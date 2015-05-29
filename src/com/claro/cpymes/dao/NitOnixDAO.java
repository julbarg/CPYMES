package com.claro.cpymes.dao;

import java.util.ArrayList;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.claro.cpymes.entity.NitOnixEntity;


@Stateless
@LocalBean
public class NitOnixDAO extends TemplateDAO<NitOnixEntity> implements NitOnixDAORemote {

   @Override
   public ArrayList<NitOnixEntity> findByEstado(String estado) throws Exception {
      EntityManager entityManager = entityManagerFactory.createEntityManager();
      entityManager.getTransaction().begin();
      TypedQuery<NitOnixEntity> query = entityManager.createNamedQuery("NitOnixEntity.findByEstado",
         NitOnixEntity.class);
      query.setParameter("estado", estado);
      ArrayList<NitOnixEntity> results = (ArrayList<NitOnixEntity>) query.getResultList();
      entityManager.close();
      return results;

   }

   @Override
   public void removeAll() throws Exception {

      EntityManager entityManager = entityManagerFactory.createEntityManager();
      entityManager.getTransaction().begin();
      Query query = entityManager.createQuery("DELETE FROM NitOnixEntity");
      query.executeUpdate();
      entityManager.getTransaction().commit();
      entityManager.close();

   }

   @Override
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

}
