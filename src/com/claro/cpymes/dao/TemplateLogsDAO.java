package com.claro.cpymes.dao;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;


/**
 * TemplateDAO - Define los metodos genericos en entidades
 * de la BD KOU Clintes
 * @author jbarragan
 *
 * @param <T>
 */
public class TemplateLogsDAO<T> {

   private Class<T> clase;

   @PersistenceUnit(unitName = "LogsPU")
   public EntityManagerFactory entityManagerFactory;

   public final void setClase(Class<T> entity) {
      this.clase = entity;
   }

   public T findOne(long id) throws Exception {
      T t;
      EntityManager entityManager = entityManagerFactory.createEntityManager();
      entityManager.getTransaction().begin();
      t = entityManager.find(this.clase, id);
      entityManager.getTransaction().commit();
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

   @TransactionAttribute(TransactionAttributeType.REQUIRED)
   public T update(T entity) throws Exception {
      T t;
      EntityManager entityManager = entityManagerFactory.createEntityManager();
      entityManager.getTransaction().begin();
      t = entityManager.merge(entity);
      entityManager.getTransaction().commit();
      entityManager.close();

      return t;
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
