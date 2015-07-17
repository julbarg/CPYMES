package com.claro.cpymes.ejb;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.xml.rpc.ServiceException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import CMBD.EquipoCMBD;
import claro.com.co.IvrcmdbLocator;
import claro.com.co.ServicesDevicesDTO;

import com.claro.cpymes.dao.AlarmCatalogDAORemote;
import com.claro.cpymes.dao.AlarmPymesDAORemote;
import com.claro.cpymes.dao.AlarmaPymesIVRDAORemote;
import com.claro.cpymes.dao.AlarmaPymesServicioNitIVRDAORemote;
import com.claro.cpymes.dao.Logs2DAORemote;
import com.claro.cpymes.dao.LogsDAORemote;
import com.claro.cpymes.dao.NitOnixDAORemote;
import com.claro.cpymes.dao.ParameterDAORemote;
import com.claro.cpymes.dto.KeyCatalogDTO;
import com.claro.cpymes.dto.LogDTO;
import com.claro.cpymes.dto.RestoreEventAlarmDTO;
import com.claro.cpymes.ejb.remote.ProcessEJBRemote;
import com.claro.cpymes.entity.AlarmCatalogEntity;
import com.claro.cpymes.entity.AlarmPymesEntity;
import com.claro.cpymes.entity.AlarmaPymeIVREntity;
import com.claro.cpymes.entity.AlarmaPymesServicioNitIVREntity;
import com.claro.cpymes.entity.Log2Entity;
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
import com.claro.cpymes.util.LogUtil2;
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
   private Logs2DAORemote logs2DAORemote;

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

   @EJB
   private ParameterDAORemote parametroDAO;

   private HashMap<KeyCatalogDTO, AlarmCatalogEntity> catalog;

   private HashMap<String, String> nitOnixs;

   private Filtrado filtrado;

   private Correlacion correlacion;

   private RestoreEvent restoreEvent;

   private ArrayList<LogEntity> listAlarms;

   private ArrayList<Log2Entity> listAlarms2;

   private ArrayList<LogDTO> listLog;

   @PostConstruct
   private void initialize() {

      try {
         createCatalog();
         filtrado = new Filtrado();
         filtrado.initialize(catalog);
         correlacion = new Correlacion();
         correlacion.initialize();
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

   private boolean validateInfoNit(String codeService, String nit) {
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
         getListAlarmsEntity2();
         mapearListLogDTO();
         mapearListLogDTO2();
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
      ArrayList<NitOnixEntity> listNitOnix = nitOnixDAO.findAll();
      nitOnixs = new HashMap<String, String>();
      for (NitOnixEntity nitOnix : listNitOnix) {
         String codeService = nitOnix.getCodeService();
         String nit = nitOnix.getNit();
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
         parametroDAO.addCountResgister(Constant.SEND_FROM_KOU, listAlarms.size());
      } catch (Exception e) {
         LOGGER.error("Obtenido Registro de Logs: ", e);
      }
   }

   private void getListAlarmsEntity2() {
      listAlarms2 = new ArrayList<Log2Entity>();
      try {
         listAlarms2 = logs2DAORemote.findNoProcess();
         LOGGER.info("KOU - Alarmas Encontradas Equipos: " + listAlarms2.size());
         parametroDAO.addCountResgister(Constant.SEND_FROM_KOU, listAlarms2.size());
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
    * @throws Exception 
    */
   private void mapearListLogDTO() throws Exception {
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
      LOGGER.info("MAPEADAS - Alarmas Mapeadas: " + mapeadas);
      logsDAORemote.updateList(listAlarms);
      LOGGER.info("UPDATE PROCESADOS - Alarmas Mapeadas");

   }

   private void mapearListLogDTO2() throws Exception {
      int mapeadas = 0;
      String procesados = ProcessEnum.PROCESADO.getValue();
      for (Log2Entity log2Entity : listAlarms2) {
         LogDTO logDTO = LogUtil2.mapearLog(log2Entity);
         listLog.add(logDTO);
         log2Entity.setProcesados(procesados);
         if (logDTO.isMapeado()) {
            mapeadas++;
         }
      }
      LOGGER.info("MAPEADAS - Alarmas Mapeadas Equipo: " + mapeadas);
      logs2DAORemote.updateList(listAlarms2);
      LOGGER.info("UPDATE PROCESADOS - Alarmas Mapeadas Equipo");

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
      listLog = filtrado.filtrar(listLog);
   }

   private void saveAlarmFilter() throws Exception {
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
      correlacion.insertToEntryPoint(listLog);
   }

   /**
    * Limpia las alarmas que han sido marcadas como reconocidas por el usuario.
    * Se extraen del WorkingMemoryEntryPoint
    * @throws Exception 
    */
   private void cleanMemory() throws Exception {
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
                  logDTO.setTypeEvent(TypeEventEnum.MULTIPLE);

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
    * @throws Exception 
    */
   private ArrayList<AlarmPymesEntity> findAlarmCPE(LogDTO logDTO) throws Exception {
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
    * @throws Exception 
    */
   private void updateAlarmCEP(LogDTO logDTO, AlarmPymesEntity alarmEntity) throws Exception {
      alarmEntity.setMessage(logDTO.getMessageDRL());
      alarmEntity.setEstado(StateEnum.ACTIVO.getValue());

      alarmPymesDAORemote.update(alarmEntity);
   }

   /**
    * Crea una alarma correlacionada
    * @param logDTO Log que contiene informacion
    * @throws Exception 
    */
   private void saveAlarmCEP(LogDTO logDTO) throws Exception {
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
      alarmEntity.setSeverity(logDTO.getSeverity());
      alarmEntity.setSendIVR(logDTO.isSendIVR() ? ProcessEnum.SI.getValue() : null);
      alarmEntity.setTypeEvent(logDTO.getTypeEvent().getValue());

      alarmPymesDAORemote.create(alarmEntity);
   }

   private void sendIVR() throws Exception {
      ArrayList<LogDTO> listLogSendIVR = alarmPymesDAORemote.findSendIVR();
      int numberRegistersIVR = 0;
      try {
         for (LogDTO log : listLogSendIVR) {
            if (log.isSendIVR() && log.isRelevant()) {
               if (sendIVR(log)) {
                  numberRegistersIVR++;
               }
            }
         }
         LOGGER.info("IVR - Alarmas enviadas al IVR: " + numberRegistersIVR);
         parametroDAO.addCountResgister(Constant.SEND_TO_IVR, numberRegistersIVR);
         alarmPymesDAORemote.updateAlarmSendIVR(listLogSendIVR);
      } catch (Exception e) {
         LOGGER.error("Error Send IVR", e);
      }

   }

   private boolean sendIVR(LogDTO log) throws Exception {
      EquipoCMBD equipo = getEquipo(log);
      if (equipo.getCiudad() == null)
         return false;

      AlarmaPymeIVREntity alarmaIVR = new AlarmaPymeIVREntity();

      alarmaIVR.setClaseEquipo(log.getNameEvent());
      alarmaIVR.setDescripcionAlarma(equipo.getDescripcion());
      alarmaIVR.setCiudad(equipo.getCiudad());
      alarmaIVR.setDivision(equipo.getDivision());
      alarmaIVR.setEstadoAlarma(StateEnum.ACTIVO.getValue());
      alarmaIVR.setCodigoAudioIvr(getCodigoAudioIvr());

      Date today = new Date();
      alarmaIVR.setFechaInicio(today);

      // TODO
      alarmaIVR.setFechaEsperanza(getFechaEsperanza(equipo, today));
      alarmaIVR.setIp(log.getIp());

      alarmaIVR.setTipoEvento(log.getTypeEvent().getType());

      alarmaIVR = alarmaPymesIVRDAO.updateAlarm(alarmaIVR);

      if (alarmaIVR != null) {
         saveAlarmaPymesServicioNits(alarmaIVR, equipo);
         return true;
      }
      return false;

   }

   public EquipoCMBD getEquipo(LogDTO log) {
      EquipoCMBD equipoCMBD = new EquipoCMBD();
      ;
      ServicesDevicesDTO equipo;

      try {
         ServicesDevicesDTO[] equipos = consultCMBD(log);
         if (equipos != null && equipos.length > 0) {
            equipo = equipos[0];
            equipoCMBD = getCityDescriptionDivision(equipo, equipoCMBD);
            equipoCMBD.setCodigosServicio(getCodesService(equipo));
         }

      } catch (RemoteException | ServiceException e) {
         LOGGER.error("Error consultando Servicios Afectados en CMBD", e);
      }

      return equipoCMBD;

   }

   private ServicesDevicesDTO[] consultCMBD(LogDTO log) throws RemoteException, ServiceException {
      ServicesDevicesDTO[] equipos = null;
      IvrcmdbLocator ivrCmbd = new IvrcmdbLocator();
      String IP = log.getIp();
      String interFace = log.getInterFace();
      String name = log.getName();
      if (log.isCorrelation() && isVerificable(log)) {
         equipos = ivrCmbd.getIvrcmdbWsImplPort().extractServicesIp(IP);
      } else if (interFace != null && interFace.length() > 0 && IP != null && IP.length() > 0) {
         equipos = ivrCmbd.getIvrcmdbWsImplPort().extractServicesPort(IP, interFace, name);
      } else if (IP != null && IP.length() > 0) {
         equipos = ivrCmbd.getIvrcmdbWsImplPort().extractServicesIp(IP);
      }
      return equipos;
   }

   private EquipoCMBD getCityDescriptionDivision(ServicesDevicesDTO equipo, EquipoCMBD equipoCMBD) {
      equipoCMBD.setCiudad(equipo.getCity());
      if (equipo.getDescription() != null) {
         equipoCMBD.setDescripcion(equipo.getDescription());
      } else if (equipo.getDevice() != null) {
         equipoCMBD.setDescripcion(equipo.getDevice());
      }
      equipoCMBD.setDivision(equipo.getSds());

      return equipoCMBD;
   }

   private ArrayList<String> getCodesService(ServicesDevicesDTO equipo) {
      ArrayList<String> codesService = new ArrayList<String>();
      if (equipo.getServiceList() != null && equipo.getServiceList().length > 0) {
         for (String code : equipo.getServiceList()) {
            codesService.add(code);
         }
      } else {
         codesService.add(equipo.getService());
      }

      return codesService;
   }

   private BigDecimal getCodigoAudioIvr() {
      return Constant.CODIGO_AUDIO_IVR;
   }

   // TODO
   private Date getFechaEsperanza(EquipoCMBD equipo, Date today) {
      int numberHours = 20;
      return Util.addHoursToDate(today, numberHours);
   }

   private void saveAlarmaPymesServicioNits(AlarmaPymeIVREntity alarmaIVR, EquipoCMBD equipo) throws Exception {
      for (String codigoServicio : equipo.getCodigosServicio()) {
         AlarmaPymesServicioNitIVREntity alarmaServicioNitIVR = new AlarmaPymesServicioNitIVREntity();
         String nit = getNit(codigoServicio);
         if (nit != null && alarmaIVR.getIdAlarmaPymes() != 0) {
            alarmaServicioNitIVR.setNit(nit.toString());
            alarmaServicioNitIVR.setCodigoServicio(codigoServicio);
            alarmaServicioNitIVR.setAlarmaPyme(alarmaIVR);

            alarmaPymesServicioNitIVRDAO.updateAlarm(alarmaServicioNitIVR);
         }

      }
   }

   private String getNit(String codigoServicio) {
      return nitOnixs.get(codigoServicio);
   }

   private void restoreEvent() {
      try {
         restoreEvent = null;
         restoreEvent = new RestoreEvent();
         restoreEvent.initialize();
      } catch (Exception e) {
         LOGGER.error("ERROR Initialize Restore Rules: ", e);
      }
      ArrayList<RestoreEventAlarmDTO> listRestoreAlarmEvent = new ArrayList<RestoreEventAlarmDTO>();
      listRestoreAlarmEvent = restoreEvent.restoreEvent(listLog);

      LOGGER.info("RESTORE Eventos de Recuperacion: " + listRestoreAlarmEvent.size());
      clearAlarmCPYMES(listRestoreAlarmEvent);
      clearAlarmIVR(listRestoreAlarmEvent);
   }

   private void clearAlarmCPYMES(ArrayList<RestoreEventAlarmDTO> listRestoreCPYMES) {
      try {
         int alarmRestore = alarmPymesDAORemote.clearAlarm(listRestoreCPYMES);
         parametroDAO.addCountResgister(Constant.ALARM_RESTORE_CPYMES, alarmRestore);
      } catch (Exception e) {
         LOGGER.error("Error actualizando registros de Alarmas [Restore CPYMES]", e);
      }
   }

   private void clearAlarmIVR(ArrayList<RestoreEventAlarmDTO> listRestoreIVR) {
      try {
         int alarmRestore = alarmaPymesIVRDAO.clearAlarm(listRestoreIVR);
         parametroDAO.addCountResgister(Constant.ALARM_RESTORE_IVR, alarmRestore);
      } catch (Exception e) {
         LOGGER.error("Error actualizando registros de Alarmas [Restore IVR]", e);
      }

   }

}
