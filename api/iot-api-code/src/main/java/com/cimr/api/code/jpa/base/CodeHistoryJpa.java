package com.cimr.api.code.jpa.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.cimr.api.code.model.base.CodeHistory;

public interface CodeHistoryJpa extends JpaRepository<CodeHistory, Long>,JpaSpecificationExecutor<CodeHistory>{

}
