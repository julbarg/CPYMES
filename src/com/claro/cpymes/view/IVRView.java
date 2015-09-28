package com.claro.cpymes.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.claro.cpymes.dto.AlarmaPymeIVRDTO;
import com.claro.cpymes.ejb.remote.IVREJBRemote;
import com.claro.cpymes.entity.AlarmaPymesServicioNitIVREntity;
import com.claro.cpymes.enums.TypeEventEnum;
import com.claro.cpymes.exceptions.SessionException;
import com.claro.cpymes.util.Constant;
import com.claro.cpymes.util.Util;


/**
 * Controla la vista cpymes.xhtml
 * @author jbarragan
 *
 */
@ManagedBean(name = "ivr")
@SessionScoped
public class IVRView implements Serializable {

   /**
    * 
    */
   private static final long serialVersionUID = 4280299934664307887L;

   private static Logger LOGGER = LogManager.getLogger(IVRView.class.getName());

   private boolean viewPause;

   private boolean viewPlay;

   @EJB
   private IVREJBRemote IVREJB;

   private ArrayList<AlarmaPymeIVRDTO> listAlarmaPymesIVR;

   private ArrayList<AlarmaPymesServicioNitIVREntity> listCodigosServicio;

   private AlarmaPymeIVRDTO alarmFilter;

   private AlarmaPymeIVRDTO alarmEdit;

   private ArrayList<TypeEventEnum> listTypeEvent;

   private int interval;

   @PostConstruct
   public void initial() {
      interval = 30;
      alarmFilter = new AlarmaPymeIVRDTO();
      listTypeEvent = new ArrayList<TypeEventEnum>();
      listTypeEvent.add(TypeEventEnum.NODO);
      listTypeEvent.add(TypeEventEnum.FIBRA);
      load();
      play();
   }

   public void load() {
      if (!Util.validateLogIn()) {
         return;
      }
      try {
         validateDateLoadNits();
         initializeAttributes();
         listAlarmaPymesIVR = IVREJB.findByFilter(alarmFilter);
      } catch (Exception e) {
         LOGGER.error("Ha ocurrido un error al cargar alarmas del IVR", e);
      }
   }

   private void validateDateLoadNits() {
      Date dateLoadNits = IVREJB.getDateLoadNits();
      Date today = new Date();
      long hours;
      if (dateLoadNits != null) {
         hours = Util.getHoursBetweenTwoDates(dateLoadNits, today);
         if (hours > Constant.TIMER_LOAD_NITS) {
            Util.addMessageFatal("Se esta presentando un error al realizar el cargue de Codigos de Servicio vs Nits. " + hours);
            LOGGER.info("Se esta presentando un error al realizar el cargue de Codigos de Servicio vs Nits.");
         }
      }

   }

   public void initializeAttributes() {
      listCodigosServicio = new ArrayList<AlarmaPymesServicioNitIVREntity>();
      listAlarmaPymesIVR = new ArrayList<AlarmaPymeIVRDTO>();
   }

   public void view() {
      try {
         listCodigosServicio = IVREJB.findCodigosServicio(alarmEdit);

      } catch (Exception e) {
         LOGGER.error("Ha ocurrido un error al cargar codigos de servicios del IVR", e);
      }
   }

   public void edit() {
      try {
         alarmEdit.setFechaModificacion(new Date());
         alarmEdit.setUsuarioModificacion(Util.getUserName());
         IVREJB.edit(alarmEdit);

         Util.addMessageInfo("Alarma modificada exitosamente");
      } catch (SessionException e1) {
         LOGGER.error("Ha ocurrido un error al modificar alarmas del IVR", e1);
         Util.addMessageFatal("No se ha iniciado Sesion");
      } catch (Exception e) {
         LOGGER.error("Ha ocurrido un error al modificar alarmas del IVR", e);
         Util.addMessageFatal("Ha ocurrido un error al modificar Alarma");
      }
   }

   public void find() {
      LOGGER.info("find View");
      try {
         listAlarmaPymesIVR = IVREJB.findByFilter(alarmFilter);
      } catch (Exception e) {
         LOGGER.error("Ha ocurrido un error al consultar alarmas del IVR", e);
      }
   }

   public void clean() {

      try {
         alarmFilter = new AlarmaPymeIVRDTO();
         listAlarmaPymesIVR = IVREJB.findAllAlarmIVR();
      } catch (Exception e) {
         LOGGER.error("Ha ocurrido un error al cargar alarmas del IVR", e);
      }
   }

   /**
    * Pausa la ejecucion periodica de refresh
    */
   public void pause() {
      viewPlay = true;
      viewPause = false;
   }

   /**
    * Reanuda la ejecucion periodica de refresh
    */
   public void play() {
      viewPlay = false;
      viewPause = true;
   }

   public void goCpymes() {
      Util.redirectURL(Constant.URL_CPYMES);
   }

   public void goControl() {
      if (Util.validateLogIn()) {
         Util.redirectURL(Constant.URL_CONTROL);
      }
   }

   public void goReport() {
      if (Util.validateLogIn()) {
         Util.redirectURL(Constant.URL_REPORT_PAGE);
      }
   }

   public ArrayList<AlarmaPymeIVRDTO> getListAlarmaPymesIVR() {
      return listAlarmaPymesIVR;
   }

   public void setListAlarmaPymesIVR(ArrayList<AlarmaPymeIVRDTO> listAlarmaPymesIVR) {
      this.listAlarmaPymesIVR = listAlarmaPymesIVR;
   }

   public AlarmaPymeIVRDTO getAlarmEdit() {
      return alarmEdit;
   }

   public void setAlarmEdit(AlarmaPymeIVRDTO alarmEdit) {
      this.alarmEdit = alarmEdit;
   }

   public ArrayList<AlarmaPymesServicioNitIVREntity> getListCodigosServicio() {
      return listCodigosServicio;
   }

   public void setListCodigosServicio(ArrayList<AlarmaPymesServicioNitIVREntity> listCodigosServicio) {
      this.listCodigosServicio = listCodigosServicio;
   }

   public AlarmaPymeIVRDTO getAlarmFilter() {
      return alarmFilter;
   }

   public void setAlarmFilter(AlarmaPymeIVRDTO alarmFilter) {
      this.alarmFilter = alarmFilter;
   }

   public boolean isViewPause() {
      return viewPause;
   }

   public void setViewPause(boolean viewPause) {
      this.viewPause = viewPause;
   }

   public boolean isViewPlay() {
      return viewPlay;
   }

   public void setViewPlay(boolean viewPlay) {
      this.viewPlay = viewPlay;
   }

   public ArrayList<TypeEventEnum> getListTypeEvent() {
      return listTypeEvent;
   }

   public void setListTypeEvent(ArrayList<TypeEventEnum> listTypeEvent) {
      this.listTypeEvent = listTypeEvent;
   }

   public int getInterval() {
      return interval;
   }

   public void setInterval(int interval) {
      this.interval = interval;
   }

}
