package com.claro.cpymes.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the fecha_esperanza database table.
 * 
 */
@Entity
@Table(name = "fecha_esperanza")
@NamedQueries({
   @NamedQuery(name = "FechaEsperanzaEntity.findAll", query = "SELECT f FROM FechaEsperanzaEntity f"),
   @NamedQuery(name = "FechaEsperanzaEntity.findByDivisionalAndCausa", query = "SELECT f.numeroHoras FROM FechaEsperanzaEntity f WHERE f.divisional=:divisional AND f.causa=:causa") })
public class FechaEsperanzaEntity implements Serializable {
   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private int id;

   private String causa;

   private String divisional;

   @Column(name = "numero_horas")
   private int numeroHoras;

   public FechaEsperanzaEntity() {
   }

   public int getId() {
      return this.id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getCausa() {
      return this.causa;
   }

   public void setCausa(String causa) {
      this.causa = causa;
   }

   public String getDivisional() {
      return this.divisional;
   }

   public void setDivisional(String divisional) {
      this.divisional = divisional;
   }

   public int getNumeroHoras() {
      return this.numeroHoras;
   }

   public void setNumeroHoras(int numeroHoras) {
      this.numeroHoras = numeroHoras;
   }

}