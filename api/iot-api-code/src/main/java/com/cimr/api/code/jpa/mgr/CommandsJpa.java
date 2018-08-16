package com.cimr.api.code.jpa.mgr;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.cimr.api.code.model.mgr.Commands;
@Repository
public interface CommandsJpa extends JpaRepository<Commands, Long>,JpaSpecificationExecutor<Commands>{

}
