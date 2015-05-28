package com.claro.cpymes.ejb.remote;

import java.util.ArrayList;

import javax.ejb.Remote;

import com.claro.cpymes.dto.AlarmaPymeIVRDTO;
import com.claro.cpymes.entity.AlarmaPymesServicioNitIVREntity;


@Remote
public interface IVREJBRemote {

   public ArrayList<AlarmaPymeIVRDTO> findAllAlarmIVR() throws Exception;

   public void edit(AlarmaPymeIVRDTO alarmEdit) throws Exception;

   public ArrayList<AlarmaPymesServicioNitIVREntity> findCodigosServicio(AlarmaPymeIVRDTO alarmFind) throws Exception;

   public ArrayList<AlarmaPymeIVRDTO> findByFilter(AlarmaPymeIVRDTO alarmaFilter) throws Exception;

}
