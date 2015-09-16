package com.claro.cpymes.ejb;

import java.io.FileOutputStream;
import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.claro.cpymes.dao.AlarmaPymesIVRDAORemote;
import com.claro.cpymes.dto.DataDTO;
import com.claro.cpymes.dto.ReportDTO;
import com.claro.cpymes.ejb.remote.ReportEJBRemote;


@Stateless
@LocalBean
public class ReportEJB implements ReportEJBRemote {

   @EJB
   private AlarmaPymesIVRDAORemote alarmPymesDAO;

   @Override
   public ArrayList<DataDTO> findDataByFilter(ReportDTO reportDTO) throws Exception {
      return alarmPymesDAO.findDataByFilter(reportDTO);
   }

   @Override
   public void generateReport(ArrayList<DataDTO> listData) throws Exception {

      String excelFileName = "/usr/share/tomee/logs/Report.xlsx";

      String sheetName = "Report IVR";

      ArrayList<String> listTitle = getTitles();

      XSSFWorkbook workBook = new XSSFWorkbook();
      XSSFSheet sheet = workBook.createSheet(sheetName);

      int rowNum = sheet.getLastRowNum();
      Row row = sheet.createRow(rowNum++);
      int cellNum = 0;
      for (String title : listTitle) {
         Cell cell = row.createCell(cellNum++);
         cell.setCellValue(title);
      }

      for (DataDTO data : listData) {
         row = sheet.createRow(rowNum++);
         row = getCell(row, data);
      }

      sheet = adjustWidthColumns(sheet);

      FileOutputStream fileReport = new FileOutputStream(excelFileName);
      workBook.write(fileReport);
      workBook.close();
      fileReport.flush();
      fileReport.close();

   }

   private ArrayList<String> getTitles() {
      ArrayList<String> listTitles = new ArrayList<String>();
      listTitles.add("ID_ALARMA_PYMES");
      listTitles.add("TICKET_ONIX");
      listTitles.add("IP");
      listTitles.add("EVENTO");
      listTitles.add("NOMBRE_EQUIPO");
      listTitles.add("TIPO_EVENTO");
      listTitles.add("CIUDAD");
      listTitles.add("DIVISION");
      listTitles.add("REGIONAL");
      listTitles.add("FECHA_INICIO");
      listTitles.add("FECHA_ESPERANZA");
      listTitles.add("FECHA_FIN");
      listTitles.add("TIEMPO_TOTAL_FALLA");
      listTitles.add("FECHA_MODIFICACION");
      listTitles.add("USUARIO_MODIFICACION");
      listTitles.add("ESTADO_ALARMA");
      listTitles.add("CODIGO_SERVICIO");
      listTitles.add("NIT");

      return listTitles;
   }

   private Row getCell(Row row, DataDTO data) {
      int cellNum = 0;
      Cell cell;

      cell = row.createCell(cellNum++);
      cell.setCellValue(data.getIdAlarmaPymes());

      cell = row.createCell(cellNum++);
      cell.setCellValue(data.getTicketOnix());

      cell = row.createCell(cellNum++);
      cell.setCellValue(data.getIp());

      cell = row.createCell(cellNum++);
      cell.setCellValue(data.getClaseEquipo());

      cell = row.createCell(cellNum++);
      cell.setCellValue(data.getDescripcionAlarma());

      cell = row.createCell(cellNum++);
      cell.setCellValue(data.getTipoEvento());

      cell = row.createCell(cellNum++);
      cell.setCellValue(data.getCiudad());

      cell = row.createCell(cellNum++);
      cell.setCellValue(data.getDivision());

      cell = row.createCell(cellNum++);
      cell.setCellValue(data.getRegion());

      cell = row.createCell(cellNum++);
      cell.setCellValue(data.getFechaInicio());

      cell = row.createCell(cellNum++);
      cell.setCellValue(data.getFechaEsperanza());

      cell = row.createCell(cellNum++);
      cell.setCellValue(data.getFechaFin());

      cell = row.createCell(cellNum++);
      cell.setCellValue(data.getTiempoTotalFalla());

      cell = row.createCell(cellNum++);
      cell.setCellValue(data.getFechaModificacion());

      cell = row.createCell(cellNum++);
      cell.setCellValue(data.getUsuarioModificacion());

      cell = row.createCell(cellNum++);
      cell.setCellValue(data.getEstadoAlarma());

      cell = row.createCell(cellNum++);
      cell.setCellValue(data.getCodigoServicio());

      cell = row.createCell(cellNum++);
      cell.setCellValue(data.getNit());

      return row;

   }

   private XSSFSheet adjustWidthColumns(XSSFSheet sheet) {
      short numColumns = sheet.getRow(0).getLastCellNum();
      for (int i = 0; i < numColumns; i++) {
         sheet.autoSizeColumn(i);
      }

      return sheet;
   }
}
