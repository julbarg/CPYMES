package com.claro.cpymes.dto;

import java.io.Serializable;


public class DataDTO implements Serializable {

   /**
    * 
    */
   private static final long serialVersionUID = 8092676511073413719L;

   private String idAlarmaPymes;

   private String ciudad;

   private String claseEquipo;

   private String codigoServicio;

   private String descripcionAlarma;

   private String region;

   private String division;

   private String estadoAlarma;

   private String fechaEsperanza;

   private String fechaFin;

   private String fechaInicio;

   private String fechaModificacion;

   private String ip;

   private String nit;

   private String ticketOnix;

   private String tiempoTotalFalla;

   private String tipoEvento;

   private String usuarioModificacion;

   public String getCiudad() {
      return ciudad;
   }

   public void setCiudad(String ciudad) {
      this.ciudad = ciudad;
   }

   public String getClaseEquipo() {
      return claseEquipo;
   }

   public void setClaseEquipo(String claseEquipo) {
      this.claseEquipo = claseEquipo;
   }

   public String getCodigoServicio() {
      return codigoServicio;
   }

   public void setCodigoServicio(String codigoServicio) {
      this.codigoServicio = codigoServicio;
   }

   public String getDescripcionAlarma() {
      return descripcionAlarma;
   }

   public void setDescripcionAlarma(String descripcionAlarma) {
      this.descripcionAlarma = descripcionAlarma;
   }

   public String getRegion() {
      return region;
   }

   public void setRegion(String region) {
      this.region = region;
   }

   public String getDivision() {
      return division;
   }

   public void setDivision(String division) {
      this.division = division;
   }

   public String getEstadoAlarma() {
      return estadoAlarma;
   }

   public void setEstadoAlarma(String estadoAlarma) {
      this.estadoAlarma = estadoAlarma;
   }

   public String getFechaEsperanza() {
      return fechaEsperanza;
   }

   public void setFechaEsperanza(String fechaEsperanza) {
      this.fechaEsperanza = fechaEsperanza;
   }

   public String getFechaFin() {
      return fechaFin;
   }

   public void setFechaFin(String fechaFin) {
      this.fechaFin = fechaFin;
   }

   public String getFechaInicio() {
      return fechaInicio;
   }

   public void setFechaInicio(String fechaInicio) {
      this.fechaInicio = fechaInicio;
   }

   public String getFechaModificacion() {
      return fechaModificacion;
   }

   public void setFechaModificacion(String fechaModificacion) {
      this.fechaModificacion = fechaModificacion;
   }

   public String getIp() {
      return ip;
   }

   public void setIp(String ip) {
      this.ip = ip;
   }

   public String getNit() {
      return nit;
   }

   public void setNit(String nit) {
      this.nit = nit;
   }

   public String getTicketOnix() {
      return ticketOnix;
   }

   public void setTicketOnix(String ticketOnix) {
      this.ticketOnix = ticketOnix;
   }

   public String getTiempoTotalFalla() {
      return tiempoTotalFalla;
   }

   public void setTiempoTotalFalla(String tiempoTotalFalla) {
      this.tiempoTotalFalla = tiempoTotalFalla;
   }

   public String getTipoEvento() {
      return tipoEvento;
   }

   public void setTipoEvento(String tipoEvento) {
      this.tipoEvento = tipoEvento;
   }

   public String getUsuarioModificacion() {
      return usuarioModificacion;
   }

   public void setUsuarioModificacion(String usuarioModificacion) {
      this.usuarioModificacion = usuarioModificacion;
   }

   public String getIdAlarmaPymes() {
      return idAlarmaPymes;
   }

   public void setIdAlarmaPymes(String idAlarmaPymes) {
      this.idAlarmaPymes = idAlarmaPymes;
   }

}
