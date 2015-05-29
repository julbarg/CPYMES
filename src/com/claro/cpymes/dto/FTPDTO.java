package com.claro.cpymes.dto;

import java.io.Serializable;

import com.claro.cpymes.util.Constant;


public class FTPDTO implements Serializable {

   /**
    * 
    */
   private static final long serialVersionUID = -7595289007139487980L;

   private String server;

   private int port;

   private String userName;

   private String password;

   private String workingDir;

   private String protocol;

   public FTPDTO() {
      this.server = Constant.SERVER_FTP;
      this.port = Constant.PORT_FTP;
      this.userName = Constant.USER_NAME_FTP;
      this.password = Constant.PASSWORD_FTP;
      this.workingDir = Constant.FTP_WORKING_DIR;
      this.protocol = Constant.PROTOCOL_FTP;
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
