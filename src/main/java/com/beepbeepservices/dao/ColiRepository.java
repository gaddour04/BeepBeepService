package com.beepbeepservices.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.beepbeepservices.entites.Coli;
import com.beepbeepservices.entites.User;



public interface ColiRepository extends JpaRepository<Coli, Long>{
	public List<Coli> findByChauffeurIsNull();
	@Query("select x from Coli x where x.dateCreated like :x and x.chauffeur IS NULL") 
	public List<Coli> findByDate(@Param("x") String date);
	
	public List<Coli> findByChauffeurIsNullAndDateCreated(Date date);
	
	public List<Coli> findByEtat(String etat);
	public List<Coli> findByEtatAndUser(String etat,User user);
	public List<Coli> findByEtatAndUserAndDateCreated(String etat,User user,Date date);
	@Query("SELECT SUM(c.prix)  FROM  Coli c  WHERE c.user=:user and c.etat='Livré' and c.dateCreated=:date")
	public double montantLivreparJour(@Param("user")User u,@Param("date") Date date);
	
	
	public List<Coli> findByChauffeur(User user);
	
	@Query("select c from Coli c where c.reference like :x")
	public Page<Coli> chercherAdmin(@Param("x")String reference,Pageable pageable);
	
	
	@Query("select c from Coli c where c.reference like :x and c.user.username=:user")
	public Page<Coli> chercherClient(@Param("x")String reference,@Param("user") String username,Pageable pageable);
	
	
	

	
	
	


}
