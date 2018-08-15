package com.cimr.auth.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.cimr.auth.model.AuthenticationModel;


public interface AuthenticationModelJpa extends JpaRepository<AuthenticationModel, Long>, JpaSpecificationExecutor<AuthenticationModel>{

}
