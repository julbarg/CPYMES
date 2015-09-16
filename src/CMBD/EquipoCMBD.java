package CMBD;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * Mapea la Informacion Obtenidad del CMBD
 * @author jbarragan
 *
 */
public class EquipoCMBD implements Serializable {

   /**
    * 
    */
   private static final long serialVersionUID = 1L;

   private String claseEquipo;

   private String descripcion;

   private String ciudad;

   private String division;

   private ArrayList<String> codigosServicio;

   private String divisional;

   public String getClaseEquipo() {
      return claseEquipo;
   }

   public void setClaseEquipo(String claseEquipo) {
      this.claseEquipo = claseEquipo;
   }

   public String getDescripcion() {
      return descripcion;
   }

   public void setDescripcion(String descripcion) {
      this.descripcion = descripcion;
   }

   public String getCiudad() {
      return ciudad;
   }

   public void setCiudad(String ciudad) {
      this.ciudad = ciudad;
   }

   public String getDivision() {
      return division;
   }

   public void setDivision(String division) {
      this.division = division;
   }

   public ArrayList<String> getCodigosServicio() {
      return codigosServicio;
   }

   public void setCodigosServicio(ArrayList<String> codigosServicio) {
      this.codigosServicio = codigosServicio;
   }

   public String getDivisional() {
      return divisional;
   }

   public void setDivisional(String divisional) {
      this.divisional = divisional;
   }

}