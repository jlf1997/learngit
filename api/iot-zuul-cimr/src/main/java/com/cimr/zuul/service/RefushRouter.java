package com.cimr.zuul.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cimr.boot.utils.CacheManager;
import com.cimr.routers.model.ZuulRoute;
import com.cimr.routers.service.ZuulRouteService;

/**
 * 定时刷新router
 * @author Administrator
 *
 */
@Component
public class RefushRouter {
	
	@Autowired
	private ZuulRouteService zuulRouteService;
	
	@Scheduled(cron = "0 0/30 * * * *")
    public void refushRouter(){
		ZuulRoute t = new ZuulRoute(); 
	    t.setEnabled(true);
		List<ZuulRoute> results = zuulRouteService.findAll(t);
		CacheManager.setData("router", results);
		
	
	}

}
