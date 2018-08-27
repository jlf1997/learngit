package com.cimr.api.comm.configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cimr.api.code.config.RedisProperties;

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
	
	@Bean(name="executorServiceForSendCodeByKafka")
    @ConditionalOnMissingBean
	public ExecutorService getExecutorService() {
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		return executorService;
	}
}
