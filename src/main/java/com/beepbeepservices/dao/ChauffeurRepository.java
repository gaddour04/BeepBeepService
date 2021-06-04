package com.beepbeepservices.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.beepbeepservices.entites.Chauffeur;

public interface ChauffeurRepository extends JpaRepository<Chauffeur, Long> {

}
