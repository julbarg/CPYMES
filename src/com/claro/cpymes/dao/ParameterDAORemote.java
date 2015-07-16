package com.claro.cpymes.dao;

import javax.ejb.Remote;

import com.claro.cpymes.dto.HistoricalRecordsDTO;


@Remote
public interface ParameterDAORemote {

   public String findByName(String name) throws Exception;

   public void updateParameter(String parameter, String value) throws Exception;

   void addCountResgister(String name, int value);

   HistoricalRecordsDTO getHistoricalRecords() throws Exception;
}
