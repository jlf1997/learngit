package com.cimr.api.zuul.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.web.ZuulHandlerMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cimr.api.routers.model.ZuulRoute;
import com.cimr.api.routers.service.ZuulRouteService;
import com.cimr.api.zuul.service.RefreshRouteService;
import com.cimr.boot.utils.CacheManager;

@RestController
public class RefreshController {
    @Autowired
    RefreshRouteService refreshRouteService;
    @Autowired
    ZuulHandlerMapping zuulHandlerMapping;
    
    @Autowired
    private ZuulRouteService zuulRouteService;
 
    @GetMapping("/refreshRoute")
    public String refresh() {
      	ZuulRoute t = new ZuulRoute(); 
	    t.setEnabled(true);
		List<ZuulRoute> results = zuulRouteService.findAll(t);
		CacheManager.setData("router", results);
        refreshRouteService.refreshRoute();
        return "refresh success";
    }
 
    @RequestMapping("/watchRoute")
    public Object watchNowRoute() {
        //可以用debug模式看里面具体是什么
        Map<String, Object> handlerMap = zuulHandlerMapping.getHandlerMap();
        return handlerMap;
    }
}
