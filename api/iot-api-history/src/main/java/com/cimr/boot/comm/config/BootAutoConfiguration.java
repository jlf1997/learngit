package com.cimr.boot.comm.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cimr.boot.mongodb.config.MongoOptionProperties;

/**
 * 加载相关属性类
 * @author Administrator
 *
 */
@Configuration
public class BootAutoConfiguration {

	
	@Bean
    @ConditionalOnMissingBean
	public MongoOptionProperties mongoOptionProperties() {
		return new MongoOptionProperties();
	}
	
	

	
}
