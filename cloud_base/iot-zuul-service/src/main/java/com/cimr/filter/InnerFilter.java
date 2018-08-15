package com.cimr.filter;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;


/**
 * 内部安全控制：控制某些接口只能内部访问
 * @author Administrator
 *
 */
@Component
public class InnerFilter extends ZuulFilter {

	private static Logger log = LoggerFactory.getLogger(InnerFilter.class);  
	
	
	@Override
	public Object run() throws ZuulException{
		RequestContext ctx = RequestContext.getCurrentContext();  
		ctx.setSendZuulResponse(false);  
        ctx.setResponseStatusCode(401);  
        ctx.setResponseBody("This request only access inline");
		return null;
	}

	@Override
	public boolean shouldFilter() {
		RequestContext ctx = RequestContext.getCurrentContext();  
        HttpServletRequest request = ctx.getRequest();  
        String uri = request.getRequestURI();
        if(uri.startsWith("/auth/")) {
        	log.info("路径过滤uri:"+uri);
        	//生成appKey只能内部访问
        	if(uri.startsWith("/auth/gen")) {
        		log.error("生成appKey只能内部访问");
        		return true;
        	}
        }
        return false;
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public String filterType() {
		return FilterConstants.PRE_TYPE;// 前置过滤器 
	}

}
