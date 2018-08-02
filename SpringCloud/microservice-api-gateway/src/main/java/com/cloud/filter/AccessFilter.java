package com.cloud.filter;


import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cloud.constant.HttpRespStatusConstant;
import com.cloud.util.JsonResult;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
@Component
public class AccessFilter  extends ZuulFilter{
	private Logger log = LoggerFactory.getLogger(AccessFilter.class);
	@Override
	public Object run() {//执行实际业务逻辑的方法
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		log.info("{}:{}:{} ",request.getRemoteHost(),request.getMethod(),request.getRequestURL().toString());
		Object accessToken = request.getParameter("accessToken");
		if(accessToken==null){
			log.error("{}:{}:{} accessToken无效",request.getRemoteHost(),request.getMethod(),request.getRequestURL().toString());
			ctx.setSendZuulResponse(false);
			ctx.setResponseStatusCode(401);
			 try {
				 String result = JSON.toJSONString(new JsonResult(HttpRespStatusConstant.AUTHENTICATION_FAILURE_CODE,HttpRespStatusConstant.AUTHENTICATION_FAILURE_MSG));
				 //解决返回值中文问题
				 ctx.getResponse().setCharacterEncoding("GBK");
				 ctx.getResponse().getWriter().write(result);
	            }catch (Exception e){
	            	log.error(e.getMessage());
	            }
		}
		return null;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public int filterOrder() {//执行的顺序
		return 0;
	}

	@Override
	public String filterType() {//过滤器的类型
		return "pre";
	}

}
