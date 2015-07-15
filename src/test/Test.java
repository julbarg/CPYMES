package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;

import CMBD.EquipoCMBD;

import com.claro.cpymes.ejb.ProcessEJB;


public class Test {

   public void read() throws IOException {
      int i = 0;
      boolean head = false;
      String aFileName = "C:/Users/jbarragan/Desktop/snmptt_cisco.txt";
      final Charset ENCODING = StandardCharsets.UTF_8;
      Path path = Paths.get(aFileName);
      try (BufferedReader reader = Files.newBufferedReader(path, ENCODING)) {
         String line = null;

         while ((line = reader.readLine()) != null) {
            if (line.contains("nameEvent")) {
               System.out.println(line);
            }

         }

      }
   }

   public static void main(String[] args) {
      String IP = "172.30.15.134";
      String interFace = "1/1/14";
      String name = "ZAC-YOP.HELECHOS-CP1";
      EquipoCMBD equipo = ProcessEJB.getEquipo(IP, interFace, name);
      System.out.println("Consultando CMBD IP + Port");
      System.out.println("Ciudad: " + equipo.getCiudad());
      System.out.println("Descripcion: " + equipo.getDescripcion());
      System.out.println("Division: " + equipo.getDivision());
      System.out.println("Codigos de Servicios: ");
      ArrayList<String> codesService = equipo.getCodigosServicio();
      for (String service : codesService) {
         System.out.println("\t" + service);
      }

      System.out.println("");

      interFace = null;
      name = null;
      equipo = ProcessEJB.getEquipo(IP, interFace, name);
      System.out.println("Consultando CMBD IP");
      System.out.println("Ciudad: " + equipo.getCiudad());
      System.out.println("Descripcion: " + equipo.getDescripcion());
      System.out.println("Division: " + equipo.getDivision());
      System.out.println("Codigos de Servicios: ");
      codesService = equipo.getCodigosServicio();
      for (String service : codesService) {
         System.out.println("\t" + service);
      }

   }
}
