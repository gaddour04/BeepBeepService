package com.beepbeepservices.entites;

import java.util.Date;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data @NoArgsConstructor @ToString
@DiscriminatorValue("CLIENT")
public class Client extends User{public Client( @Email String email, @NotEmpty String username, String password,
			Ville ville, Date dateJointure, String image) {
		super( email, username, password, ville, dateJointure, image);
		// TODO Auto-generated constructor stub
	}
	
	

}
