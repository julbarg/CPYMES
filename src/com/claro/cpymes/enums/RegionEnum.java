package com.claro.cpymes.enums;

public enum RegionEnum {

   CENTRO("Centro"), 
   NORTE("Norte"), 
   OCCIDENTE("Occidente");

   private String value;

   private RegionEnum(String value) {
      this.value = value;
   }

   public String getValue() {
      return value;
   }

}
