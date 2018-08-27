package com.cimr.api.code.util;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.cimr.boot.redis.RedisTemplateConfig;


@Configuration
public class RedisTemplateFactory {
	
	
	@Autowired
	private RedisTemplateConfig redisTemplateConfig;

	
	/**
	 * 获取默认的fastjson序列化template
	 * @return
	 */
	@Bean("fastJsonredisTemplate")
	public RedisTemplate getRedisTemplateIndex0() {
		 RedisTemplate<String, Object> redisTemplate ;
		 redisTemplate =
				 redisTemplateConfig.getFastJsonStringTemplate(0,Object.class);
		 return redisTemplate;
	}
	
	
	/**
	 * 动态切换redis库
	 * @param redisTemplate
	 * @param index
	 * @return
	 */
	public static RedisTemplate changeDataBase(RedisTemplate redisTemplate,int index) {
		JedisConnectionFactory jedisConnectionFactory = (JedisConnectionFactory) redisTemplate.getConnectionFactory();
        jedisConnectionFactory.setDatabase(index);
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        return redisTemplate;
	}
	

	
}
