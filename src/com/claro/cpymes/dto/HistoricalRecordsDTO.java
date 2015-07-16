package com.claro.cpymes.dto;

import java.io.Serializable;


public class HistoricalRecordsDTO implements Serializable {

   /**
    * 
    */
   private static final long serialVersionUID = 2796278197145312661L;

   private String sendFromKou;

   private String sendToCPYMESNoActive;

   private String sendToCPYMESActive;

   private String sendToIVR;

   private String alarmRestoreCPYMES;

   private String alarmRestoreIVR;

   public String getSendFromKou() {
      return sendFromKou;
   }

   public void setSendFromKou(String sendFromKou) {
      this.sendFromKou = sendFromKou;
   }

   public String getSendToCPYMESNoActive() {
      return sendToCPYMESNoActive;
   }

   public void setSendToCPYMESNoActive(String sendToCPYMESNoActive) {
      this.sendToCPYMESNoActive = sendToCPYMESNoActive;
   }

   public String getSendToCPYMESActive() {
      return sendToCPYMESActive;
   }

   public void setSendToCPYMESActive(String sendToCPYMESActive) {
      this.sendToCPYMESActive = sendToCPYMESActive;
   }

   public String getSendToIVR() {
      return sendToIVR;
   }

   public void setSendToIVR(String sendToIVR) {
      this.sendToIVR = sendToIVR;
   }

   public String getAlarmRestoreCPYMES() {
      return alarmRestoreCPYMES;
   }

   public void setAlarmRestoreCPYMES(String alarmRestoreCPYMES) {
      this.alarmRestoreCPYMES = alarmRestoreCPYMES;
   }

   public String getAlarmRestoreIVR() {
      return alarmRestoreIVR;
   }

   public void setAlarmRestoreIVR(String alarmRestoreIVR) {
      this.alarmRestoreIVR = alarmRestoreIVR;
   }

}
