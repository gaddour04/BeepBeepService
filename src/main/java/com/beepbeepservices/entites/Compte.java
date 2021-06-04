package com.beepbeepservices.entites;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Data;

@Entity

@Data  @NoArgsConstructor @AllArgsConstructor @ToString
public  class Compte implements Serializable{
	@Id
	@NotEmpty
	private String codeCompte;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateCreation;
	private Double solde;
	@ManyToOne
	@JoinColumn(name = "user_id" )
	private User user;
	@OneToMany(mappedBy = "compte",cascade = CascadeType.REMOVE)
	private Collection<Operation> operation;
	
	

	

}
