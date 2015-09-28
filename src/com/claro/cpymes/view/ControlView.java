package com.claro.cpymes.view;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.claro.cpymes.dto.ControlReportDTO;
import com.claro.cpymes.ejb.remote.ControlEJBRemote;
import com.claro.cpymes.util.Constant;
import com.claro.cpymes.util.Util;


@ManagedBean(name = "control")
@SessionScoped
public class ControlView {

   private static Logger LOGGER = LogManager.getLogger(ControlView.class.getName());

   @EJB
   public ControlEJBRemote controlEJB;

   private static final int INTERVAL = 900;

   private ControlReportDTO controlReport;

   private int interval;

   private boolean viewPlay;

   private boolean viewPause;

   @PostConstruct
   private void initialize() {
      interval = INTERVAL;
      load();
      play();

   }

   public void load() {
      try {
         controlReport = controlEJB.loadControl();
      } catch (Exception e) {
         Util.addMessageFatal("Ha ocurrido un error al cargar informacion de Control");
         LOGGER.error("Ha ocurrido un error al cargar informacion de Control", e);
      }
   }

   public void goIVR() {
      Util.redirectURL(Constant.URL_IVR);
   }

   /**
    * Pausa la ejecucion periodica de refresh
    */
   public void pause() {
      setViewPlay(true);
      setViewPause(false);
      interval = INTERVAL * 1000;
   }

   /**
    * Reanuda la ejecucion periodica de refresh
    */
   public void play() {
      setViewPlay(false);
      setViewPause(true);
      interval = 60;
   }

   public ControlReportDTO getControlReport() {
      return controlReport;
   }

   public void setControlReport(ControlReportDTO controlReport) {
      this.controlReport = controlReport;
   }

   public int getInterval() {
      return interval;
   }

   public void setInterval(int interval) {
      this.interval = interval;
   }

   public boolean isViewPlay() {
      return viewPlay;
   }

   public void setViewPlay(boolean viewPlay) {
      this.viewPlay = viewPlay;
   }

   public boolean isViewPause() {
      return viewPause;
   }

   public void setViewPause(boolean viewPause) {
      this.viewPause = viewPause;
   }

}
