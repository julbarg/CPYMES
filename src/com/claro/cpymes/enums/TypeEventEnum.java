package com.claro.cpymes.enums;

public enum TypeEventEnum {

   NODO("N", "Nodo"), 
   FIBRA("F", "Fibra"), 
   TRONCAL("T", "Troncal"), 
   CLIENTE("C", "Cliente"), 
   FASTETHERNET("E", "FastEthernet"), 
   EQUIPO("Q", "Equipo"), 
   MULTIPLE("M", "Multiple");

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

   public static String getName(String value) {
      String name = "";
      switch (value) {
         case "N":
            name = TypeEventEnum.NODO.getName();
            break;
         case "F":
            name = TypeEventEnum.FIBRA.getName();
            break;
         case "T":
            name = TypeEventEnum.TRONCAL.getName();
            break;
         case "C":
            name = TypeEventEnum.CLIENTE.getName();
            break;
         case "E":
            name = TypeEventEnum.FASTETHERNET.getName();
            break;
         case "Q":
            name = TypeEventEnum.EQUIPO.getName();
            break;
         case "M":
            name = TypeEventEnum.MULTIPLE.getName();
            break;
         default:
            break;
      }
      return name;
   }

}
