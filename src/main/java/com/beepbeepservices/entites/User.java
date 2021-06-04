package com.beepbeepservices.entites;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor @ToString
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "ROLE",length = 10)
public abstract class User implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;
	@Email
	@NotEmpty
	@Column(unique = true)
	private String email;
	@NotEmpty
	@Column(unique = true)
	private String username;
	@NotEmpty
	private String password;
	@ManyToOne
	private Ville ville;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateJointure;
	private String image;
	@OneToMany(mappedBy = "user",fetch =FetchType.LAZY,cascade = CascadeType.REMOVE)
	private Collection<Compte> comptes;
	
	public User(@Email String email, @NotEmpty String username, String password,
			Ville ville, Date dateJointure, String image) {
		super();
		
		this.email = email;
		this.username = username;
		this.password = password;
		this.ville = ville;
		this.dateJointure = dateJointure;
		this.image = image;
		
	}
	
	
	
	
}
