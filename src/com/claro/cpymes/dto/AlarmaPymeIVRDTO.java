package com.claro.cpymes.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.claro.cpymes.entity.AlarmaPymesServicioNitIVREntity;

/**
 * Mapeo de AlarmaPymeIVREntity
 * @author jbarragan
 *
 */
public class AlarmaPymeIVRDTO implements Serializable {

   /**
    * 
    */
   private static final long serialVersionUID = 2014304778973064663L;

   private long idAlarmaPymes;

   private String ciudad;

   private String claseEquipo;

   private BigDecimal codigoAudioIvr;

   private String descripcionAlarma;

   private String division;

   private String estadoAlarma;

   private Date fechaEsperanza;

   private Date fechaFin;

   private Date fechaInicio;

   private Date fechaModificacion;

   private String ip;

   private String ticketOnix;

   private BigDecimal tiempoTotalFalla;

   private String tipoEvento;

   private String usuarioModificacion;

   private List<AlarmaPymesServicioNitIVREntity> alarmaPymesServicioNits;

   public AlarmaPymeIVRDTO() {
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

}