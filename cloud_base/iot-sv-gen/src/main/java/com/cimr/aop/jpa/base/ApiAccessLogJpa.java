package com.cimr.aop.jpa.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.cimr.aop.model.base.ApiAccessLog;

public interface ApiAccessLogJpa extends JpaRepository<ApiAccessLog, Long>,JpaSpecificationExecutor<ApiAccessLog>{

}
