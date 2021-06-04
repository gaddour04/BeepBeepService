package com.beepbeepservices.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.beepbeepservices.entites.Compte;
import com.beepbeepservices.entites.User;

public interface CompteRepository extends JpaRepository<Compte, String>{
	Compte findByUser(User u);

}
