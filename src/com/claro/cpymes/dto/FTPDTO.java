package com.claro.cpymes.dto;

import java.io.Serializable;

import com.claro.docsisejb.util.Util;

public class FTPDTO implements Serializable{
   
   /**
    * 
    */
   private static final long serialVersionUID = -7595289007139487980L;

   public static final String SERVER_FTP = "SERVER_FTP";

   public static final String PORT_FTP = "PORT_FTP";

   public static final String USER_NAME_FTP = "USER_NAME_FTP";

   public static final String PASSWOR_FTP = "PASSWOR_FTP";

   public static final String FTP_WORKING_DIR = "FTP_WORKING_DIR";

   private static final String PROTOCOL_FTP = "PROTOCOL_FTP";

   public static final String EXPRESION_REGULAR = "EXPRESION_REGULAR";

   private String server;
   
   private int port;
   
   private String userName;
   
   private String password;
   
   private String workingDir;
   
   private String protocol;
   
   public FTPDTO() {
      this.server = Util.readProperties(SERVER_FTP);
      this.port = Util.readPropertiesInt(PORT_FTP);
      this.userName = Util.readProperties(USER_NAME_FTP);
      this.password = Util.readProperties(PASSWOR_FTP);
      this.workingDir = Util.readProperties(FTP_WORKING_DIR);
      this.protocol = Util.readProperties(PROTOCOL_FTP);
   }

   public String getServer() {
      return server;
   }

   public void setServer(String server) {
      this.server = server;
   }

   public int getPort() {
      return port;
   }

   public void setPort(int port) {
      this.port = port;
   }

   public String getUserName() {
      return userName;
   }

   public void setUserName(String userName) {
      this.userName = userName;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getWorkingDir() {
      return workingDir;
   }

   public void setWorkingDir(String workingDir) {
      this.workingDir = workingDir;
   }

   public String getProtocol() {
      return protocol;
   }

   public void setProtocol(String protocol) {
      this.protocol = protocol;
   }
}
