package com.cimr.api.dev.jpa.mgr;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.cimr.api.dev.model.mgr.Commands;
@Repository
public interface CommandsJpa extends JpaRepository<Commands, Long>,JpaSpecificationExecutor<Commands>{

}
