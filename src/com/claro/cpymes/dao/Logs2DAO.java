package com.claro.cpymes.dao;

import java.util.ArrayList;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.claro.cpymes.entity.Log2Entity;
import com.claro.cpymes.enums.ProcessEnum;
import com.claro.cpymes.util.Constant;


/**
 * LogsDAO - DAO que controla las transaciones a base 
 * de datos de la entidad LogEntity
 * @author jbarragan
 *
 */
@Stateless
@LocalBean
public class Logs2DAO extends TemplateLogs2DAO<Log2Entity> implements Logs2DAORemote {

   private static Logger LOGGER = LogManager.getLogger(Logs2DAO.class.getName());

   /**
    * Obtiene las entidades LogEntity por estado
    * @param procesado Filtro con el que se realiza la consulta
    * @return ArrayList<LogEntity> Lista de entidades encontradas
    */
   @Override
   public ArrayList<Log2Entity> findNoProcess() throws Exception {
      EntityManager entityManager = entityManagerFactory.createEntityManager();

      TypedQuery<Log2Entity> query = entityManager.createNamedQuery("Log2Entity.findNoProcess", Log2Entity.class);

      ArrayList<Log2Entity> results = (ArrayList<Log2Entity>) query.setMaxResults(Constant.MAXIME_RESULT_LOGS)
         .getResultList();

      entityManager.close();

      return results;

   }

   /**
    * Persiste la entidad
    * @param entity Entidad a persistir
    */
   @Override
   @TransactionAttribute(TransactionAttributeType.REQUIRED)
   public Log2Entity update(Log2Entity entity) throws Exception {
      Log2Entity logEntity = new Log2Entity();
      try {
         logEntity = super.update(entity);
      } catch (Exception e) {
         LOGGER.error("Error actualizando registro: " + e);
      }

      return logEntity;
   }

   @Override
   @TransactionAttribute(TransactionAttributeType.REQUIRED)
   public void updateList(ArrayList<Log2Entity> listEntity) throws Exception {
      ArrayList<Integer> seqs = new ArrayList<Integer>();
      if (listEntity.size() == 0) {
         return;
      }
      for (Log2Entity log : listEntity) {
         seqs.add(log.getSeq());
      }
      EntityManager entityManager = entityManagerFactory.createEntityManager();
      entityManager.getTransaction().begin();

      Query query = entityManager.createQuery(getQuery());
      query.setParameter("estado", ProcessEnum.PROCESADO.getValue());
      query.setParameter("seqs", seqs);

      query.executeUpdate();

      entityManager.getTransaction().commit();
      entityManager.close();
   }

   private String getQuery() {
      String query = "UPDATE Log2Entity l SET l.procesados=:estado WHERE l.seq IN :seqs";
      return query;
   }

}
