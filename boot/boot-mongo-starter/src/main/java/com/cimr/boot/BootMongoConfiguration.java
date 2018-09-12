package com.cimr.boot;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.cimr.boot.mongodb.config.MongoOptionProperties;

@Configuration
@EnableConfigurationProperties(value = MongoOptionProperties.class)                                                                        
@ConditionalOnClass(MongoTemplate.class)                                                                                                     
@ConditionalOnProperty(prefix = "boot.mongo", value = "enable", matchIfMissing = true) 
public class BootMongoConfiguration {

	
	@Bean
    @ConditionalOnMissingBean
	public MongoOptionProperties mongoOptionProperties() {
		return new MongoOptionProperties();
	}
}
