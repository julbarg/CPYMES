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

import com.claro.cpymes.entity.LogEntity;
import com.claro.cpymes.enums.ProcessEnum;
import com.claro.cpymes.util.Constant;


/**
 * DAO para LogEntity BD KOU Clientes
 * @author jbarragan
 *
 */
@Stateless
@LocalBean
public class LogsDAO extends TemplateLogsDAO<LogEntity> implements LogsDAORemote {

   private static Logger LOGGER = LogManager.getLogger(LogsDAO.class.getName());

   @Override
   public ArrayList<LogEntity> findByEstado(String procesado) throws Exception {
      EntityManager entityManager = entityManagerFactory.createEntityManager();

      TypedQuery<LogEntity> query = entityManager.createNamedQuery("LogEntity.findByProcesado", LogEntity.class);
      query.setParameter("procesados", procesado);
      ;
      ArrayList<LogEntity> results = (ArrayList<LogEntity>) query.setMaxResults(Constant.MAXIME_RESULT_LOGS)
         .getResultList();

      entityManager.close();

      return results;

   }

   @Override
   @TransactionAttribute(TransactionAttributeType.REQUIRED)
   public LogEntity update(LogEntity entity) throws Exception {
      LogEntity logEntity = new LogEntity();
      try {
         logEntity = super.update(entity);
      } catch (Exception e) {
         LOGGER.error("Error actualizando registro: " + e);
      }

      return logEntity;
   }

   @Override
   @TransactionAttribute(TransactionAttributeType.REQUIRED)
   public void updateList(ArrayList<LogEntity> listEntity) throws Exception {
      if (listEntity.size() == 0) {
         return;
      }
      ArrayList<Integer> seqs = new ArrayList<Integer>();
      for (LogEntity log : listEntity) {
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
      String query = "UPDATE LogEntity l SET l.procesados=:estado WHERE l.seq IN :seqs";
      return query;
   }
}
