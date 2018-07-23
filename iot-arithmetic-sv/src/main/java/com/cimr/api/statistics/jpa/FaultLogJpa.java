package com.cimr.api.statistics.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.cimr.api.statistics.model.FaultLog;

public interface FaultLogJpa extends JpaRepository<FaultLog, Long>,JpaSpecificationExecutor<FaultLog>{

}
