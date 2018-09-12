package com.cimr.api.history.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.cimr.boot.mongodb.config.AbstractMongoConfig;

@Configuration
@ConfigurationProperties(prefix="spring.data.mongodb.history")
public class HistoryMongoConfig extends AbstractMongoConfig{

    @Bean(name = "history")
	@Override
	public MongoTemplate getMongoTemplate() throws Exception {
		// TODO Auto-generated method stub
		return new MongoTemplate(simpleFactory());
	}



    
  
   
}
