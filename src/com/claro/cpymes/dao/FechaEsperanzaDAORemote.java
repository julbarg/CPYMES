package com.claro.cpymes.dao;

import javax.ejb.Remote;


@Remote
public interface FechaEsperanzaDAORemote {

   public int getHourRecovery(String divisional, String causa) throws Exception;

}
