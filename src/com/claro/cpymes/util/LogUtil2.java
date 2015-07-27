package com.claro.cpymes.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.claro.cpymes.dto.LogDTO;
import com.claro.cpymes.entity.Log2Entity;
import com.claro.cpymes.enums.PriorityEnum;


/**
 * Ayuda a mapear la informacion obtenia de BD KOU Equipo a LogDTO
 * @author jbarragan
 *
 */
public class LogUtil2 {

   private static LogDTO logDTO;

   public static LogDTO mapearLog(Log2Entity log2Entity) {
      logDTO = new LogDTO();
      String msg = log2Entity.getMsg();
      if (msg.contains(Constant.REACHABILITY)) {
         logDTO.setName(getName(msg, log2Entity.getPriority()));
         logDTO.setIp(getIp(msg));
         logDTO.setPriority(log2Entity.getPriority());
         logDTO.setTranslatedLine(msg);
         logDTO.setMapeado(true);
         if (log2Entity.getPriority().equals(PriorityEnum.ALERT.getValue())) {
            logDTO.setNameEvent(Constant.REACHABILITY_EVENT_NAME);
         } else if (log2Entity.getPriority().equals(PriorityEnum.NOTICE.getValue())) {
            logDTO.setNameEvent(Constant.REACHABILITY_RESTORE_EVENT_NAME);
         }
      }

      return logDTO;

   }

   private static String getName(String msg, String priority) {
      String name = "";
      int start = msg.indexOf(Constant.INDEX_OF_START_MSG_KOU);
      int end = 0;
      if (priority.equals(PriorityEnum.ALERT.getValue())) {
         end = msg.indexOf(Constant.INDEX_OF_END_MSG_KOU_DOWN);
      } else if (priority.equals(PriorityEnum.NOTICE.getValue())) {
         end = msg.indexOf(Constant.INDEX_OF_END_MSG_KOU_UP);
      }

      if (start != -1 && end != -1) {
         start = start + Constant.INDEX_OF_START_MSG_KOU.length();
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
