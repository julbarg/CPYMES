package com.claro.cpymes.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.claro.cpymes.dto.UserDTO;
import com.claro.cpymes.listener.Connection;


/**
 * Util para el manejo grafico de la aplicacion
 * @author jbarragan
 *
 */
public class Util {

   private static Logger LOGGER = LogManager.getLogger(Connection.class.getName());

   /**
    * Adicionar Fatal Message a la vista
    * @param fatalMsg
    */
   public static void addMessageFatal(String fatalMsg) {
      FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, fatalMsg, null);
      FacesContext.getCurrentInstance().addMessage(null, message);
   }

   public static void addMessageFatalKeep(String fatalMsg) {
      FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, fatalMsg, null);
      FacesContext.getCurrentInstance().addMessage(null, message);
      FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
   }

   public static void addMessageInfo(String mensaje) {
      FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, null);
      FacesContext.getCurrentInstance().addMessage(null, message);
   }

   /**
    * Obtener propertie
    * @param propertie
    * @return Propertie
    */
   public static String getProperties(String propertie) {

      final Properties prop = new Properties();
      InputStream input = null;
      try {
         input = new FileInputStream(Constant.PATH_CONFIG_PROPERTIES);
         prop.load(input);
         return prop.getProperty(propertie);
      } catch (final IOException ex) {
         LOGGER.error("Error getProperties", ex);
         return propertie;
      } finally {
         if (input != null) {
            try {
               input.close();
            } catch (final IOException e) {
               LOGGER.error("Error getProperties", e);
            }
         }
      }

   }

   /**
    * Obtener propertie entero
    * @param propertie
    * @return Propertie Int
    */
   public static int getPropertiesInt(String propertie) {

      final Properties prop = new Properties();
      InputStream input = null;
      int propertieInt = 0;
      try {
         input = new FileInputStream(Constant.PATH_CONFIG_PROPERTIES);
         prop.load(input);
         String propertieString = prop.getProperty(propertie);
         propertieInt = Integer.parseInt(propertieString);
         return propertieInt;

      } catch (final IOException ex) {
         LOGGER.error("Error getPropertiesInt", ex);
         return propertieInt;
      } finally {
         if (input != null) {
            try {
               input.close();
            } catch (final IOException e) {
               LOGGER.error("getPropertiesInt", e);
            }
         }
      }

   }

   /**
    * Restarle a fecha minutos
    * @param date
    * @param minutes
    * @return Date restada
    */
   public static Date restarFecha(Date date, int minutes) {
      long ONE_MINUTE_IN_MILLIS = 60000;
      long newFechaL = date.getTime() - (minutes * ONE_MINUTE_IN_MILLIS);
      Date newFecha = new Date(newFechaL);

      return newFecha;
   }

   public static HttpSession getSession() {
      return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
   }

   public static void iniciarSesion(UserDTO userName) {
      Util.getSession().setAttribute(Constant.USER_NAME, userName.getUserName());
   }

   public static String getUserName() throws Exception {
      HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
      return session.getAttribute(Constant.USER_NAME).toString();
   }

   public static boolean validateSesion() {
      try {
         Util.getUserName();
         return true;
      } catch (Exception e) {
         LOGGER.error("Error de Sesion", e);
         Util.addMessageFatal("No ha iniciado sesion");
         return false;

      }
   }

   public static void redirect(String url) throws IOException {
      FacesContext.getCurrentInstance().getExternalContext().redirect(url);
   }

   public static void logout() {
      HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
      session.invalidate();
   }

   public static Date getDateStringToDate(String dateString) {
      DateFormat format = new SimpleDateFormat("dd/MM/yy");
      Date date;
      try {
         date = format.parse(dateString);
      } catch (ParseException e) {
         return null;
      }
      return date;

   }

   public static long getHoursBetweenTwoDates(Date startDate, Date endDate) {
      long difference = (endDate.getTime() - startDate.getTime()) / 1000;
      long hours = difference / 3600;
      return hours;
   }

   public static Date addHoursToDate(Date today, int numberHours) {
      return DateUtils.addHours(today, numberHours);
      
   }
}
