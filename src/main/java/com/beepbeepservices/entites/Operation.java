package com.beepbeepservices.entites;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Entity

@Data  @NoArgsConstructor @ToString
public class Operation implements Serializable{
	
	
	@Id
	@GeneratedValue
	private Long numero;
	
	private Date dateOperation;
	
	private double montant;
	
	@ManyToOne
	@JoinColumn(name = "code_compte" )
	private Compte compte;
	
	@OneToOne
	private Recettejour recetteJour;
	
	
	public Operation(Date dateOperation, double montant,Compte compte,Recettejour recetteJour) {
		super();
		this.dateOperation = dateOperation;
		this.montant=montant;
		this.compte = compte;
		this.recetteJour=recetteJour;
	}
	
	
	
	
	

}
