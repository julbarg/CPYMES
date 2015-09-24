package com.claro.cpymes.ejb;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import co.com.claro.sisges.ldap.service.LDAPAuthenticationServices;
import co.com.claro.sisges.ldap.service.LDAPAuthenticationServicesServiceLocator;

import com.claro.cpymes.dto.UserDTO;
import com.claro.cpymes.ejb.remote.LoginEJBRemote;


/**
 * Bean que controla la autenticacion al Sistema
 * @author jbarragan
 *
 */
@Stateless
@LocalBean
public class LoginEJB implements LoginEJBRemote {

   private static final String DOMAIN_NAME = "co.attla.corp";

   @Override
   public boolean authenticate(UserDTO user) throws Exception {
      boolean logIn;
      LDAPAuthenticationServicesServiceLocator ldapL = new LDAPAuthenticationServicesServiceLocator();
      LDAPAuthenticationServices query;
      query = ldapL.getLDAPAuthenticationServices();
      logIn = query.userAuthentication(user.getUserName(), user.getPassword(), DOMAIN_NAME);

      return logIn;

   }

}
