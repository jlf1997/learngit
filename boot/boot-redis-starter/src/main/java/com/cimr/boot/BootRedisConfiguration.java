package com.cimr.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import com.cimr.boot.redis.dao.BootHRedisTemplate;
import com.cimr.boot.redis.dao.BootKeyRedisTemplate;
import com.cimr.boot.redis.dao.BootRedisOperation;

@Configuration
@ConditionalOnProperty(prefix = "boot.redis", value = "enable", matchIfMissing = true) 
public class BootRedisConfiguration{
	
	
	private static final Logger log = LoggerFactory.getLogger(BootRedisConfiguration.class);


	@Autowired
	private JedisConnectionFactory factory;
	
	
	
	@Bean
	public BootRedisOperation getBootRedisOperation() {
		BootRedisOperation bop =   new BootRedisOperation(factory);
		log.info("初始化 BootRedisOperation成功");
		return bop;
	}
	
	
	
	
}
