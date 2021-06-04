package com.beepbeepservices.entites;

import java.util.Date;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor
@DiscriminatorValue("ADMIN")
public class Admin extends User{public Admin(@Email String email, @NotEmpty String username, String password, Ville ville, Date dateJointure,
			String image) {
		super(email, username, password, ville, dateJointure, image);
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	

}
