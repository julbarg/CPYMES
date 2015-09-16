package com.claro.cpymes.ejb;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.claro.cpymes.dao.AlarmaPymesIVRDAORemote;
import com.claro.cpymes.dto.ControlReportDTO;
import com.claro.cpymes.dto.InfoRegionDTO;
import com.claro.cpymes.dto.InfoTypeAlarmDTO;
import com.claro.cpymes.ejb.remote.ControlEJBRemote;
import com.claro.cpymes.enums.RegionEnum;


@Stateless
@LocalBean
public class ControlEJB implements ControlEJBRemote {

   @EJB
   private AlarmaPymesIVRDAORemote alarmaPymesIVRDAO;

   @Override
   public ControlReportDTO loadControl() throws Exception {
      ControlReportDTO controlReport = new ControlReportDTO();
      ArrayList<InfoRegionDTO> listInfoRegionDTO = new ArrayList<InfoRegionDTO>();
      listInfoRegionDTO.add(getInfoRegion(RegionEnum.CENTRO.getValue()));
      listInfoRegionDTO.add(getInfoRegion(RegionEnum.NORTE.getValue()));
      listInfoRegionDTO.add(getInfoRegion(RegionEnum.OCCIDENTE.getValue()));
      controlReport.setListInfoRegionDTO(listInfoRegionDTO);

      controlReport = getTotal(controlReport, listInfoRegionDTO);

      return controlReport;
   }

   private ControlReportDTO getTotal(ControlReportDTO controlReport, ArrayList<InfoRegionDTO> listInfoRegionDTO) {

      int sumNoAlarmsTotal = 0;

      int sumNoNitsAffectedTotal = 0;

      int sumNoCodesAffectedTotal = 0;

      for (InfoRegionDTO infoRegionDTO : listInfoRegionDTO) {
         sumNoAlarmsTotal = sumNoAlarmsTotal + infoRegionDTO.getSumNoAlarms();
         sumNoNitsAffectedTotal = sumNoNitsAffectedTotal + infoRegionDTO.getSumNoNitsAffected();
         sumNoCodesAffectedTotal = sumNoCodesAffectedTotal + infoRegionDTO.getSumNoCodesAffected();
      }

      controlReport.setSumNoAlarmsTotal(sumNoAlarmsTotal);
      controlReport.setSumNoNitsAffectedTotal(sumNoNitsAffectedTotal);
      controlReport.setSumNoCodesAffectedTotal(sumNoCodesAffectedTotal);

      return controlReport;
   }

   private InfoRegionDTO getInfoRegion(String nameRegion) throws Exception {

      InfoRegionDTO infoRegion = new InfoRegionDTO();
      infoRegion.setNameRegion(nameRegion);
      ArrayList<InfoTypeAlarmDTO> listInfoTypeAlarm = alarmaPymesIVRDAO.findReportByRegion(nameRegion);

      int sumNoAlarms = 0;
      int sumNoCodesAffected = 0;
      int sumNoNitsAffected = 0;

      for (InfoTypeAlarmDTO typeAlarm : listInfoTypeAlarm) {
         sumNoAlarms = sumNoAlarms + typeAlarm.getNoAlarms();
         sumNoCodesAffected = sumNoCodesAffected + typeAlarm.getNoCodesAffected();
         sumNoNitsAffected = sumNoNitsAffected + typeAlarm.getNoNitsAffected();

      }
      infoRegion.setSumNoAlarms(sumNoAlarms);
      infoRegion.setSumNoCodesAffected(sumNoCodesAffected);
      infoRegion.setSumNoNitsAffected(sumNoNitsAffected);

      infoRegion.setListInfoTypeAlarm(listInfoTypeAlarm);

      return infoRegion;
   }

}
