package com.claro.cpymes.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.claro.cpymes.entity.ParametroEntity;


@Stateless
@LocalBean
public class ParameterDAO extends TemplateDAO<ParametroEntity> implements ParameterDAORemote {

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
}
