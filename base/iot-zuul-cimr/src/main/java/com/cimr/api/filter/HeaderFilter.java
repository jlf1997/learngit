package com.cimr.api.filter;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
@Component
public class HeaderFilter extends ZuulFilter{

	@Override
	public boolean shouldFilter() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		RequestContext ctx = RequestContext.getCurrentContext();  
		HttpServletRequest request = ctx.getRequest();  
		Enumeration<String> headers = request.getHeaderNames();
		while(headers.hasMoreElements()) {
			String header = headers.nextElement();
			ctx.addZuulRequestHeader(header, request.getHeader(header));
		}
		ctx.setSendZuulResponse(true);//会进行路由，也就是会调用api服务提供者  
        ctx.setResponseStatusCode(200);// 返回错误码  
		return null;
	}

	@Override
	public String filterType() {
		return FilterConstants.PRE_TYPE;// 前置过滤器 
	}

	@Override
	public int filterOrder() {
		return 0;
	}

}
