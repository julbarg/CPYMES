package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


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
      String test = "anuncio.pl[1616]: ALC_PYMES ZAC-BUC.RICAURTE-CP1 Problemas de Alcanzabilidad - 172.30.98.9";
      int start = test.indexOf("ALC_PdYMES");
      int end = test.indexOf("Probldemas");
      System.out.println(start);
      System.out.println(end);
      test = test.substring(start, end).trim();

      
      System.out.println(test);

   }
}
