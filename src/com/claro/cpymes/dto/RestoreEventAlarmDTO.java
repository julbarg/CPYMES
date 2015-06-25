package com.claro.cpymes.dto;

import java.io.Serializable;
import java.util.Date;


public class RestoreEventAlarmDTO implements Serializable {

   /**
    * 
    */
   private static final long serialVersionUID = 1L;

   private String evenRestore;

   private String[] eventTrigger;

   private String ip;

   private Date date;

   public RestoreEventAlarmDTO(String eventRestore, String eventTrigger[], String ip, Date date) {
      this.evenRestore = eventRestore;
      this.eventTrigger = eventTrigger;
      this.ip = ip;
      this.date = date;
   }

   public String getEvenRestore() {
      return evenRestore;
   }

   public void setEvenRestore(String evenRestore) {
      this.evenRestore = evenRestore;
   }

   public String[] getEventTrigger() {
      return eventTrigger;
   }

   public void setEventTrigger(String eventTrigger[]) {
      this.eventTrigger = eventTrigger;
   }

   public String getIp() {
      return ip;
   }

   public void setIp(String ip) {
      this.ip = ip;
   }

   public Date getDate() {
      return date;
   }

   public void setDate(Date date) {
      this.date = date;
   }

}
