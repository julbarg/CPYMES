package com.claro.cpymes.util;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.claro.cpymes.dto.KeyCatalogDTO;
import com.claro.cpymes.dto.LogDTO;
import com.claro.cpymes.entity.LogEntity;
import com.claro.cpymes.enums.PriorityEnum;
import com.github.shyiko.mysql.binlog.event.Event;
import com.github.shyiko.mysql.binlog.event.EventType;


public class LogUtil {

   private static Logger LOGGER = LogManager.getLogger(LogUtil.class.getName());

   private static StringTokenizer tokenMain;

   private static StringTokenizer tokenCodeServiceIp;

   private static LogDTO logDTO;

   public static LogDTO mapearLog(LogEntity logEntity) {
      logDTO = new LogDTO();
      try {
         getTokenMain(logEntity);
         mapearLogDTO();
         adicionarInformacionLogDTO(logEntity);

      } catch (FormatLogException e) {

      }

      return logDTO;

   }

   public static void getTokenMain(LogEntity logEntity) throws FormatLogException {
      String mensaje = logEntity.getMsg();
      tokenMain = new StringTokenizer(mensaje, Constant.DELIMETER_SNMPTT);
      if (tokenMain.countTokens() < 6) {
         throw new FormatLogException("FormatLogException: " + mensaje);
      }

   }

   /* 'TFM0775 - httpd: ZTE 172.30.15.195 |
    * ZAC-BOG.TRIARA-CP2 |
    * Niveles estables en la ventana de operacion de potencia optica OLT Cliente 43 P000889 - RAE0003-4$ |
    * |
    * |
    * .1.3.6.1.4.1.3902.1012.3.45.106 |
    * zxGponOltDOWiRestore' */
   /**
    * Metodo encargado de mapear la informacion de los logs
    * Obtiene la IP, NameDevice, NameEvent, OID, Nodo
    * @param token
    * @return LogDTO mapeado
    * @throws FormatLogException 
    */
   private static LogDTO mapearLogDTO() {
      int numberTokens = tokenMain.countTokens();
      String codeServiceIp = validateNextTokenMain();
      tokenCodeServiceIp = new StringTokenizer(codeServiceIp, Constant.DELIMETER_IP);

      validateNextTokenCodeServiceIp().trim(); // codeService
      String ip = validateNextTokenCodeServiceIp().trim();
      ip = getIp(ip);

      String name = validateNextTokenMain().trim();
      String translatedLine = validateNextTokenMain(); // translatedLine

      if (numberTokens > 6) {
         validateNextTokenMain(); // marca
      }

      validateNextTokenMain();
      procesarTranslatedLine(logDTO, translatedLine);

      logDTO.setTranslatedLine(translatedLine);

      String OID = validateNextTokenMain().trim();
      String nameEvent = validateNextTokenMain().trim();

      logDTO.setIp(ip);
      logDTO.setName(name);
      logDTO.setOID(OID);
      logDTO.setNameEvent(nameEvent);

      logDTO.setKey(new KeyCatalogDTO(OID, null));

      logDTO.setNodo(getNodo(name));

      logDTO.setMapeado(true);

      return logDTO;
   }

   private static void adicionarInformacionLogDTO(LogEntity logEntity) {
      logDTO.setSeq(logEntity.getSeq());
      String priority = logEntity.getPriority();
      if (PriorityEnum.CRITIC.getValue().equals(priority)) {
         priority = PriorityEnum.CRITICAL.getValue();
      }
      logDTO.setPriority(priority);
      if (logDTO.getKey() != null) {
         logDTO.getKey().setCriticality(priority);
      }

   }

   /**
    * Obtiene el nodo a partir del NameDevice
    * @param name
    * @return Nodo
    */
   private static String getNodo(String name) {
      String expresion = "[.][A-Z_0-9]+[-]";
      String nodo = "";

      Pattern pattern = Pattern.compile(expresion);
      Matcher matcher = pattern.matcher(name);

      if (matcher.find()) {
         nodo = matcher.group();
         nodo = nodo.replace("-", "");
         nodo = nodo.replace(".", "");
      }
      return nodo;
   }

   private static LogDTO procesarTranslatedLine(LogDTO logDTO, String translatedLine) {
      logDTO.setInterFace(getInterface(translatedLine));
      logDTO.setDescriptionAlarm(getDescripcionAlarma(translatedLine));

      return logDTO;
   }

   private static String validateNextTokenMain() {
      try {
         String value = tokenMain.nextToken();
         return value != null ? value : "";
      } catch (Exception e) {
         LOGGER.error("Validando Tokenizer: " + e);
         return "";
      }

   }

   private static String validateNextTokenCodeServiceIp() {
      try {
         String value = tokenCodeServiceIp.nextToken();
         return value != null ? value : "";
      } catch (Exception e) {
         LOGGER.error("Validando Tokenizer: " + e);
         return "";
      }

   }

   /**
    * Evalua las operaciones a escuchar del Listener MySQL
    * @param sql
    * @param operacionesAEscuchar
    * @return Resultado de la Evaluacion
    */
   public static boolean evaluarOperacion(String sql, ArrayList<String> operacionesAEscuchar) {
      for (String operacion : operacionesAEscuchar) {
         int index = sql.indexOf(operacion);
         if (index != -1) {
            return true;
         }
      }
      return false;
   }

   /**
    * Evalua la equivalencia de eventos configurados y
    * el evento presentado
    * @param eventType
    * @param event
    * @return Resultado de la evaluacion
    */
   public static boolean evaluarEvento(EventType eventType, Event event) {
      return eventType.equals(event.getHeader().getEventType());
   }

   public static String getIp(String ip) {
      Pattern pattern = Pattern.compile(Constant.REGEX_IP);
      Matcher matcher = pattern.matcher(ip);
      if (matcher.find()) {
         ip = matcher.group();
      }
      return ip;
   }

   public static String getInterface(String translatedLine) {
      Pattern pattern = Pattern.compile(Constant.REGEX_INTERFACE);
      Matcher matcher = pattern.matcher(translatedLine);

      if (matcher.find()) {
         return matcher.group();
      } else {
         return "";
      }
   }

   /**
    * Obtien la descripcion apartir de translatedLine
    * @param translatedLine
    * @return Descripcion de Alarma
    */
   public static String getDescripcionAlarma(String translatedLine) {
      String descripcionAlarma = "";
      try {
         descripcionAlarma = translatedLine.replaceAll(Constant.REGEX_INTERFACE, "");
         descripcionAlarma = descripcionAlarma.replaceAll(Constant.REGEX_LOWECASE, "");
         descripcionAlarma = descripcionAlarma.replaceAll(Constant.REGEX_UPPERCASECASE_ALONE, " ");
         descripcionAlarma = descripcionAlarma.replaceAll(Constant.REGEX_UPPERCASECASE_ALONE_2, "");
         descripcionAlarma = descripcionAlarma.replaceAll(Constant.REGEX_GUION_NUMBER, " ");
         descripcionAlarma = descripcionAlarma.replaceAll(Constant.REGEX_POINT_NUMBER, " ");
         descripcionAlarma = descripcionAlarma.replaceAll(Constant.REGEX_GUION, "");
         descripcionAlarma = descripcionAlarma.replaceAll(Constant.REGEX_WHITESPACE_INIT, "");
         descripcionAlarma = descripcionAlarma.replaceAll(Constant.REGEX_WHITESPACE, " ");
         descripcionAlarma = descripcionAlarma.replaceAll(Constant.CHARACTER_ESPECIAL, " ");

         descripcionAlarma = descripcionAlarma.trim();

         return descripcionAlarma;
      } catch (Exception e) {
         return descripcionAlarma;
      }
   }
}
