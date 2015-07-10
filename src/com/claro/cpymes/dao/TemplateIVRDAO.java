package com.claro.cpymes.dao;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;


public class TemplateIVRDAO<T> {

   private Class<T> clase;

   @PersistenceUnit(unitName = "IVRPU")
   public EntityManagerFactory entityManagerFactory;

   public final void setClase(Class<T> entity) {
      this.clase = entity;
   }

   public T findOne(long id) throws Exception {

      EntityManager entityManager = entityManagerFactory.createEntityManager();
      T t = entityManager.find(this.clase, id);
      entityManager.close();

      return t;
   }

   @TransactionAttribute(TransactionAttributeType.REQUIRED)
   public void create(T entity) throws Exception {
      EntityManager entityManager = entityManagerFactory.createEntityManager();
      EntityTransaction entityTransaction = entityManager.getTransaction();
      entityTransaction.begin();
      entityManager.persist(entity);
      entityTransaction.commit();
      entityManager.close();
   }

   @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
   public T update(T entity) throws Exception {
      EntityManager entityManager = entityManagerFactory.createEntityManager();
      EntityTransaction entityTransaction = entityManager.getTransaction();
      entityTransaction.begin();
      entity = entityManager.merge(entity);
      entityTransaction.commit();
      entityManager.close();

      return entity;
   }

   @TransactionAttribute(TransactionAttributeType.REQUIRED)
   public void delete(T entity) throws Exception {
      EntityManager entityManager = entityManagerFactory.createEntityManager();
      entityManager.getTransaction().begin();
      entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
      entityManager.getTransaction().commit();
      entityManager.close();
   }

   @TransactionAttribute(TransactionAttributeType.REQUIRED)
   public void deleteById(long entityId) throws Exception {
      EntityManager entityManager = entityManagerFactory.createEntityManager();
      entityManager.getTransaction().begin();
      T entity = this.findOne(entityId);
      this.delete(entity);
      entityManager.getTransaction().commit();
      entityManager.close();

   }

}
