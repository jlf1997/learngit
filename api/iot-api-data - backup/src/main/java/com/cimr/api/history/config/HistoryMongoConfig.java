package com.cimr.api.history.config;

import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.cimr.boot.mongodb.config.AbstractMongoConfig;

@Configuration
@ConfigurationProperties(prefix="spring.data.mongodb.history")
public class HistoryMongoConfig extends AbstractMongoConfig{
	
	public static final String MONGO_TEMPLATE = "history";


	
	
 

    @Bean(name = MONGO_TEMPLATE)
    @Primary
	@Override
	public MongoTemplate getMongoTemplate() throws Exception {
		// TODO Auto-generated method stub
		return new MongoTemplate(simpleFactory());
	}




    
  
   
}
