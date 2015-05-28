package com.claro.cpymes.view;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.claro.cpymes.dto.UserDTO;
import com.claro.cpymes.ejb.remote.LoginEJBRemote;
import com.claro.cpymes.util.Messages;
import com.claro.cpymes.util.Util;


/**
 * Controla la vista login.xhtml
 * @author jbarragan
 *
 */
@ManagedBean(name = "loginView")
@SessionScoped
public class LoginView {

   private static Logger LOGGER = LogManager.getLogger(LoginView.class.getName());

   private UserDTO user;

   @EJB
   private LoginEJBRemote loginEJB;

   private static final String URL_LOGIN = "login.xhtml";

   @ManagedProperty(value = "#{ivr}")
   private IVRView ivr;

   @PostConstruct
   public void initialize() {
      user = new UserDTO();
      FacesContext.getCurrentInstance().getExternalContext().getSession(true);
   }

   /**
    * Verifica autenticacion
    * @return Redireccion de pagina
    */
   public boolean authenticate() {
      try {
         LOGGER.info("AUTENTICAR - IVR");
         if (loginEJB.authenticate(user)) {
            Util.iniciarSesion(user);
            ivr.initial();
            return true;
         }
         Util.addMessageFatal(Messages.AUTHENTICATION_ERROR);
         return false;

      } catch (Exception e) {
         LOGGER.error(Messages.AUTHENTICATION_ERROR, e);
         Util.addMessageFatal(Messages.AUTHENTICATION_ERROR);
         return false;
      }

   }

   public void validateSession() {
      try {
         Util.getUserName();
      } catch (Exception e) {
         goLogIn();
      }
   }

   public void closeSession() {
      goLogIn();
      Util.logout();
      user = new UserDTO();
   }

   public void goLogIn() {
      try {
         Util.redirect(URL_LOGIN);
      } catch (IOException e) {
      }
   }

   public UserDTO getUser() {
      return user;
   }

   public void setUser(UserDTO user) {
      this.user = user;
   }

   public LoginEJBRemote getLoginEJB() {
      return loginEJB;
   }

   public void setLoginEJB(LoginEJBRemote loginEJB) {
      this.loginEJB = loginEJB;
   }

   public IVRView getIvr() {
      return ivr;
   }

   public void setIvr(IVRView ivr) {
      this.ivr = ivr;
   }

}
