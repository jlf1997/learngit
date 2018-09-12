package com.cimr.api.code.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 加载相关属性类
 * @author Administrator
 *
 */
@Configuration
public class IotAutoConfiguration {

	
	
	
	@Bean
    @ConditionalOnMissingBean
	public RedisProperties redisProperties() {
//		RedisProperties c = new RedisProperties();
		return new RedisProperties();
	}
	

}
