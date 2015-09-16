package com.claro.cpymes.dao;

import java.util.ArrayList;

import javax.ejb.Remote;

import com.claro.cpymes.dto.AlarmaPymeIVRDTO;
import com.claro.cpymes.dto.DataDTO;
import com.claro.cpymes.dto.InfoTypeAlarmDTO;
import com.claro.cpymes.dto.ReportDTO;
import com.claro.cpymes.dto.RestoreEventAlarmDTO;
import com.claro.cpymes.entity.AlarmaPymeIVREntity;


@Remote
public interface AlarmaPymesIVRDAORemote {

   public ArrayList<AlarmaPymeIVREntity> findByEstado(String estado) throws Exception;

   public AlarmaPymeIVREntity findOne(long id) throws Exception;

   public AlarmaPymeIVREntity update(AlarmaPymeIVREntity alarmaPymeIVR) throws Exception;

   public AlarmaPymeIVREntity findById(long idAlarmaPymes) throws Exception;

   public ArrayList<AlarmaPymeIVREntity> findByFilter(AlarmaPymeIVRDTO filterAlarm) throws Exception;

   public int clearAlarm(ArrayList<RestoreEventAlarmDTO> listRestore) throws Exception;

   public int clearAlarm(RestoreEventAlarmDTO restore) throws Exception;

   public AlarmaPymeIVREntity updateAlarm(AlarmaPymeIVREntity alarmaIVR) throws Exception;

   public ArrayList<DataDTO> findDataByFilter(ReportDTO reportDTO) throws Exception;

   public ArrayList<InfoTypeAlarmDTO> findReportByRegion(String nameRegion) throws Exception;

}
