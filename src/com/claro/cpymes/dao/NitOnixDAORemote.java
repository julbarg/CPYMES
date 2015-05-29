package com.claro.cpymes.dao;

import java.util.ArrayList;

import javax.ejb.Remote;

import com.claro.cpymes.entity.NitOnixEntity;


@Remote
public interface NitOnixDAORemote {

   public ArrayList<NitOnixEntity> findByEstado(String filter) throws Exception;

   public void createList(ArrayList<NitOnixEntity> listNitOnix) throws Exception;

   public void removeAll() throws Exception;

}
