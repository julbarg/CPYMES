package test;

import java.util.ArrayList;
import java.util.StringTokenizer;

import com.claro.cpymes.util.Constant;


public class Test {

   public static void main(String[] args) {
      String inter = "0/4/4, 0/6/32, 0/2/14, 0/4/4, 0/9/21 ";
      if (inter.contains("0/4/4")) {
         System.out.println("Sip");
      } else {
         System.out.println("Nop");
      }

   }
}
