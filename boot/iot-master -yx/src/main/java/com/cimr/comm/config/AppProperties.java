package com.cimr.comm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.config")
public class AppProperties {

	private static String domainName;
	
	/**
	 * js版本
	 */
	private static String jsVersion;

	
	/**
	 * api服务器地址端口
	 */
	private static String apiPort;
	/**
	 * api服务器地址
	 */
	private static String apiIp;
	
	/**
	 * 主页地址
	 */
	private static String mainIndexPage;
	
	/**
	 * 默认头像地址
	 */
	private static String defaultHead;

	public static String getDomainName() {
		return domainName;
	}
	
	@Value("${app.config.domainName}")
	public static void setDomainName(String domainName) {
		AppProperties.domainName = domainName;
	}

	

	public static String getJsVersion() {
		return jsVersion;
	}
	
	@Value("${app.config.jsVersion}")
	public static void setJsVersion(String jsVersion) {
		AppProperties.jsVersion = jsVersion;
	}

	
	

	public static String getApiPort() {
		return apiPort;
	}
	@Value("${app.config.apiPort}")
	public static void setApiPort(String apiPort) {
		AppProperties.apiPort = apiPort;
	}

	

	public static String getApiIp() {
		return apiIp;
	}
	@Value("${app.config.apiIp}")
	public static void setApiIp(String apiIp) {
		AppProperties.apiIp = apiIp;
	}

	public static String getDefaultHead() {
		return defaultHead;
	}
	@Value("${app.config.defaultHead}")
	public static void setDefaultHead(String defaultHead) {
		AppProperties.defaultHead = defaultHead;
	}

	/**
	 * 获取主页地址
	 * @return
	 */
	public static String getMainIndexPage() {
		return mainIndexPage;
	}
	
	@Value("${app.config.mainIndexPage}")
	public static void setMainIndexPage(String mainIndexPage) {
		AppProperties.mainIndexPage = mainIndexPage;
	}

	
	
	
	
}
