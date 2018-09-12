package com.cimr.boot;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "boot.web", value = "enable", matchIfMissing = true) 
public class BootWebConfiguration {

	
	
}
