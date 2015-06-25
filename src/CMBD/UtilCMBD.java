package CMBD;

import java.util.ArrayList;
import java.util.Random;


public class UtilCMBD {

   public static ArrayList<String> getRandomNitsCRM() {
      ConstanteCMBD constante = new ConstanteCMBD();
      ArrayList<String> result = new ArrayList<String>();
      Random randomGenerator = new Random();
      int numberOfRwgisters = randomGenerator.nextInt(9);
      for (int i = 0; i < numberOfRwgisters; i++) {
         randomGenerator = new Random();
         int register = randomGenerator.nextInt(constante.getListNitCodeServices().size());
         result.add(constante.getListNitCodeServices().get(register));
      }
      return result;
   }

   public static String getCiudad() {
      ConstanteCMBD constante = new ConstanteCMBD();
      Random randomGenerator = new Random();
      int ciudadId = randomGenerator.nextInt(7);
      return constante.getCiudades().get(ciudadId);
   }

}
