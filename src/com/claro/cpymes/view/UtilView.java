package com.claro.cpymes.view;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.claro.cpymes.exceptions.SessionException;
import com.claro.cpymes.util.Util;


@ManagedBean(name = "util")
@SessionScoped
public class UtilView {

   private String userName = "";

   public boolean validateAuthentication() {
      if (Util.validateLogIn()) {
         userName = getUserNameSession();
         return true;
      }
      return false;
   }

   private String getUserNameSession() {
      try {
         return Util.getUserName();
      } catch (SessionException e) {
         return "NO USER";
      }
   }

   public boolean validateLogIn() {
      return Util.validateLogIn();
   }

   public String getUserName() {
      return userName;
   }

   public void setUserName(String userName) {
      this.userName = userName;
   }

}
