package com.claro.cpymes.dao;

import java.util.ArrayList;

import javax.ejb.Remote;

import com.claro.cpymes.entity.LogEntity;


/**
 * Interface remota de LogsDAO
 * @author jbarragan
 *
 */
@Remote
public interface LogsDAORemote {

   public LogEntity findOne(long id) throws Exception;

   public void create(LogEntity entity) throws Exception;

   public LogEntity update(LogEntity entity) throws Exception;

   public void delete(LogEntity entity) throws Exception;

   public void deleteById(long entityId) throws Exception;

   public ArrayList<LogEntity> findByEstado(String estado) throws Exception;
   
   public void updateList(ArrayList<LogEntity> listEntity) throws Exception;

}
