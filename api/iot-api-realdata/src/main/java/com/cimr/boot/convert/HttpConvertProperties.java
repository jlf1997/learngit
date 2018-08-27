package com.cimr.boot.convert;


import org.springframework.boot.context.properties.ConfigurationProperties;




@ConfigurationProperties("convert.gson")
public class HttpConvertProperties {

	  /**
	   * 配置路径
	   */
	private String path = "com.cimr.boot.convert.DefaultGsonConvert";

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	  
	  
	  
}
