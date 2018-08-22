package com.cimr.boot.mongodb.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlusMongodbAutoConfig {

	@Bean
    @ConditionalOnMissingBean
	public MongoOptionProperties mongoOptionProperties() {
		return new MongoOptionProperties();
	}
}
