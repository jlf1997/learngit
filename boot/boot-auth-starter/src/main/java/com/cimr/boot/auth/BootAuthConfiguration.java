package com.cimr.boot.auth;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableConfigurationProperties(value = MongoOptionProperties.class)                                                                        
//@ConditionalOnClass(MongoTemplate.class)                                                                                                     
@ConditionalOnProperty(prefix = "boot.auth", value = "enable", matchIfMissing = true) 
public class BootAuthConfiguration {

	
	
	
	
	
	
	
}
