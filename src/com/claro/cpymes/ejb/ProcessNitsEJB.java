package com.claro.cpymes.ejb;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.claro.cpymes.dao.NitOnixDAORemote;
import com.claro.cpymes.dao.ParameterDAORemote;
import com.claro.cpymes.ejb.remote.ProcessNitsRemote;
import com.claro.cpymes.ejb.remote.SFTPRemote;
import com.claro.cpymes.entity.NitOnixEntity;
import com.claro.cpymes.exceptions.FileNitEmptyException;
import com.claro.cpymes.exceptions.PercentageDifferenceException;
import com.claro.cpymes.util.Constant;


@Stateless
@LocalBean
public class ProcessNitsEJB implements ProcessNitsRemote {

   private static Logger LOGGER = LogManager.getLogger(ProcessNitsEJB.class.getName());

   @EJB
   private SFTPRemote SFTPRemote;

   @EJB
   private NitOnixDAORemote nitOnixDAO;

   @EJB
   private ParameterDAORemote parameterDAO;

   private ArrayList<NitOnixEntity> listNitOnix;

   @Override
   public void processNits() {
      try {
         listNitOnix = new ArrayList<NitOnixEntity>();
         processFile();
         validateNumberRegisters();
         deleteTableNits();
         createRegistersNits();
         updateLastDateLoad();
      } catch (Exception e) {
         LOGGER.error("Ha ocurrido un error al obtener el archivo de Nits", e);

      }

   }

   private void processFile() throws IOException, FileNitEmptyException {

      BufferedReader buffer = new BufferedReader(new FileReader(Constant.PATH_ONIX_IVR));
      try {
         String register = buffer.readLine();
         while (register != null) {
            converToEntity(register);
            register = buffer.readLine();
         }
      } finally {
         buffer.close();
      }

   }

   private void converToEntity(String register) {
      NitOnixEntity nitOnix;
      StringTokenizer token = new StringTokenizer(register, Constant.DELIMETER_SNMPTT);
      try {
         long id = getNextTokenLong(token);
         String codeService = getNextTokenString(token);
         String nit = getNextTokenString(token);
         nitOnix = new NitOnixEntity();
         nitOnix.setId(id);
         nitOnix.setCodeService(codeService);
         nitOnix.setNit(nit);
         listNitOnix.add(nitOnix);
      } catch (Exception e) {
         LOGGER.error("Error Creando Registros de Nit Onix", e);
      }

   }

   private static String getNextTokenString(StringTokenizer token) throws Exception {
      String value = token.nextToken();
      if (value == null)
         throw new Exception();
      return value.trim();
   }

   private static long getNextTokenLong(StringTokenizer token) throws Exception {
      String value = token.nextToken();
      return Long.parseLong(value.trim());
   }

   private void validateNumberRegisters() throws Exception {
      int numberRegistersPrevious = nitOnixDAO.findAllCount();
      if (numberRegistersPrevious == 0) {
         return;
      }
      int numberRegistersNew = listNitOnix.size();
      double difference = Math.abs(numberRegistersPrevious - numberRegistersNew);
      double percentageDifference = (difference / numberRegistersPrevious) * 100;
      if (percentageDifference > Constant.PERCENTAGE_DIFFERENCE_NITS) {
         throw new PercentageDifferenceException("La diferncia porcentual de Numero de Registros de Nits es anormal");
      }

   }

   private void deleteTableNits() throws Exception {
      nitOnixDAO.removeAll();
   }

   private void createRegistersNits() throws Exception {
      nitOnixDAO.createList(listNitOnix);

   }

   private void updateLastDateLoad() {
      try {
         Date today = new Date();
         SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
         String dateString = dateFormat.format(today);
         parameterDAO.updateParameter(Constant.FECHA_ULTIMO_CARGUE_NITS, dateString);
      } catch (Exception e) {
         LOGGER.info("Error al actualizar fecha de ultimo cargue");
      }
   }

}
