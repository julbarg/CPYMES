package com.claro.cpymes.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the nit_onix database table.
 * 
 */
@Entity
@Table(name = "nit_onix")
@NamedQueries({
   @NamedQuery(name = "NitOnixEntity.findAll", query = "SELECT n FROM NitOnixEntity n"),
   @NamedQuery(name = "NitOnixEntity.findAllCount", query = "SELECT COUNT(n) FROM NitOnixEntity n"),
   @NamedQuery(name = "NitOnixEntity.findByEstado", query = "SELECT n FROM NitOnixEntity n WHERE n.estadoServicio =:estado"), })
public class NitOnixEntity implements Serializable {
   private static final long serialVersionUID = 1L;

   @Id
   private long id;

   @Column(name = "estado_servicio")
   private String estadoServicio;

   @Column(name = "id_cliente")
   private long idCliente;

   @Column(name = "id_enlace")
   private String idEnlace;

   private long nit;

   public NitOnixEntity() {
   }

   public long getId() {
      return this.id;
   }

   public void setId(long id) {
      this.id = id;
   }

   public String getEstadoServicio() {
      return this.estadoServicio;
   }

   public void setEstadoServicio(String estadoServicio) {
      this.estadoServicio = estadoServicio;
   }

   public long getIdCliente() {
      return this.idCliente;
   }

   public void setIdCliente(long idCliente) {
      this.idCliente = idCliente;
   }

   public String getIdEnlace() {
      return this.idEnlace;
   }

   public void setIdEnlace(String idEnlace) {
      this.idEnlace = idEnlace;
   }

   public long getNit() {
      return this.nit;
   }

   public void setNit(long nit) {
      this.nit = nit;
   }

}