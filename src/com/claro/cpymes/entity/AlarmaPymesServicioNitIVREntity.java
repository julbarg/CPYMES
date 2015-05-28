package com.claro.cpymes.entity;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the ALARMA_PYMES_SERVICIO_NIT database table.
 * 
 */
@Entity
@Table(name = "ALARMA_PYMES_SERVICIO_NIT")
@NamedQueries({
   @NamedQuery(name = "AlarmaPymesServicioNitIVREntity.findAll", query = "SELECT a FROM AlarmaPymesServicioNitIVREntity a"),
   @NamedQuery(name = "AlarmaPymesServicioNitIVREntity.findByAlarm", query = "SELECT a FROM AlarmaPymesServicioNitIVREntity a WHERE a.alarmaPyme = :alarm"), })
public class AlarmaPymesServicioNitIVREntity implements Serializable {
   private static final long serialVersionUID = 1L;

   @Id
   @SequenceGenerator(name = "ALARMA_PYMES_SERVICIO_NIT_IDALARMASERVICIONIT_GENERATOR", sequenceName = "ALARMA_PYMES_SEQ")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ALARMA_PYMES_SERVICIO_NIT_IDALARMASERVICIONIT_GENERATOR")
   @Column(name = "ID_ALARMA_SERVICIO_NIT")
   private long idAlarmaServicioNit;

   @Column(name = "CODIGO_SERVICIO")
   private String codigoServicio;

   private String nit;

   // bi-directional many-to-one association to AlarmaPymeIVREntity
   @ManyToOne
   @JoinColumn(name = "ID_ALARMA_PYMES")
   private AlarmaPymeIVREntity alarmaPyme;

   public AlarmaPymesServicioNitIVREntity() {
   }

   public long getIdAlarmaServicioNit() {
      return this.idAlarmaServicioNit;
   }

   public void setIdAlarmaServicioNit(long idAlarmaServicioNit) {
      this.idAlarmaServicioNit = idAlarmaServicioNit;
   }

   public String getCodigoServicio() {
      return this.codigoServicio;
   }

   public void setCodigoServicio(String codigoServicio) {
      this.codigoServicio = codigoServicio;
   }

   public String getNit() {
      return this.nit;
   }

   public void setNit(String nit) {
      this.nit = nit;
   }

   public AlarmaPymeIVREntity getAlarmaPyme() {
      return this.alarmaPyme;
   }

   public void setAlarmaPyme(AlarmaPymeIVREntity alarmaPyme) {
      this.alarmaPyme = alarmaPyme;
   }

}