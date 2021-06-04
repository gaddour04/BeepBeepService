package com.beepbeepservices.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.beepbeepservices.entites.Operation;

public interface OperationRepository extends JpaRepository<Operation, Long>{

}
