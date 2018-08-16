package com.cimr.api.code.service.configs;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageConfig {

	@Bean
	@ConditionalOnMissingBean
	public MessageHandle getMessageHandle() {
		return new MessageHandleWihtConcurrentHashMap();
	}
	
 
}
