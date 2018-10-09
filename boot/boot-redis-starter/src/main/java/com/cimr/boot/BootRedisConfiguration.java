package com.cimr.boot;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cimr.boot.redis.dao.BootRedisTemplate;

@Configuration
@ConditionalOnProperty(prefix = "boot.redis", value = "enable", matchIfMissing = true) 
public class BootRedisConfiguration extends RedisAutoConfiguration{

	
	@Bean
	public BootRedisTemplate getBootRedisDao() {
		return new BootRedisTemplate();
	}
	
	
}
