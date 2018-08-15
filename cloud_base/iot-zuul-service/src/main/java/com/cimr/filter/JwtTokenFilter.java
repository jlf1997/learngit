package com.cimr.filter;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import com.cimr.routers.model.ExcludeUrl;
import com.cimr.routers.model.ZuulRoute;
import com.cimr.routers.service.ZuulRouteService;
import com.cimr.token.JwtTokenFactory;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public  class JwtTokenFilter extends ZuulFilter{
	
	
	private static final Logger log = LoggerFactory.getLogger(JwtTokenFilter.class);


	@Autowired
	private JwtTokenFactory jwtTokenFactory;
	@Autowired
	private ZuulRouteService zuulRouteService;
	

	public Object run()  throws ZuulException{
		// TODO Auto-generated method stub
		log.info("jwt Token Filter!");
		RequestContext ctx = RequestContext.getCurrentContext();  
        HttpServletRequest request = ctx.getRequest();  
        String token = request.getHeader("Authorization");
        if(token !=null) {
        	log.info("partner token:"+ token);
        	Map<String, Object> jwtClaims = jwtTokenFactory.parserJavaWebToken(token);
        	if(jwtClaims != null) {
        		ctx.setSendZuulResponse(true);//会进行路由，也就是会调用api服务提供者  
                ctx.setResponseStatusCode(200);// 返回错误码  
                ctx.set("isSuccess", true); //可以把一些值放到ctx中，便于后面的filter获取使用  
        	}else {
        		ctx.setSendZuulResponse(false);  
                ctx.setResponseStatusCode(401); 
                ctx.setResponseBody("token error");// 返回错误内容  
                ctx.set("isOK",false);
        	}
        	
        }else {
        	ctx.setSendZuulResponse(false);// 过滤该请求，不对其进行路由  
            ctx.setResponseStatusCode(401);// 返回错误码  
            ctx.setResponseBody("This request needs to be logged in first.");// 返回错误内容  
            ctx.set("isSuccess", false); 
        }
        
		return null;
	}


	@Override
	public boolean shouldFilter() {
		RequestContext ctx = RequestContext.getCurrentContext();  
        HttpServletRequest request = ctx.getRequest();  
        String uri = request.getRequestURI();
        ZuulRoute t = new ZuulRoute(); 
        t.setEnabled(true);
        List<ZuulRoute> results = zuulRouteService.findAll(t);
        if(uri.endsWith(".css")||uri.endsWith(".html")||uri.endsWith(".js")) {
        	return false;
        }
        for(ZuulRoute route : results) {
        	log.info("路径过滤uri:"+uri);
        	 if(uri.startsWith(route.getUrlStart())) {
        		for(ExcludeUrl excludeUrls : route.getExcludeUrls()) {
        			if(uri.startsWith(route.getUrlStart()+excludeUrls.getUrl())) {
        				return false;
        			}
        		}
             	return true;
             }
        }
       
        	
        return false;
	}


	@Override
	public String filterType() {
		return FilterConstants.PRE_TYPE;// 前置过滤器 
	}


	@Override
	public int filterOrder() {
		return 1;
	}

}
