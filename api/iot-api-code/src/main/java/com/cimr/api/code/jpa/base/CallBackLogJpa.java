package com.cimr.api.code.jpa.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.cimr.api.code.model.base.CallBackLog;

public interface CallBackLogJpa extends JpaRepository<CallBackLog, Long>,JpaSpecificationExecutor<CallBackLog>{

}
