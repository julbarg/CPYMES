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
@NamedQueries({ @NamedQuery(name = "NitOnixEntity.findAll", query = "SELECT n FROM NitOnixEntity n"),
   @NamedQuery(name = "NitOnixEntity.findAllCount", query = "SELECT COUNT(n) FROM NitOnixEntity n"), })
public class NitOnixEntity implements Serializable {
   private static final long serialVersionUID = 1L;

   @Id
   private long id;

   @Column(name = "code_service")
   private String codeService;

   private String nit;

   public NitOnixEntity() {
   }

   public long getId() {
      return this.id;
   }

   public void setId(long id) {
      this.id = id;
   }

   public String getCodeService() {
      return this.codeService;
   }

   public void setCodeService(String codeService) {
      this.codeService = codeService;
   }

   public String getNit() {
      return this.nit;
   }

   public void setNit(String nit) {
      this.nit = nit;
   }

}