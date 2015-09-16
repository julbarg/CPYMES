package com.claro.cpymes.dto;

import java.io.Serializable;
import java.util.ArrayList;


public class ControlReportDTO implements Serializable {

   /**
    * 
    */
   private static final long serialVersionUID = -8238858322934819573L;

   ArrayList<InfoRegionDTO> listInfoRegionDTO;

   private int sumNoAlarmsTotal;

   private int sumNoNitsAffectedTotal;

   private int sumNoCodesAffectedTotal;

   public ArrayList<InfoRegionDTO> getListInfoRegionDTO() {
      return listInfoRegionDTO;
   }

   public void setListInfoRegionDTO(ArrayList<InfoRegionDTO> listInfoRegionDTO) {
      this.listInfoRegionDTO = listInfoRegionDTO;
   }

   public int getSumNoAlarmsTotal() {
      return sumNoAlarmsTotal;
   }

   public void setSumNoAlarmsTotal(int sumNoAlarmsTotal) {
      this.sumNoAlarmsTotal = sumNoAlarmsTotal;
   }

   public int getSumNoNitsAffectedTotal() {
      return sumNoNitsAffectedTotal;
   }

   public void setSumNoNitsAffectedTotal(int sumNoNitsAffectedTotal) {
      this.sumNoNitsAffectedTotal = sumNoNitsAffectedTotal;
   }

   public int getSumNoCodesAffectedTotal() {
      return sumNoCodesAffectedTotal;
   }

   public void setSumNoCodesAffectedTotal(int sumNoCodesAffectedTotal) {
      this.sumNoCodesAffectedTotal = sumNoCodesAffectedTotal;
   }

}
