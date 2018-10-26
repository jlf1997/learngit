package com.cimr.boot.redis;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import com.cimr.baseutil.BaseBeanUtils;
import com.cimr.boot.utils.LogsUtil;

@Configuration
@EnableAutoConfiguration  
public class RedisTemplateConfig extends RedisAutoConfiguration{

	
	private static final Logger log = LoggerFactory.getLogger(RedisTemplateConfig.class);

	@Autowired
	private JedisConnectionFactory factory;
	
	
   public static  JedisConnectionFactory getJedisConnectionFactory(JedisConnectionFactory factory) {
	   JedisConnectionFactory jedisConnectionFactory =  new JedisConnectionFactory();
	    try {
			BeanUtils.copyProperties(factory, jedisConnectionFactory);
			BaseBeanUtils.copy(factory
					,jedisConnectionFactory,"cluster","sentinelConfig","clusterConfig","clusterCommandExecutor"
					,"useSsl");

		} catch (Exception e) {
			LogsUtil.getStackTrace(e);
		}
	    return jedisConnectionFactory;
	   
   }
   
   
   
   
	
 
	/**
	 * 使用jackson进行redis 序列化
	 * @param clazz
	 * @return
	 */

		
	 public <String, V> RedisTemplate<String, V> getJacksonStringTemplate(Class<V> clazz) {
		 JedisConnectionFactory jedisConnectionFactory = getJedisConnectionFactory(factory);
	        RedisTemplate<String, V> redisTemplate = new RedisTemplate<String, V>();
	        redisTemplate.setConnectionFactory(jedisConnectionFactory);
	        redisTemplate.setKeySerializer(new StringRedisSerializer());	       
	        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<V>(clazz));
	        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
	        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
	        // 不是注入方法的话，必须调用它。Spring注入的话，会在注入时调用
	        redisTemplate.afterPropertiesSet();
	        return redisTemplate;
	  }
	
	
	/**
	 * 使用fastJson进行redis 序列化
	 * @param clazz
	 * @return
	 */

	 public <String, V> RedisTemplate<String, V> getFastJsonStringTemplate(int databaseIndex,Class<V> clazz) {
	        RedisTemplate<String, V> redisTemplate = new RedisTemplate<String, V>();
	        JedisConnectionFactory jedisConnectionFactory = getJedisConnectionFactory(factory);
	        jedisConnectionFactory.setDatabase(databaseIndex);
	        redisTemplate.setConnectionFactory(jedisConnectionFactory);
	        log.info("设置库号："+databaseIndex);
	        redisTemplate.setKeySerializer(new StringRedisSerializer());
	        redisTemplate.setValueSerializer(new FastJsonRedisSerializer<V>(clazz));
	        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
	        redisTemplate.setHashValueSerializer(new StringRedisSerializer());	
	       
	        // 不是注入方法的话，必须调用它。Spring注入的话，会在注入时调用
	        redisTemplate.afterPropertiesSet();

	        return redisTemplate;
	  }
	 

	 
	 

}
