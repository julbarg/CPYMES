package com.claro.cpymes.dao;

import javax.ejb.Remote;


@Remote
public interface ParameterDAORemote {

   public String findByName(String name) throws Exception;

   public void updateParameter(String fechaUltimoCargueNits, String dateString) throws Exception;
}
