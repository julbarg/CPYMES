package com.claro.cpymes.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the parametro database table.
 * 
 */
@Entity
@Table(name = "parametro")
@NamedQueries({
   @NamedQuery(name = "ParametroEntity.findAll", query = "SELECT p FROM ParametroEntity p"),
   @NamedQuery(name = "ParametroEntity.findByName", query = "SELECT p.value FROM ParametroEntity p WHERE p.name = :name"), })
public class ParametroEntity implements Serializable {
   private static final long serialVersionUID = 1L;

   @Id
   private String name;

   private String value;

   public ParametroEntity() {
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getValue() {
      return this.value;
   }

   public void setValue(String value) {
      this.value = value;
   }

}