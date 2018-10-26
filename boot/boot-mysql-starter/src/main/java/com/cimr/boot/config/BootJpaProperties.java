package com.cimr.boot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 属性
 * @author Administrator
 *
 */
@Component
@ConfigurationProperties(prefix = "boot.jpa.config")
public class BootJpaProperties {

	private String type ;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
	
}
