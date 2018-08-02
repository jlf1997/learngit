package com.cloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloud.filter.AccessFilter;

@Configuration
public class AccessConfig {

	@Bean
	public AccessFilter accessFilter(){
		return new AccessFilter();
	}
}
