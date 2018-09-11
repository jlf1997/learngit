package com.cimr.configs;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.netflix.zuul.filters.RefreshableRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.util.StringUtils;

import com.cimr.api.routers.model.ZuulRoute;
import com.cimr.api.routers.service.ZuulRouteService;
import com.cimr.boot.utils.CacheManager;
public class CustomRouteLocator extends SimpleRouteLocator implements RefreshableRouteLocator {
	 
    public final static Logger logger = LoggerFactory.getLogger(CustomRouteLocator.class);
 
    private ZuulRouteService zuulRouteService;
 
    private ZuulProperties properties;
 
    public void setJdbcTemplate(ZuulRouteService zuulRouteService) {
        this.zuulRouteService = zuulRouteService;
    }
 
    public CustomRouteLocator(String servletPath, ZuulProperties properties) {
        super(servletPath, properties);
        this.properties = properties;
        logger.info("servletPath:{}", servletPath);
    }
 
    //父类已经提供了这个方法，这里写出来只是为了说明这一个方法很重要！！！
//    @Override
//    protected void doRefresh() {
//        super.doRefresh();
//    }
 
 
    @Override
    public void refresh() {
        doRefresh();
    }
 
    @Override
    protected Map<String, ZuulProperties.ZuulRoute> locateRoutes() {
        LinkedHashMap<String, ZuulProperties.ZuulRoute> routesMap = new LinkedHashMap<>();
        //从application.properties中加载路由信息
        routesMap.putAll(super.locateRoutes());
        //从db中加载路由信息
        routesMap.putAll(locateRoutesFromDB());
        //优化一下配置
        LinkedHashMap<String, ZuulProperties.ZuulRoute> values = new LinkedHashMap<>();
        for (Map.Entry<String, ZuulProperties.ZuulRoute> entry : routesMap.entrySet()) {
            String path = entry.getKey();
            // Prepend with slash if not already present.
            if (!path.startsWith("/")) {
                path = "/" + path;
            }
            if (StringUtils.hasText(this.properties.getPrefix())) {
                path = this.properties.getPrefix() + path;
                if (!path.startsWith("/")) {
                    path = "/" + path;
                }
            }
            values.put(path, entry.getValue());
        }
        return values;
    }
 
    private Map<String, ZuulProperties.ZuulRoute> locateRoutesFromDB() {
        Map<String, ZuulProperties.ZuulRoute> routes = new LinkedHashMap<>();
        ZuulRoute t = new ZuulRoute();
        t.setEnabled(true);
        List<ZuulRoute> results = CacheManager.getData("router");
        if(results==null) {
        	return routes;
        }
        for (ZuulRoute result : results) {
            if (StringUtils.isEmpty(result.getPath()) ) {
                continue;
            }
            if (StringUtils.isEmpty(result.getServiceId()) ) {
                continue;
            }
            ZuulProperties.ZuulRoute zuulRoute = new ZuulProperties.ZuulRoute();
            try {
            	System.out.println("路由信息：path:"+result.getPath()+",serviceId:"+result.getServiceId());
                BeanUtils.copyProperties(result, zuulRoute);
            } catch (Exception e) {
                logger.error("=============load zuul route info from db with error==============", e);
            }
            routes.put(zuulRoute.getPath(), zuulRoute);
        }
        return routes;
    }
 
    
}
