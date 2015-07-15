package com.claro.cpymes.enums;

public enum TypeEventEnum {

   TRONCAL("T", "Troncal", "Fibra"), 
   CLIENTE("C", "Cliente", "Fibra"), 
   FASTETHERNET("E", "FastEthernet", "Fibra"), 
   EQUIPO("Q", "Equipo", "Nodo"),
   MULTIPLE("M", "Multiple", "Fibra");

   private String value;

   private String name;

   private String type;

   TypeEventEnum(String value, String name, String type) {
      this.name = name;
      this.value = value;
      this.setType(type);
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

   public String getType() {
      return type;
   }

   public void setType(String type) {
      this.type = type;
   }

}
