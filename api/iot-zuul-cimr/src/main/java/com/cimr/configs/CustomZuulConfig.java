package com.cimr.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.cimr.routers.service.ZuulRouteService;
 
@Component
public class CustomZuulConfig {
 
    @Autowired
    ZuulProperties zuulProperties;
    @Autowired
    ServerProperties server;
   
 
    @Bean
    @Autowired
    public CustomRouteLocator routeLocator(ZuulRouteService zuulRouteService) {
        CustomRouteLocator routeLocator = new CustomRouteLocator(this.server.getServletPrefix(), this.zuulProperties);
        routeLocator.setJdbcTemplate(zuulRouteService);
        return routeLocator;
    }
 
}
