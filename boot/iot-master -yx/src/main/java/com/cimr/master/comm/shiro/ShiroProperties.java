package com.cimr.master.comm.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "iot.shiro")
public class ShiroProperties {

	
	/**
	 * 未登录跳转地址
	 */
	private String loginUrl = "/login/blank";
	
	
	/**
	 * 无权限跳转地址
	 */
	private String unauthorizedUrl = "/login/blank";
	
	/**
	 * shiro路由配置
	 */
	private Map<String,String> filterChainDefinitionMap = new LinkedHashMap<>();


	public String getLoginUrl() {
		return loginUrl;
	}


	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}


	public String getUnauthorizedUrl() {
		return unauthorizedUrl;
	}


	public void setUnauthorizedUrl(String unauthorizedUrl) {
		this.unauthorizedUrl = unauthorizedUrl;
	}


	public Map<String, String> getFilterChainDefinitionMap() {
		return filterChainDefinitionMap;
	}


	public void setFilterChainDefinitionMap(Map<String, String> filterChainDefinitionMap) {
		this.filterChainDefinitionMap = filterChainDefinitionMap;
	}
	
	
	
	
}
