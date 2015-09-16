package com.claro.cpymes.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


public class ReportDTO implements Serializable {

   /**
    * 
    */
   private static final long serialVersionUID = -3854519863734236668L;

   private String ciudad;

   private String claseEquipo;

   private String codigoServicio;

   private String region;

   private String division;

   private String estadoAlarma;

   private Date fechaEsperanzaDesde;

   private Date fechaEsperanzaHasta;

   private Date fechaFinDesde;

   private Date fechaFinHasta;

   private Date fechaInicioDesde;

   private Date fechaInicioHasta;

   private Date fechaModificacionDesde;

   private Date fechaModificacionHasta;

   private String ip;

   private String nit;

   private String ticketOnix;

   private BigDecimal tiempoTotalFalla;

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

   public Date getFechaEsperanzaDesde() {
      return fechaEsperanzaDesde;
   }

   public void setFechaEsperanzaDesde(Date fechaEsperanzaDesde) {
      this.fechaEsperanzaDesde = fechaEsperanzaDesde;
   }

   public Date getFechaEsperanzaHasta() {
      return fechaEsperanzaHasta;
   }

   public void setFechaEsperanzaHasta(Date fechaEsperanzaHasta) {
      this.fechaEsperanzaHasta = fechaEsperanzaHasta;
   }

   public Date getFechaFinDesde() {
      return fechaFinDesde;
   }

   public void setFechaFinDesde(Date fechaFinDesde) {
      this.fechaFinDesde = fechaFinDesde;
   }

   public Date getFechaFinHasta() {
      return fechaFinHasta;
   }

   public void setFechaFinHasta(Date fechaFinHasta) {
      this.fechaFinHasta = fechaFinHasta;
   }

   public Date getFechaInicioDesde() {
      return fechaInicioDesde;
   }

   public void setFechaInicioDesde(Date fechaInicioDesde) {
      this.fechaInicioDesde = fechaInicioDesde;
   }

   public Date getFechaInicioHasta() {
      return fechaInicioHasta;
   }

   public void setFechaInicioHasta(Date fechaInicioHasta) {
      this.fechaInicioHasta = fechaInicioHasta;
   }

   public Date getFechaModificacionDesde() {
      return fechaModificacionDesde;
   }

   public void setFechaModificacionDesde(Date fechaModificacionDesde) {
      this.fechaModificacionDesde = fechaModificacionDesde;
   }

   public Date getFechaModificacionHasta() {
      return fechaModificacionHasta;
   }

   public void setFechaModificacionHasta(Date fechaModificacionHasta) {
      this.fechaModificacionHasta = fechaModificacionHasta;
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

   public BigDecimal getTiempoTotalFalla() {
      return tiempoTotalFalla;
   }

   public void setTiempoTotalFalla(BigDecimal tiempoTotalFalla) {
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

   public String getIp() {
      return ip;
   }

   public void setIp(String ip) {
      this.ip = ip;
   }

}
