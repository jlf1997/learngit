package com.cimr.api.comm.configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 加载相关属性类
 * @author Administrator
 *
 */
@Configuration
public class IotAutoConfiguration {

	@Bean(name="executorServiceForMongo")
	@Primary
    @ConditionalOnMissingBean
	public ExecutorService getMongoPoolExecutorService() {
		ExecutorService executorService = Executors.newFixedThreadPool(100);
		return executorService;
	}

	
	@Bean(name="executorServiceForSendCodeByKafka")
    @ConditionalOnMissingBean
	public ExecutorService getExecutorService() {
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		return executorService;
	}
	
	@Bean
    @ConditionalOnMissingBean
    public ProjectPropertities projectPropertities() {
        return new ProjectPropertities();
    }
}
