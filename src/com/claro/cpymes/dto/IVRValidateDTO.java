package com.claro.cpymes.dto;

import java.io.Serializable;


public class IVRValidateDTO implements Serializable {

   /**
    * 
    */
   private static final long serialVersionUID = -7828667013858575616L;

   private String eventName;

   private String ip;

   public IVRValidateDTO(String eventName, String ip) {
      this.eventName = eventName;
      this.ip = ip;
   }

   public String getEventName() {
      return eventName;
   }

   public void setEventName(String eventName) {
      this.eventName = eventName;
   }

   public String getIp() {
      return ip;
   }

   public void setIp(String ip) {
      this.ip = ip;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj != null && obj instanceof IVRValidateDTO) {
         IVRValidateDTO key = (IVRValidateDTO) obj;
         try {
            return eventName.toUpperCase().equals(key.eventName.toUpperCase())
               && ip.toUpperCase().equals(key.ip.toUpperCase());
         } catch (Exception e) {
            return false;
         }
      }
      return false;
   }

   @Override
   public int hashCode() {
      return (eventName + ip).hashCode();
   }

}
