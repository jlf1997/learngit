package com.cimr.boot;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "boot.mysql", value = "enable", matchIfMissing = true) 
public class BootRedisConfiguration {

	
}
