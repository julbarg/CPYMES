package com.claro.cpymes.dao;

import java.util.ArrayList;

import javax.ejb.Remote;

import com.claro.cpymes.entity.AlarmaPymeIVREntity;
import com.claro.cpymes.entity.AlarmaPymesServicioNitIVREntity;


@Remote
public interface AlarmaPymesServicioNitIVRDAORemote {

   public ArrayList<AlarmaPymesServicioNitIVREntity> findByAlarm(AlarmaPymeIVREntity alarmaPymeIVR) throws Exception;

   public AlarmaPymesServicioNitIVREntity update(AlarmaPymesServicioNitIVREntity alarmaServicioNitIVR) throws Exception;
   
   public AlarmaPymesServicioNitIVREntity updateAlarm(AlarmaPymesServicioNitIVREntity alarmaServicioNitIVR) throws Exception;

}
