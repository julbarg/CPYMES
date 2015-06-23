package com.claro.cpymes.enums;

public enum TypeEventEnum {

   TRONCAL("T", "Troncal"), PUERTO("P", "Puerto"), EQUIPO("E", "Equipo");

   private String value;

   private String name;

   TypeEventEnum(String value, String name) {
      this.name = name;
      this.value = value;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getValue() {
      return value;
   }

   public void setValue(String value) {
      this.value = value;
   }

}
