package com.claro.cpymes.ejb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.claro.cpymes.dao.NitOnixDAORemote;
import com.claro.cpymes.ejb.remote.ProcessNitsRemote;
import com.claro.cpymes.ejb.remote.SFTPRemote;
import com.claro.cpymes.entity.NitOnixEntity;
import com.claro.cpymes.util.Constant;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;


@Stateless
@LocalBean
public class ProcessNitsEJB implements ProcessNitsRemote {

   private static Logger LOGGER = LogManager.getLogger(ProcessNitsEJB.class.getName());

   @EJB
   private SFTPRemote SFTPRemote;

   @EJB
   private NitOnixDAORemote nitOnixDAO;

   private ArrayList<NitOnixEntity> listNitOnix;

   private static final int A = CellReference.convertColStringToIndex("A");

   private static final int B = CellReference.convertColStringToIndex("B");

   private static final int C = CellReference.convertColStringToIndex("C");

   private static final int D = CellReference.convertColStringToIndex("D");

   private static final int E = CellReference.convertColStringToIndex("E");

   @Override
   public void processNits() {
      try {
         listNitOnix = new ArrayList<NitOnixEntity>();
         getFileNitsCopyServe();
         processFile();
         deleteTableNits();
         createRegistersNits();
      } catch (Exception e) {
         LOGGER.error("Ha ocurrido un error al obtener el archivo de Nits", e);

      }

   }

   private void getFileNitsCopyServe() throws FileNotFoundException, JSchException, SftpException {
      SFTPRemote.download();
   }

   @SuppressWarnings("resource")
   private void processFile() throws IOException {
      File pathFile = new File(getPathFile());
      FileInputStream file = new FileInputStream(pathFile);
      XSSFWorkbook myWorkBook = new XSSFWorkbook(file);
      XSSFSheet mySheet = myWorkBook.getSheetAt(0);

      int i = 0;
      for (Row row : mySheet) {
         if (i++ > 0) {
            NitOnixEntity nitOnix = getInfoFromRow(row);
            if (nitOnix != null) {
               listNitOnix.add(nitOnix);
            }
         }
      }

   }

   private NitOnixEntity getInfoFromRow(Row row) {
      NitOnixEntity nitOnix = new NitOnixEntity();
      try {
         Cell cell = row.getCell(A);
         nitOnix.setId((long) cell.getNumericCellValue());

         cell = row.getCell(B);
         nitOnix.setIdEnlace(cell.getStringCellValue());

         cell = row.getCell(C);
         nitOnix.setNit((long) cell.getNumericCellValue());

         cell = row.getCell(D);
         nitOnix.setIdCliente((long) cell.getNumericCellValue());

         cell = row.getCell(E);
         nitOnix.setEstadoServicio(cell.getStringCellValue());
         return nitOnix;
      } catch (Exception e) {
         return null;
      }

   }

   private String getPathFile() {
      return Constant.SERVER_WORKING_DIR + Constant.NAME_FILE_NITS;
   }

   private void deleteTableNits() throws Exception {
      nitOnixDAO.removeAll();
   }

   private void createRegistersNits() throws Exception {
      nitOnixDAO.createList(listNitOnix);

   }

}
