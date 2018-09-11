package com.cimr;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.cimr.boot.utils.CacheManager;
import com.cimr.routers.model.ZuulRoute;
import com.cimr.routers.service.ZuulRouteService;

@Component
public class MyApplicationRunner implements ApplicationRunner{

	@Autowired
	private ZuulRouteService zuulRouteService;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		ZuulRoute t = new ZuulRoute(); 
	    t.setEnabled(true);
		List<ZuulRoute> results = zuulRouteService.findAll(t);
		CacheManager.setData("router", results);
	}

}
