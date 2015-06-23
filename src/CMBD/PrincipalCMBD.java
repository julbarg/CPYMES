package CMBD;



public class PrincipalCMBD {

   public static EquipoCMBD getNitsFromCRM(String IP, String interFace, String name) {
      EquipoCMBD equipo = new EquipoCMBD();
      equipo.setClaseEquipo("Clase Equipo CMBD");
      equipo.setDescripcion("Descripcion Equipo CMBD");
      equipo.setCiudad(UtilCMBD.getCiudad());
      equipo.setDivision("Divison CMBD");
      equipo.setCodigosServicio(UtilCMBD.getRandomNitsCRM());

      return equipo;
   }

}
