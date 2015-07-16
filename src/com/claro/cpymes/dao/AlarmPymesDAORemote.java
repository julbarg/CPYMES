package com.claro.cpymes.dao;

import java.util.ArrayList;
import java.util.Date;

import javax.ejb.Remote;

import com.claro.cpymes.dto.LogDTO;
import com.claro.cpymes.dto.RestoreEventAlarmDTO;
import com.claro.cpymes.entity.AlarmPymesEntity;


/**
 * Interface remota de AlarmPymesDAO
 * @author jbarragan
 *
 */
@Remote
public interface AlarmPymesDAORemote {

   public AlarmPymesEntity findOne(long id) throws Exception;

   public void create(AlarmPymesEntity entity) throws Exception;

   public AlarmPymesEntity update(AlarmPymesEntity entity) throws Exception;

   public void delete(AlarmPymesEntity entity) throws Exception;

   public void deleteById(long entityId) throws Exception;

   public ArrayList<AlarmPymesEntity> findByEstado(String estado) throws Exception;

   public ArrayList<AlarmPymesEntity> findSimiliarCEP(String nodo, String nameCorrelation, Date startDate, Date endDate)
      throws Exception;

   public ArrayList<AlarmPymesEntity> findSimiliarCEP(String nodo, String nameCorrelation, Date date) throws Exception;

   public ArrayList<AlarmPymesEntity> findSimiliarCEPReconocidas(Date startDate, Date endDate) throws Exception;

   public ArrayList<AlarmPymesEntity> findByPriority(ArrayList<String> listPrioritySelect) throws Exception;

   public ArrayList<LogDTO> createList(ArrayList<LogDTO> listLog) throws Exception;

   public int clearAlarm(ArrayList<RestoreEventAlarmDTO> listRestore) throws Exception;

}
