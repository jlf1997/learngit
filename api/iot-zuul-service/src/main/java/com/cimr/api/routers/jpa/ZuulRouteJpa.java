package com.cimr.api.routers.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.cimr.api.routers.model.ZuulRoute;

public interface ZuulRouteJpa extends JpaRepository<ZuulRoute, String>, JpaSpecificationExecutor<ZuulRoute>{

}
