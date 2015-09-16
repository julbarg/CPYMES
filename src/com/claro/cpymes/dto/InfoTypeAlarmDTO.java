package com.claro.cpymes.dto;

import java.io.Serializable;


public class InfoTypeAlarmDTO implements Serializable {

   /**
    * 
    */
   private static final long serialVersionUID = 7522844618225364375L;

   private String nameTypeAlarm;

   private int noAlarms;

   private int noNitsAffected;

   private int noCodesAffected;

   public String getNameTypeAlarm() {
      return nameTypeAlarm;
   }

   public void setNameTypeAlarm(String nameTypeAlarm) {
      this.nameTypeAlarm = nameTypeAlarm;
   }

   public int getNoAlarms() {
      return noAlarms;
   }

   public void setNoAlarms(int noAlarms) {
      this.noAlarms = noAlarms;
   }

   public int getNoNitsAffected() {
      return noNitsAffected;
   }

   public void setNoNitsAffected(int noNitsAffected) {
      this.noNitsAffected = noNitsAffected;
   }

   public int getNoCodesAffected() {
      return noCodesAffected;
   }

   public void setNoCodesAffected(int noCodesAffected) {
      this.noCodesAffected = noCodesAffected;
   }

}
