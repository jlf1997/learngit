package com.cimr.api.websocket.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 指令相关配置
 * @author Administrator
 *
 */
@ConfigurationProperties("iot.websocket")
public class WebSocketProperties {


	
	private String callbackUrl = "/callback";

	

	public String getCallbackUrl() {
		return callbackUrl;
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}
	
	
	
	
}
