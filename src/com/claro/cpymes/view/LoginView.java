package com.claro.cpymes.view;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.claro.cpymes.dto.UserDTO;
import com.claro.cpymes.ejb.remote.LoginEJBRemote;
import com.claro.cpymes.util.Constant;
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
            Util.redirectURL(Constant.URL_IVR);
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

   public void closeSession() {
      Util.logout();
      user = new UserDTO();
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

}
