package com.cimr.boot.auth.shiro.properties;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "iot.auth")
public class AuthProperties {

	
	/**
	 * 未登录跳转地址
	 */
	private String loginUrl = "/login/blank";
	
	
	/**
	 * 无权限跳转地址
	 */
	private String unauthorizedUrl = "/login/blank";
	
	
	private String prefixUrl;
	 
	private String casLoginUrl;
	 
	private String callbackUrl;
	 
	//jwt秘钥
	private String salt;
	    
	    
	    
	
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


	public String getPrefixUrl() {
		return prefixUrl;
	}


	public void setPrefixUrl(String prefixUrl) {
		this.prefixUrl = prefixUrl;
	}


	public String getCasLoginUrl() {
		return casLoginUrl;
	}


	public void setCasLoginUrl(String casLoginUrl) {
		this.casLoginUrl = casLoginUrl;
	}


	public String getCallbackUrl() {
		return callbackUrl;
	}


	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}


	public String getSalt() {
		return salt;
	}


	public void setSalt(String salt) {
		this.salt = salt;
	}
	
	
	
	
}
