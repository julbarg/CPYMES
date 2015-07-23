package com.claro.cpymes.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.claro.cpymes.entity.FechaEsperanzaEntity;


@Stateless
@LocalBean
public class FechaEsperanzaDAO extends TemplateDAO<FechaEsperanzaEntity> implements FechaEsperanzaDAORemote {

   @Override
   public int getHourRecovery(String divisional, String causa) throws Exception {
      EntityManager entityManager = entityManagerFactory.createEntityManager();
      entityManager.getTransaction().begin();

      Query query = entityManager.createNamedQuery("FechaEsperanzaEntity.findByDivisionalAndCausa");
      query.setParameter("divisional", divisional);
      query.setParameter("causa", causa);

      int numeroHoras = (int) query.getSingleResult();

      entityManager.getTransaction().commit();
      entityManager.close();

      return numeroHoras;
   }
}
