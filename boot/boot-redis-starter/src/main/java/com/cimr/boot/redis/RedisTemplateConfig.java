package com.cimr.boot.redis;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import javax.annotation.PostConstruct;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.support.collections.RedisProperties;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import com.cimr.boot.redis.utils.MyHashMapper;
import com.cimr.boot.utils.LogsUtil;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableAutoConfiguration  
public class RedisTemplateConfig extends RedisAutoConfiguration{

	
	private static final Logger log = LoggerFactory.getLogger(RedisTemplateConfig.class);

	@Autowired
	private JedisConnectionFactory factory;
//	@Autowired
//	private RedisClusterConfiguration clusterConfig;
//	@Autowired
//	private JedisPoolConfig poolConfig;
	
	
   private JedisConnectionFactory getJedisConnectionFactory() {
	   JedisConnectionFactory jedisConnectionFactory =  new JedisConnectionFactory();
	    try {
			BeanUtils.copyProperties(jedisConnectionFactory, factory);
			setPrivateValue(jedisConnectionFactory
					,factory,"cluster","sentinelConfig","clusterConfig","clusterCommandExecutor"
					,"useSsl");

		} catch (Exception e) {
			LogsUtil.getStackTrace(e);
		}
	    return jedisConnectionFactory;
	   
   }
   
   private void setPrivateValue(
		   JedisConnectionFactory jedisConnectionFactory,
		   JedisConnectionFactory factory,
		   String...fields
		   ) throws Exception{
	   Class<?> classType = factory.getClass();
	   for(String fieldstr:fields) {
		   Field field = classType.getDeclaredField(fieldstr);
	       field.setAccessible(true); // 抑制Java对修饰符的检查
	       field.set(jedisConnectionFactory, field.get(factory));
	   }
       
   }
	
 
	/**
	 * 使用jackson进行redis 序列化
	 * @param clazz
	 * @return
	 */

		
	 public <String, V> RedisTemplate<String, V> getJacksonStringTemplate(Class<V> clazz) {
		 JedisConnectionFactory factory = getJedisConnectionFactory();
	        RedisTemplate<String, V> redisTemplate = new RedisTemplate<String, V>();
	        redisTemplate.setConnectionFactory(factory);
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
	        JedisConnectionFactory factory = getJedisConnectionFactory();
	        factory.setDatabase(databaseIndex);
	        redisTemplate.setConnectionFactory(factory);
	        log.info("设置库号："+databaseIndex);
	        redisTemplate.setKeySerializer(new StringRedisSerializer());
	        redisTemplate.setValueSerializer(new FastJsonRedisSerializer<V>(clazz));
	        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
	        redisTemplate.setHashValueSerializer(new StringRedisSerializer());	
	       
	        // 不是注入方法的话，必须调用它。Spring注入的话，会在注入时调用
	        redisTemplate.afterPropertiesSet();

	        return redisTemplate;
	  }
	 
//	 @Bean
//	 public RedisTemplate getDefaultRedisTemplate() {
//		
//		 RedisTemplate<String, HashMap> redisTemplate ;
//		 redisTemplate =
//				 getFastJsonStringTemplate(0,HashMap.class);
//		 return redisTemplate;
//	 }
	 
	 @Bean
	 public HashMapper getDefaultMapper() {
		   HashMapper<HashMap, String, String> mapper =
		    		MyHashMapper.getInstance(HashMap.class, String.class, String.class);
		   return mapper;
	 }
	 
	 

}
