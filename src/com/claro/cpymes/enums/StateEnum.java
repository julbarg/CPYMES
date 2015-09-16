package com.claro.cpymes.enums;

/**
 * Define los estados de las alarmas
 * @author jbarragan
 *
 */
public enum StateEnum {

   ACTIVO("A", "Activo"), NO_SAVE("N", "No Save"), INACTIVO("I", "Incactivo");

   private String value;

   private String name;

   StateEnum(String value, String name) {
      this.name = name;
      this.value = value;
   }

   public static String getName(String value) {
      String name = "";
      switch (value) {
         case "A":
            name = StateEnum.ACTIVO.getName();
            break;
         case "I":
            name = StateEnum.INACTIVO.getName();
            break;
         default:
            break;
      }
      return name;
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
