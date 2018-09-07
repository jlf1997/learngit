package com.cimr.api.dev.jpa.mgr;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.cimr.api.dev.model.mgr.Dict;
@Repository
public interface DictJpa extends JpaRepository<Dict, Long>,JpaSpecificationExecutor<Dict>{

}
