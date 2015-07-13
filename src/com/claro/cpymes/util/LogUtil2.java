package com.claro.cpymes.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.claro.cpymes.dto.LogDTO;
import com.claro.cpymes.entity.Log2Entity;


public class LogUtil2 {

   private static LogDTO logDTO;

   public static LogDTO mapearLog(Log2Entity log2Entity) {
      logDTO = new LogDTO();
      String msg = log2Entity.getMsg();
      if (msg.contains(Constant.REACHABILITY)) {
         logDTO.setName(getName(msg));
         logDTO.setIp(getIp(msg));
         logDTO.setPriority(log2Entity.getPriority());
         logDTO.setNameEvent(Constant.REACHABILITY_EVENT_NAME);
         logDTO.setTranslatedLine(msg);
         logDTO.setMapeado(true);
      }

      return logDTO;

   }

   private static String getName(String msg) {
      String name = "";
      int start = msg.indexOf("ALC_PYMES");
      int end = msg.indexOf("Problemas");

      if (start != -1 && end != -1) {
         start = start + "ALC_PYMES".length();
         name = msg.substring(start, end).trim();
      }

      return name;
   }

   public static String getIp(String ip) {
      Pattern pattern = Pattern.compile(Constant.REGEX_IP);
      Matcher matcher = pattern.matcher(ip);
      if (matcher.find()) {
         ip = matcher.group();
      }
      return ip;
   }
}
