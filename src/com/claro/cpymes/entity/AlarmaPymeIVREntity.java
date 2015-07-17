package com.claro.cpymes.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the ALARMA_PYMES database table.
 * 
 */
@Entity
@Table(name = "ALARMA_PYMES")
@NamedQueries({
   @NamedQuery(name = "AlarmaPymeIVREntity.findAll", query = "SELECT a FROM AlarmaPymeIVREntity a"),
   @NamedQuery(name = "AlarmaPymeIVREntity.findByEstado", query = "SELECT a FROM AlarmaPymeIVREntity a WHERE a.estadoAlarma = :estado ORDER BY a.fechaInicio DESC"),
   @NamedQuery(name = "AlarmaPymeIVREntity.findSimiliar", query = "SELECT a FROM AlarmaPymeIVREntity a WHERE a.estadoAlarma=:estado AND a.claseEquipo=:eventName and a.ip=:ip"), })
public class AlarmaPymeIVREntity implements Serializable {
   private static final long serialVersionUID = 1L;

   @Id
   @SequenceGenerator(name = "ALARMA_PYMES_IDALARMAPYMES_GENERATOR", sequenceName = "ALARMA_PYMES_SEQ")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ALARMA_PYMES_IDALARMAPYMES_GENERATOR")
   @Column(name = "ID_ALARMA_PYMES")
   private long idAlarmaPymes;

   private String ciudad;

   @Column(name = "CLASE_EQUIPO")
   private String claseEquipo;

   @Column(name = "CODIGO_AUDIO_VIR")
   private BigDecimal codigoAudioIvr;

   @Column(name = "DESCRIPCION_ALARMA")
   private String descripcionAlarma;

   private String division;

   @Column(name = "ESTADO_ALARMA")
   private String estadoAlarma;

   @Temporal(TemporalType.TIMESTAMP)
   @Column(name = "FECHA_ESPERANZA")
   private Date fechaEsperanza;

   @Temporal(TemporalType.TIMESTAMP)
   @Column(name = "FECHA_FIN")
   private Date fechaFin;

   @Temporal(TemporalType.TIMESTAMP)
   @Column(name = "FECHA_INICIO")
   private Date fechaInicio;

   @Temporal(TemporalType.TIMESTAMP)
   @Column(name = "FECHA_MODIFICACION")
   private Date fechaModificacion;

   private String ip;

   @Column(name = "TICKET_ONIX")
   private String ticketOnix;

   @Column(name = "TIEMPO_TOTAL_FALLA")
   private BigDecimal tiempoTotalFalla;

   @Column(name = "TIPO_EVENTO")
   private String tipoEvento;

   @Column(name = "USUARIO_MODIFICACION")
   private String usuarioModificacion;

   // bi-directional many-to-one association to AlarmaPymesServicioNitIVREntity
   @OneToMany(mappedBy = "alarmaPyme")
   private List<AlarmaPymesServicioNitIVREntity> alarmaPymesServicioNits;

   public AlarmaPymeIVREntity() {
   }

   public long getIdAlarmaPymes() {
      return this.idAlarmaPymes;
   }

   public void setIdAlarmaPymes(long idAlarmaPymes) {
      this.idAlarmaPymes = idAlarmaPymes;
   }

   public String getCiudad() {
      return this.ciudad;
   }

   public void setCiudad(String ciudad) {
      this.ciudad = ciudad;
   }

   public String getClaseEquipo() {
      return this.claseEquipo;
   }

   public void setClaseEquipo(String claseEquipo) {
      this.claseEquipo = claseEquipo;
   }

   public BigDecimal getCodigoAudioIvr() {
      return this.codigoAudioIvr;
   }

   public void setCodigoAudioIvr(BigDecimal codigoAudioIvr) {
      this.codigoAudioIvr = codigoAudioIvr;
   }

   public String getDescripcionAlarma() {
      return this.descripcionAlarma;
   }

   public void setDescripcionAlarma(String descripcionAlarma) {
      this.descripcionAlarma = descripcionAlarma;
   }

   public String getDivision() {
      return this.division;
   }

   public void setDivision(String division) {
      this.division = division;
   }

   public String getEstadoAlarma() {
      return this.estadoAlarma;
   }

   public void setEstadoAlarma(String estadoAlarma) {
      this.estadoAlarma = estadoAlarma;
   }

   public Date getFechaEsperanza() {
      return this.fechaEsperanza;
   }

   public void setFechaEsperanza(Date fechaEsperanza) {
      this.fechaEsperanza = fechaEsperanza;
   }

   public Date getFechaFin() {
      return this.fechaFin;
   }

   public void setFechaFin(Date fechaFin) {
      this.fechaFin = fechaFin;
   }

   public Date getFechaInicio() {
      return this.fechaInicio;
   }

   public void setFechaInicio(Date fechaInicio) {
      this.fechaInicio = fechaInicio;
   }

   public Date getFechaModificacion() {
      return this.fechaModificacion;
   }

   public void setFechaModificacion(Date fechaModificacion) {
      this.fechaModificacion = fechaModificacion;
   }

   public String getIp() {
      return this.ip;
   }

   public void setIp(String ip) {
      this.ip = ip;
   }

   public String getTicketOnix() {
      return this.ticketOnix;
   }

   public void setTicketOnix(String ticketOnix) {
      this.ticketOnix = ticketOnix;
   }

   public BigDecimal getTiempoTotalFalla() {
      return this.tiempoTotalFalla;
   }

   public void setTiempoTotalFalla(BigDecimal tiempoTotalFalla) {
      this.tiempoTotalFalla = tiempoTotalFalla;
   }

   public String getTipoEvento() {
      return this.tipoEvento;
   }

   public void setTipoEvento(String tipoEvento) {
      this.tipoEvento = tipoEvento;
   }

   public String getUsuarioModificacion() {
      return this.usuarioModificacion;
   }

   public void setUsuarioModificacion(String usuarioModificacion) {
      this.usuarioModificacion = usuarioModificacion;
   }

   public List<AlarmaPymesServicioNitIVREntity> getAlarmaPymesServicioNits() {
      return this.alarmaPymesServicioNits;
   }

   public void setAlarmaPymesServicioNits(List<AlarmaPymesServicioNitIVREntity> alarmaPymesServicioNits) {
      this.alarmaPymesServicioNits = alarmaPymesServicioNits;
   }

   public AlarmaPymesServicioNitIVREntity addAlarmaPymesServicioNit(
      AlarmaPymesServicioNitIVREntity alarmaPymesServicioNit) {
      getAlarmaPymesServicioNits().add(alarmaPymesServicioNit);
      alarmaPymesServicioNit.setAlarmaPyme(this);

      return alarmaPymesServicioNit;
   }

   public AlarmaPymesServicioNitIVREntity removeAlarmaPymesServicioNit(
      AlarmaPymesServicioNitIVREntity alarmaPymesServicioNit) {
      getAlarmaPymesServicioNits().remove(alarmaPymesServicioNit);
      alarmaPymesServicioNit.setAlarmaPyme(null);

      return alarmaPymesServicioNit;
   }

}