package com.claro.cpymes.ejb;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import CMBD.EquipoCMBD;
import CMBD.PrincipalCMBD;

import com.claro.cpymes.dao.AlarmCatalogDAORemote;
import com.claro.cpymes.dao.AlarmPymesDAORemote;
import com.claro.cpymes.dao.AlarmaPymesIVRDAORemote;
import com.claro.cpymes.dao.AlarmaPymesServicioNitIVRDAORemote;
import com.claro.cpymes.dao.LogsDAORemote;
import com.claro.cpymes.dao.NitOnixDAORemote;
import com.claro.cpymes.dto.KeyCatalogDTO;
import com.claro.cpymes.dto.LogDTO;
import com.claro.cpymes.dto.RestoreEventAlarmDTO;
import com.claro.cpymes.ejb.remote.ProcessEJBRemote;
import com.claro.cpymes.entity.AlarmCatalogEntity;
import com.claro.cpymes.entity.AlarmPymesEntity;
import com.claro.cpymes.entity.AlarmaPymeIVREntity;
import com.claro.cpymes.entity.AlarmaPymesServicioNitIVREntity;
import com.claro.cpymes.entity.LogEntity;
import com.claro.cpymes.entity.NitOnixEntity;
import com.claro.cpymes.enums.FilterCatalogEnum;
import com.claro.cpymes.enums.ProcessEnum;
import com.claro.cpymes.enums.StateEnum;
import com.claro.cpymes.enums.TypeEventEnum;
import com.claro.cpymes.rule.Correlacion;
import com.claro.cpymes.rule.Filtrado;
import com.claro.cpymes.rule.RestoreEvent;
import com.claro.cpymes.util.Constant;
import com.claro.cpymes.util.LogUtil;
import com.claro.cpymes.util.Util;


/**
 * Bean que ejecuta el proceso de ejecucion de reglas
 * de filtrado y correlacion
 * @author jbarragan
 *
 */
@Stateless
@LocalBean
public class ProcessEJB implements ProcessEJBRemote {

   private static Logger LOGGER = LogManager.getLogger(ProcessEJB.class.getName());

   @EJB
   private LogsDAORemote logsDAORemote;

   @EJB
   private AlarmPymesDAORemote alarmPymesDAORemote;

   @EJB
   private AlarmCatalogDAORemote alarmCatalogDAO;

   @EJB
   private NitOnixDAORemote nitOnixDAO;

   @EJB
   private AlarmaPymesIVRDAORemote alarmaPymesIVRDAO;

   @EJB
   private AlarmaPymesServicioNitIVRDAORemote alarmaPymesServicioNitIVRDAO;

   private HashMap<KeyCatalogDTO, AlarmCatalogEntity> catalog;

   private HashMap<String, Long> nitOnixs;

   private Filtrado filtrado;

   private Correlacion correlacion;

   private RestoreEvent restoreEvent;

   private ArrayList<LogEntity> listAlarms;

   private ArrayList<LogDTO> listLog;

   @PostConstruct
   private void initialize() {

      try {
         createCatalog();
         filtrado = new Filtrado();
         filtrado.initialize(catalog);
         correlacion = new Correlacion();
         correlacion.initialize();
         restoreEvent = new RestoreEvent();
         restoreEvent.initialize();

      } catch (Exception e) {
         LOGGER.error("Error iniciando Rules: ", e);
      }
   }

   private void createCatalog() throws Exception {
      KeyCatalogDTO key;
      catalog = new HashMap<KeyCatalogDTO, AlarmCatalogEntity>();
      ArrayList<AlarmCatalogEntity> listAlarmCatalog = alarmCatalogDAO.findByFilter(FilterCatalogEnum.PYMES.getValue());
      for (AlarmCatalogEntity alarmCatalog : listAlarmCatalog) {
         if ((key = getKey(alarmCatalog)) != null) {
            catalog.put(key, alarmCatalog);
         }
      }
   }

   private boolean validateInfoNit(String codeService, Long nit) {
      return (codeService != null && nit != null && !codeService.isEmpty());

   }

   private KeyCatalogDTO getKey(AlarmCatalogEntity alarmCatalog) {
      String OID = alarmCatalog.getOid().trim();
      String nameEvent = alarmCatalog.getTextAlarm().trim();
      String criticality = alarmCatalog.getCriticality().trim();
      if (OID != null && nameEvent != null && criticality != null) {
         return new KeyCatalogDTO(OID, criticality);
      }
      return null;

   }

   /**
    * Metodo principal que llama paso a paso el flujo del proceso
    * de filtrado y correlacion
    */
   public void procesar() {
      try {
         LOGGER.info("INICIO PROCESO PRINCIPAL");
         loadNitOnix();
         getListAlarmsEntity();
         mapearListLogDTO();
         filtrar();
         saveAlarmFilter();
         cleanMemory();
         correlate();
         saveOrUpdateCEP();
         sendIVR();
         restoreEvent();
         LOGGER.info("FIN");
         LOGGER.info("-----------------------------------------------");
      } catch (Exception e) {
         LOGGER.error("Error Procesando Alarmas", e);
      }

   }

   private void loadNitOnix() throws Exception {
      ArrayList<NitOnixEntity> listNitOnix = nitOnixDAO.findByEstado(Constant.ACTIVADO);
      nitOnixs = new HashMap<String, Long>();
      for (NitOnixEntity nitOnix : listNitOnix) {
         String codeService = nitOnix.getIdEnlace();
         Long nit = nitOnix.getNit();
         if (validateInfoNit(codeService, nit)) {
            nitOnixs.put(codeService, nit);
         }

      }
   }

   /**
    * Obtiene los logs con estado NO PROCESADO de la Base de Datos
    * KOU
    * @return ArrayList<LogEntity> Lista de logs obtenidas
    */
   private void getListAlarmsEntity() {
      listAlarms = new ArrayList<LogEntity>();
      try {
         listAlarms = logsDAORemote.findByEstado(ProcessEnum.NO_PROCESADO.getValue());
         LOGGER.info("KOU - Alarmas Encontradas: " + listAlarms.size());
      } catch (Exception e) {
         LOGGER.error("Obtenido Registro de Logs: ", e);
      }
   }

   /**
    * Mapea los registros obtenidos en la base de datos KOU,
    * Atravez de string obtiene la informacion para ser mapaeada a
    * objetos java. IP, OID, EventName, NameDevice, Priority, Interface, Nodo
    * Description a el objeto LogDTO
    * @param listAlarms Lista de logs a procesar
    * @return ArrayList<LogEntity> Mapeados
    */
   private void mapearListLogDTO() {
      int mapeadas = 0;
      listLog = new ArrayList<LogDTO>();
      String procesados = ProcessEnum.PROCESADO.getValue();
      for (LogEntity logEntity : listAlarms) {
         LogDTO logDTO = LogUtil.mapearLog(logEntity);
         listLog.add(logDTO);
         logEntity.setProcesados(procesados);
         if (logDTO.isMapeado()) {
            mapeadas++;
         }
      }
      logsDAORemote.updateList(listAlarms);
      LOGGER.info("MAPEADAS - Alarmas Mapeadas: " + mapeadas);
   }

   /**
    * Hace el llamado a la implementacion Drools para las reglas
    * de filtrado, ejecutando uno a uno
    * Son analizadas regla por regla y cambiando el estado en el 
    * campo relevant y messageDrl del objeto LogDTO
    * @param listLog Lista de logs a ejecutar filtros
    * @return ArrayList<LogDTO> con la informacion modificada resultado del filtrado
    */
   private void filtrar() {
      try {
         for (LogDTO log : listLog) {
            if (log.isMapeado()) {
               log = filtrado.filtrar(log);
            }
         }
      } catch (Exception e) {
         LOGGER.error("Error Filtrando", e);
      }

   }

   private void saveAlarmFilter() {
      listLog = alarmPymesDAORemote.createList(listLog);
   }

   /**
    * Hace el llamado a la implemtacion Drools para las reglas
    * de correlacion. Solo se ejecutan las que han sido marcadas 
    * como correlacionable y relevante en el proceso
    * de filtrado
    * @param listLogDTOs Lista de logs a ejecutar
    */
   private void correlate() {
      for (LogDTO logCEP : listLog) {
         if (logCEP.isCorrelation() && logCEP.isRelevant()) {
            // Si fueron marcados con correlacion y son relevantes
            // se almacenan en MemoryEntryPoint para tener en cuenta
            // en la proxima ejecucion de correlacion
            logCEP.setContCorrelate(1);
            correlacion.insertToEntryPoint(logCEP);
         }
      }

   }

   /**
    * Limpia las alarmas que han sido marcadas como reconocidas por el usuario.
    * Se extraen del WorkingMemoryEntryPoint
    */
   private void cleanMemory() {
      Date endDate = new Date();
      Date startDate = Util.restarFecha(endDate, Constant.TIME_RECOGNIZE_CORRELATION);
      ArrayList<AlarmPymesEntity> listAlarmsReconocidas = alarmPymesDAORemote.findSimiliarCEPReconocidas(startDate,
         endDate);
      for (AlarmPymesEntity alarmEntity : listAlarmsReconocidas) {
         correlacion.retract(alarmEntity.getNodo(), alarmEntity.getNameCorrelation());
      }

   }

   /**
    * Una vez ejecutadas las reglas de filtrado son procesadas las
    * reglas correlacionadas que tengan mayor o igual de numero de alarmas correlacionadas
    * configuradas en el sistema
    * @param listLog
    */
   @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
   private void saveOrUpdateCEP() {
      ArrayList<LogDTO> listLogDTOs = correlacion.getListLogsCorrelation();
      int news = 0;
      int update = 0;
      for (LogDTO logDTO : listLogDTOs) {
         if (isVerificable(logDTO)) {
            try {
               ArrayList<AlarmPymesEntity> listAlarmsSimilar = findAlarmCPE(logDTO);
               if (listAlarmsSimilar.size() > 0) {
                  updateAlarmCEP(logDTO, listAlarmsSimilar.get(0));
                  update++;
               } else {
                  saveAlarmCEP(logDTO);
                  news++;
               }
            } catch (Exception e) {
               LOGGER.error("Error guardando Alarma", e);
            }
         }
      }
      LOGGER.info("CORRELACION - Alarmas Procesadas: " + listLogDTOs.size());
      LOGGER.info("CORRELACION - Alarmas Correlacionadas Nuevas: " + news);
      LOGGER.info("CORRELACION - Alarmas Correlacionadas Actualizadas: " + update);
   }

   /**
    * Validacion para poder guardar una alarma correlacionada
    * La alarma a guardar debe tener mayor o igual numero de alarmas
    * configuradas en el sistema
    * @param logDTO Contiene los datos a validar
    * @return Resultado de la verificacion
    */
   private boolean isVerificable(LogDTO logDTO) {
      return logDTO.getContCorrelate() >= Constant.NUMBER_ALARMS_CORRELATE && logDTO.getDate() != null;
   }

   /**
    * Busca alarmas por nodo, nameCorrelation y date
    * @param logDTO Log que contiene la informacion para realizar busqueda
    * @return ArrayList<AlarmPymesEntity> de alarmas encontradas
    */
   private ArrayList<AlarmPymesEntity> findAlarmCPE(LogDTO logDTO) {
      Date date = logDTO.getDate();
      String nodo = logDTO.getNodo();
      String nameCorrelation = logDTO.getNameCorrelation();
      ArrayList<AlarmPymesEntity> listAlarmsSimilar = alarmPymesDAORemote.findSimiliarCEP(nodo, nameCorrelation, date);
      return listAlarmsSimilar;
   }

   /**
    * Actualiza la alarma correlacionada
    * @param logDTO Log para ampliar informacion
    * @param alarmEntity Alarma a actualizar
    */
   private void updateAlarmCEP(LogDTO logDTO, AlarmPymesEntity alarmEntity) {
      alarmEntity.setMessage(logDTO.getMessageDRL());
      alarmEntity.setEstado(StateEnum.ACTIVO.getValue());

      alarmPymesDAORemote.update(alarmEntity);

   }

   /**
    * Crea una alarma correlacionada
    * @param logDTO Log que contiene informacion
    */
   private void saveAlarmCEP(LogDTO logDTO) {
      AlarmPymesEntity alarmEntity = new AlarmPymesEntity();
      alarmEntity.setIp(logDTO.getIp());
      alarmEntity.setOid(logDTO.getOID());
      alarmEntity.setName(logDTO.getName());
      alarmEntity.setEventName(logDTO.getNameEvent());
      alarmEntity.setPriority(logDTO.getPriority());
      alarmEntity.setMessage(logDTO.getMessageDRL());
      alarmEntity.setNameCorrelation(logDTO.getNameCorrelation());
      alarmEntity.setEstado(StateEnum.ACTIVO.getValue());
      alarmEntity.setNodo(logDTO.getNodo());
      alarmEntity.setDate(logDTO.getDate());

      alarmPymesDAORemote.create(alarmEntity);
   }

   private void sendIVR() {
      int numberRegistersIVR = 0;
      try {
         for (LogDTO log : listLog) {
            if (log.isSendIVR()) {
               EquipoCMBD equipo = getEquipo(log.getIp(), log.getInterFace(), log.getName());
               AlarmaPymeIVREntity alarmaIVR = new AlarmaPymeIVREntity();

               alarmaIVR.setClaseEquipo(equipo.getClaseEquipo());
               // alarmaIVR.setDescripcionAlarma(equipo.getDescripcion());
               alarmaIVR.setDescripcionAlarma(log.getNameEvent());
               alarmaIVR.setCiudad(equipo.getCiudad());
               alarmaIVR.setDivision(equipo.getDivision());

               alarmaIVR.setEstadoAlarma(StateEnum.ACTIVO.getValue());

               alarmaIVR.setCodigoAudioIvr(getCodigoAudioIvr());

               Date today = new Date();
               alarmaIVR.setFechaInicio(today);

               // TODO
               alarmaIVR.setFechaEsperanza(getFechaEsperanza(equipo, today));

               alarmaIVR.setIp(log.getIp());

               // TODO
               TypeEventEnum.TRONCAL.getValue();
               TypeEventEnum.PUERTO.getValue();
               TypeEventEnum.EQUIPO.getValue();
               alarmaIVR.setTipoEvento(TypeEventEnum.EQUIPO.getValue());

               alarmaIVR = alarmaPymesIVRDAO.update(alarmaIVR);

               saveAlarmaPymesServicioNits(alarmaIVR, equipo);
               numberRegistersIVR++;
            }
         }
         LOGGER.info("IVR - Alarmas enviadas al IVR: " + numberRegistersIVR);
      } catch (Exception e) {
         LOGGER.error("Error Send IVR", e);
      }

   }

   private EquipoCMBD getEquipo(String IP, String interFace, String name) {
      return PrincipalCMBD.getNitsFromCRM(IP, interFace, name);
   }

   private BigDecimal getCodigoAudioIvr() {
      return Constant.CODIGO_AUDIO_IVR;
   }

   // TODO
   private Date getFechaEsperanza(EquipoCMBD equipo, Date today) {
      int numberHours = 20;
      return Util.addHoursToDate(today, numberHours);
   }

   private void saveAlarmaPymesServicioNits(AlarmaPymeIVREntity alarmaIVR, EquipoCMBD equipo) {
      for (String codigoServicio : equipo.getCodigosServicio()) {
         AlarmaPymesServicioNitIVREntity alarmaServicioNitIVR = new AlarmaPymesServicioNitIVREntity();
         Long nit = getNit(codigoServicio);
         if (nit != null) {
            alarmaServicioNitIVR.setNit(nit.toString());
            alarmaServicioNitIVR.setCodigoServicio(codigoServicio);
            alarmaServicioNitIVR.setAlarmaPyme(alarmaIVR);

            alarmaPymesServicioNitIVRDAO.update(alarmaServicioNitIVR);
         }

      }
   }

   private Long getNit(String codigoServicio) {
      return nitOnixs.get(codigoServicio);
   }

   private void restoreEvent() {
      int numberRegistersClear = 0;
      RestoreEventAlarmDTO restoreAlarmEvent;
      for (LogDTO log : listLog) {
         if (log.isRelevant()) {
            restoreAlarmEvent = restoreEvent.restoreEvent(log);
            if (restoreAlarmEvent != null) {
               try {
                  int result = alarmPymesDAORemote.clearAlarm(restoreAlarmEvent.getEventTrigger(),
                     restoreAlarmEvent.getIp(), restoreAlarmEvent.getDate());
                  LOGGER.info(restoreAlarmEvent.getIp() + " - " + log.getName() + " - "
                     + restoreAlarmEvent.getEvenRestore() + " - " + restoreAlarmEvent.getEventTrigger()[0] + " - "
                     + result);
                  numberRegistersClear = numberRegistersClear + result;

               } catch (Exception e) {
                  LOGGER.error("Error actualizando registros de Alarmas [Restore]", e);
               }

            }
         }

      }
      LOGGER.info("RESTORE EVENT - Alarmas Restauradas: " + numberRegistersClear);
   }

}
