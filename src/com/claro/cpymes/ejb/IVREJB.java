package com.claro.cpymes.ejb;

import java.util.ArrayList;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.claro.cpymes.dao.AlarmaPymesIVRDAORemote;
import com.claro.cpymes.dao.AlarmaPymesServicioNitIVRDAORemote;
import com.claro.cpymes.dao.ParameterDAORemote;
import com.claro.cpymes.dto.AlarmaPymeIVRDTO;
import com.claro.cpymes.ejb.remote.IVREJBRemote;
import com.claro.cpymes.entity.AlarmaPymeIVREntity;
import com.claro.cpymes.entity.AlarmaPymesServicioNitIVREntity;
import com.claro.cpymes.enums.StateEnum;
import com.claro.cpymes.util.Constant;
import com.claro.cpymes.util.Util;


/**
 * IVREJB - Bean que controla acciones de IVR
 * @author jbarragan
 *
 */
@Stateless
@LocalBean
public class IVREJB implements IVREJBRemote {

   private static Logger LOGGER = LogManager.getLogger(IVREJB.class.getName());

   @EJB
   private AlarmaPymesIVRDAORemote alarmPymesIVRDAO;

   @EJB
   private AlarmaPymesServicioNitIVRDAORemote alarmaPymesServicioNitIVRDAO;

   @EJB
   private ParameterDAORemote parameterDAO;

   @PostConstruct
   private void initialize() {

   }

   public ArrayList<AlarmaPymeIVRDTO> findAllAlarmIVR() throws Exception {
      ArrayList<AlarmaPymeIVRDTO> listAlarmDTO = new ArrayList<AlarmaPymeIVRDTO>();
      ArrayList<AlarmaPymeIVREntity> listAlarmEntity = alarmPymesIVRDAO.findByEstado(StateEnum.ACTIVO.getValue());
      for (AlarmaPymeIVREntity alarmEntity : listAlarmEntity) {
         AlarmaPymeIVRDTO alarmDTO = mapearEntity(alarmEntity);
         listAlarmDTO.add(alarmDTO);
      }
      return listAlarmDTO;

   }

   @Override
   public void edit(AlarmaPymeIVRDTO alarmEdit) throws Exception {
      AlarmaPymeIVREntity alarmEntity = alarmPymesIVRDAO.findById(alarmEdit.getIdAlarmaPymes());
      alarmEntity.setFechaEsperanza(alarmEdit.getFechaEsperanza());
      alarmEntity.setTicketOnix(alarmEdit.getTicketOnix());
      alarmEntity.setFechaModificacion(alarmEdit.getFechaModificacion());
      alarmEntity.setUsuarioModificacion(alarmEdit.getUsuarioModificacion());

      alarmPymesIVRDAO.update(alarmEntity);
   }

   @Override
   public ArrayList<AlarmaPymesServicioNitIVREntity> findCodigosServicio(AlarmaPymeIVRDTO alarmFind) throws Exception {
      AlarmaPymeIVREntity alarmEntity = alarmPymesIVRDAO.findById(alarmFind.getIdAlarmaPymes());
      return alarmaPymesServicioNitIVRDAO.findByAlarm(alarmEntity);

   }

   public ArrayList<AlarmaPymeIVRDTO> findByFilter(AlarmaPymeIVRDTO alarmaFilter) throws Exception {
      ArrayList<AlarmaPymeIVREntity> listAlarmEntity = alarmPymesIVRDAO.findByFilter(alarmaFilter);
      ArrayList<AlarmaPymeIVRDTO> listAlarmDTO = new ArrayList<AlarmaPymeIVRDTO>();
      for (AlarmaPymeIVREntity alarmEntity : listAlarmEntity) {
         AlarmaPymeIVRDTO alarmDTO = mapearEntity(alarmEntity);
         listAlarmDTO.add(alarmDTO);
      }
      return listAlarmDTO;
   }

   private AlarmaPymeIVRDTO mapearEntity(AlarmaPymeIVREntity alarmEntity) {
      AlarmaPymeIVRDTO alarmDTO = new AlarmaPymeIVRDTO();

      alarmDTO.setCiudad(alarmEntity.getCiudad());
      alarmDTO.setClaseEquipo(alarmEntity.getClaseEquipo());
      alarmDTO.setCodigoAudioIvr(alarmEntity.getCodigoAudioIvr());
      alarmDTO.setDescripcionAlarma(alarmEntity.getDescripcionAlarma());
      alarmDTO.setDivision(alarmEntity.getDivision());
      alarmDTO.setEstadoAlarma(alarmEntity.getEstadoAlarma());
      alarmDTO.setFechaEsperanza(alarmEntity.getFechaEsperanza());
      alarmDTO.setFechaFin(alarmEntity.getFechaFin());
      alarmDTO.setFechaInicio(alarmEntity.getFechaInicio());
      alarmDTO.setFechaModificacion(alarmEntity.getFechaModificacion());
      alarmDTO.setIdAlarmaPymes(alarmEntity.getIdAlarmaPymes());
      alarmDTO.setIp(alarmEntity.getIp());
      alarmDTO.setTicketOnix(alarmEntity.getTicketOnix());
      alarmDTO.setTiempoTotalFalla(alarmEntity.getTiempoTotalFalla());
      alarmDTO.setTipoEvento(alarmEntity.getTipoEvento());
      alarmDTO.setUsuarioModificacion(alarmEntity.getUsuarioModificacion());

      return alarmDTO;
   }

   @Override
   public Date getDateLoadNits() {
      Date date;
      try {
         String dateString = parameterDAO.findByName(Constant.FECHA_ULTIMO_CARGUE_NITS);
         date = Util.getDateStringToDate(dateString);
         return date;
      } catch (Exception e) {
         LOGGER.error("getDateLoadNits", e);
         return null;
      }

   }
}
