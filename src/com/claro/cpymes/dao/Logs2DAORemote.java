package com.claro.cpymes.dao;

import java.util.ArrayList;

import javax.ejb.Remote;

import com.claro.cpymes.entity.Log2Entity;


/**
 * Interface remota de LogsDAO
 * @author jbarragan
 *
 */
@Remote
public interface Logs2DAORemote {

   public ArrayList<Log2Entity> findNoProcess() throws Exception;

   public void updateList(ArrayList<Log2Entity> listEntity) throws Exception;

}
