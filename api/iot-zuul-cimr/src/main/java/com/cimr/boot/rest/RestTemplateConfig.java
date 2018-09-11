package com.cimr.boot.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateConfig {

	@Autowired  
	private RestTemplateBuilder builder; 
	 
	@Bean  
    public RestTemplate restTemplate() {  
        return builder.build();  
    }  
	 
}
