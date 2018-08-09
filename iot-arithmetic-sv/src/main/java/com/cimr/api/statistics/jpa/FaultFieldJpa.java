package com.cimr.api.statistics.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.cimr.api.statistics.model.FaultField;


public interface FaultFieldJpa extends JpaRepository<FaultField, Long>,JpaSpecificationExecutor<FaultField>{

	
}
