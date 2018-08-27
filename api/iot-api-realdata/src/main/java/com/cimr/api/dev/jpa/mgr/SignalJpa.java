package com.cimr.api.dev.jpa.mgr;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.cimr.api.dev.model.mgr.Signal;

public interface SignalJpa extends JpaRepository<Signal, Long>,JpaSpecificationExecutor<Signal>{

}
