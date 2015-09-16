package com.claro.cpymes.dto;

import java.io.Serializable;
import java.util.ArrayList;


public class InfoRegionDTO implements Serializable {

   /**
    * 
    */
   private static final long serialVersionUID = -7469290923172166154L;

   private String nameRegion;

   private ArrayList<InfoTypeAlarmDTO> listInfoTypeAlarm;

   private int sumNoAlarms;

   private int sumNoNitsAffected;

   private int sumNoCodesAffected;

   public String getNameRegion() {
      return nameRegion;
   }

   public void setNameRegion(String nameRegion) {
      this.nameRegion = nameRegion;
   }

   public ArrayList<InfoTypeAlarmDTO> getListInfoTypeAlarm() {
      return listInfoTypeAlarm;
   }

   public void setListInfoTypeAlarm(ArrayList<InfoTypeAlarmDTO> listInfoTypeAlarm) {
      this.listInfoTypeAlarm = listInfoTypeAlarm;
   }

   public int getSumNoAlarms() {
      return sumNoAlarms;
   }

   public void setSumNoAlarms(int sumNoAlarms) {
      this.sumNoAlarms = sumNoAlarms;
   }

   public int getSumNoNitsAffected() {
      return sumNoNitsAffected;
   }

   public void setSumNoNitsAffected(int sumNoNitsAffected) {
      this.sumNoNitsAffected = sumNoNitsAffected;
   }

   public int getSumNoCodesAffected() {
      return sumNoCodesAffected;
   }

   public void setSumNoCodesAffected(int sumNoCodesAffected) {
      this.sumNoCodesAffected = sumNoCodesAffected;
   }

}
