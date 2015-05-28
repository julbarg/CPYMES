package com.claro.cpymes.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the ALARMA_PYMES_VIEW database table.
 * 
 */
//@Entity
//@Table(name="ALARMA_PYMES_VIEW")
//@NamedQuery(name="AlarmaPymesView.findAll", query="SELECT a FROM AlarmaPymesView a")
public class AlarmaPymesView implements Serializable {
	private static final long serialVersionUID = 1L;

	private String ciudad;

//	@Column(name="CLASE_EQUIPO")
	private String claseEquipo;

//	@Column(name="CODIGO_SERVICIO")
	private String codigoServicio;

//	@Column(name="DESCRIPCION_ALARMA")
	private Object descripcionAlarma;

	private String division;

//	@Column(name="ESTADO_ALARMA")
	private String estadoAlarma;

//	@Column(name="FECHA_ESPERANZA")
	private String fechaEsperanza;

//	@Column(name="FECHA_FIN")
	private String fechaFin;

//	@Column(name="FECHA_INICIO")
	private String fechaInicio;

//	@Column(name="FECHA_MODIFICACION")
	private String fechaModificacion;

//	@Column(name="HORA_ESPERANZA")
	private String horaEsperanza;

//	@Column(name="HORA_FIN")
	private String horaFin;

//	@Column(name="HORA_INICIO")
	private String horaInicio;

//	@Column(name="HORA_MODIFICACION")
	private String horaModificacion;

//	@Column(name="ID_ALARMA_PYMES")
	private BigDecimal idAlarmaPymes;

	private String nit;

//	@Column(name="TICKET_ONIX")
	private String ticketOnix;

//	@Column(name="TIEMPO_TOTAL_FALLA")
	private BigDecimal tiempoTotalFalla;

//	@Column(name="TIPO_EVENTO")
	private String tipoEvento;

//	@Column(name="USUARIO_MODIFICACION")
	private String usuarioModificacion;

	public AlarmaPymesView() {
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

	public String getCodigoServicio() {
		return this.codigoServicio;
	}

	public void setCodigoServicio(String codigoServicio) {
		this.codigoServicio = codigoServicio;
	}

	public Object getDescripcionAlarma() {
		return this.descripcionAlarma;
	}

	public void setDescripcionAlarma(Object descripcionAlarma) {
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

	public String getFechaEsperanza() {
		return this.fechaEsperanza;
	}

	public void setFechaEsperanza(String fechaEsperanza) {
		this.fechaEsperanza = fechaEsperanza;
	}

	public String getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getFechaModificacion() {
		return this.fechaModificacion;
	}

	public void setFechaModificacion(String fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public String getHoraEsperanza() {
		return this.horaEsperanza;
	}

	public void setHoraEsperanza(String horaEsperanza) {
		this.horaEsperanza = horaEsperanza;
	}

	public String getHoraFin() {
		return this.horaFin;
	}

	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}

	public String getHoraInicio() {
		return this.horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public String getHoraModificacion() {
		return this.horaModificacion;
	}

	public void setHoraModificacion(String horaModificacion) {
		this.horaModificacion = horaModificacion;
	}

	public BigDecimal getIdAlarmaPymes() {
		return this.idAlarmaPymes;
	}

	public void setIdAlarmaPymes(BigDecimal idAlarmaPymes) {
		this.idAlarmaPymes = idAlarmaPymes;
	}

	public String getNit() {
		return this.nit;
	}

	public void setNit(String nit) {
		this.nit = nit;
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

}