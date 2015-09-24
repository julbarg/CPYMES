package com.claro.cpymes.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.claro.cpymes.dto.DataDTO;
import com.claro.cpymes.dto.ReportDTO;
import com.claro.cpymes.ejb.remote.ReportEJBRemote;
import com.claro.cpymes.enums.RegionEnum;
import com.claro.cpymes.enums.StateEnum;
import com.claro.cpymes.enums.TypeEventEnum;
import com.claro.cpymes.util.Constant;
import com.claro.cpymes.util.Message;
import com.claro.cpymes.util.Messages;
import com.claro.cpymes.util.Util;


@ManagedBean(name = "report")
@SessionScoped
public class ReportView {

   private static Logger LOGGER = LogManager.getLogger(ReportView.class.getName());

   @EJB
   private ReportEJBRemote reportEJB;

   private ReportDTO reportDTO;

   private ArrayList<StateEnum> listState;

   private ArrayList<TypeEventEnum> listTypeEvent;

   private ArrayList<RegionEnum> listRegion;

   private ArrayList<DataDTO> listData;

   private boolean showReportButton;

   @PostConstruct
   private void initialize() {
      reportDTO = new ReportDTO();
      listData = new ArrayList<DataDTO>();
      loadListState();
      loadListTypeEvent();
      loadListRegion();
      showReportButton = false;

   }

   public boolean validateLogIn() {
      return Util.validateLogIn();
   }

   private void loadListState() {
      listState = new ArrayList<StateEnum>();
      listState.add(StateEnum.ACTIVO);
      listState.add(StateEnum.INACTIVO);

   }

   private void loadListTypeEvent() {
      listTypeEvent = new ArrayList<TypeEventEnum>();
      listTypeEvent.add(TypeEventEnum.EQUIPO);
      listTypeEvent.add(TypeEventEnum.TRONCAL);

   }

   private void loadListRegion() {
      listRegion = new ArrayList<RegionEnum>();
      listRegion.add(RegionEnum.CENTRO);
      listRegion.add(RegionEnum.NORTE);
      listRegion.add(RegionEnum.OCCIDENTE);
   }

   public void generate() {
      showReportButton = false;
      listData = new ArrayList<DataDTO>();
      if (validateLogIn()) {
         if (errorDates()) {
            return;
         }
         if (findData()) {
            generateReport();
            validateSplitData();
         }
      }

   }

   private boolean errorDates() {
      boolean errorFechaInicio = (reportDTO.getFechaInicioDesde() != null && reportDTO.getFechaInicioHasta() == null)
         || (reportDTO.getFechaInicioDesde() == null && reportDTO.getFechaInicioHasta() != null);
      boolean errorFechaFin = (reportDTO.getFechaFinDesde() != null && reportDTO.getFechaFinHasta() == null)
         || (reportDTO.getFechaFinDesde() == null && reportDTO.getFechaFinHasta() != null);
      boolean errorFechaEsperanza = (reportDTO.getFechaEsperanzaDesde() != null && reportDTO.getFechaEsperanzaHasta() == null)
         || (reportDTO.getFechaEsperanzaDesde() == null && reportDTO.getFechaEsperanzaHasta() != null);
      boolean errorFechaModificacion = (reportDTO.getFechaModificacionDesde() != null && reportDTO.getFechaModificacionHasta() == null)
         || (reportDTO.getFechaModificacionDesde() == null && reportDTO.getFechaModificacionHasta() != null);

      if (errorFechaInicio) {
         Util.addMessageFatal(Message.VALIDATE_FECHA_INICIO);
      }
      if (errorFechaFin) {
         Util.addMessageFatal(Message.VALIDATE_FECHA_FIN);
      }
      if (errorFechaEsperanza) {
         Util.addMessageFatal(Message.VALIDATE_FECHA_ESPERANZA);
      }
      if (errorFechaModificacion) {
         Util.addMessageFatal(Message.VALIDATE_FECHA_MODIFICACION);
      }

      return errorFechaInicio && errorFechaFin && errorFechaEsperanza && errorFechaModificacion;

   }

   private boolean findData() {
      try {
         if (!validateSizeData()) {
            Util.addMessageFatal(Messages.VALIDATE_SIZE_DATA);
            return false;
         }
         listData = reportEJB.findDataByFilter(reportDTO);
         showReportButton = listData.size() > 0;
         if (!showReportButton) {
            Util.addMessageInfo("No se han encontrado registros con los Filtros aplicados");
         }
         LOGGER.info("DATA: " + listData.size());
         return showReportButton;
      } catch (Exception e) {
         Util.addMessageFatal(Message.FIND_DATA_ERROR);
         LOGGER.error(Message.FIND_DATA_ERROR, e);
         return false;
      }

   }

   private void getListDataDB() {
      try {
         listData = reportEJB.findDataByFilter(reportDTO);
      } catch (Exception e) {
         LOGGER.error(Message.FIND_DATA_ERROR, e);
      }
   }

   private boolean validateSizeData() {
      try {
         int dataSize = reportEJB.validateSizeData(reportDTO);
         if (dataSize > Constant.MAX_REGISTERS_REPORT) {
            return false;
         }
         return true;
      } catch (Exception e) {
         LOGGER.error(Messages.VALIDATE_SIZE_DATA_ERROR, e);
         return false;
      }

   }

   private void generateReport() {
      try {
         reportEJB.generateReport(listData);
      } catch (Exception e) {
         Util.addMessageFatal(Message.GENERATE_REPORT_ERROR);
         LOGGER.error(Message.GENERATE_REPORT_ERROR, e);
      }
   }

   public StreamedContent downloadFile() {
      try {

         String extension = FilenameUtils.getExtension(Constant.URL_REPORT);
         InputStream stream = new FileInputStream(Constant.URL_REPORT.trim());
         return new DefaultStreamedContent(stream, extension, "Report" + "." + extension);
      } catch (FileNotFoundException e) {
         LOGGER.error(Messages.DOWNLOAD_FILE_ERROR, e);
      }
      return null;
   }

   private void validateSplitData() {
      int sizeData = listData.size();
      if (sizeData > 100) {
         Util.addMessageInfo("Se estan mostrando 100/" + sizeData + " registros.");
         listData = new ArrayList<DataDTO>(listData.subList(0, 100));
      } else {
         getListDataDB();
         Util.addMessageInfo("Se estan mostrando " + sizeData + "/" + sizeData + " registros.");
      }
   }

   public void goIVR() {
      Util.redirectURL(Constant.URL_IVR);
   }

   public String getNameState(String state) {
      return StateEnum.getName(state);
   }

   public ReportDTO getReportDTO() {
      return reportDTO;
   }

   public void setReportDTO(ReportDTO reportDTO) {
      this.reportDTO = reportDTO;
   }

   public ArrayList<StateEnum> getListState() {
      return listState;
   }

   public void setListState(ArrayList<StateEnum> listState) {
      this.listState = listState;
   }

   public ArrayList<TypeEventEnum> getListTypeEvent() {
      return listTypeEvent;
   }

   public void setListTypeEvent(ArrayList<TypeEventEnum> listTypeEvent) {
      this.listTypeEvent = listTypeEvent;
   }

   public ArrayList<DataDTO> getListData() {
      return listData;
   }

   public void setListData(ArrayList<DataDTO> listData) {
      this.listData = listData;
   }

   public boolean isShowReportButton() {
      return showReportButton;
   }

   public void setShowReportButton(boolean showReportButton) {
      this.showReportButton = showReportButton;
   }

   public ArrayList<RegionEnum> getListRegion() {
      return listRegion;
   }

   public void setListRegion(ArrayList<RegionEnum> listRegion) {
      this.listRegion = listRegion;
   }

}
