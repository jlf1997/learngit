package com.cimr.api.auth.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.cimr.api.auth.model.AuthenticationModel;


public interface AuthenticationModelJpa extends JpaRepository<AuthenticationModel, Long>, JpaSpecificationExecutor<AuthenticationModel>{

}
