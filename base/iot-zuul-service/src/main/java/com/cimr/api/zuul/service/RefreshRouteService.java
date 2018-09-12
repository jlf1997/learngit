package com.cimr.api.zuul.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.RoutesRefreshedEvent;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.cimr.api.routers.model.ZuulRoute;
import com.cimr.api.routers.service.ZuulRouteService;
import com.cimr.boot.utils.CacheManager;
 
@Service
public class RefreshRouteService {
    @Autowired
    ApplicationEventPublisher publisher;
 
    @Autowired
    RouteLocator routeLocator;
    @Autowired
    private ZuulRouteService zuulRouteService;
 
    public void refreshRoute() {
    	ZuulRoute t = new ZuulRoute(); 
	    t.setEnabled(true);
		List<ZuulRoute> results = zuulRouteService.findAll(t);
		CacheManager.setData("router", results);
        RoutesRefreshedEvent routesRefreshedEvent = new RoutesRefreshedEvent(routeLocator);
        publisher.publishEvent(routesRefreshedEvent);
    }
}
