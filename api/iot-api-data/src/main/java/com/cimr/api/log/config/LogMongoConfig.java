package com.cimr.api.log.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.cimr.boot.mongodb.config.AbstractMongoConfig;

@Configuration
@ConfigurationProperties(prefix="spring.data.mongodb.log")
public class LogMongoConfig extends AbstractMongoConfig{
	
	public static final String MONGO_TEMPLATE = "log";
	



	@Bean(name = MONGO_TEMPLATE)
	@Override
	public MongoTemplate getMongoTemplate() throws Exception {
		// TODO Auto-generated method stub
		return new MongoTemplate(simpleFactory());
	}
    
  
   
}
