package com.cimr.routers.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.cimr.routers.model.ZuulRoute;

public interface ZuulRouteJpa extends JpaRepository<ZuulRoute, String>, JpaSpecificationExecutor<ZuulRoute>{

}
