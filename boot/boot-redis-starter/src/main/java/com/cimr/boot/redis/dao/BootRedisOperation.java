package com.cimr.boot.redis.dao;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import com.cimr.boot.redis.RedisTemplateConfig;
import com.cimr.boot.redis.model.RedisPage;

public  class BootRedisOperation<K,V> {
	
	private static final Logger log = LoggerFactory.getLogger(BootRedisOperation.class);

	
	private RedisTemplate<K, V> redisTemplate;
	
	private  int count;
	
	private int limit;
	
	private BootHRedisTemplate<K,V> bootHRedisTemplate;
	
	private BootKeyRedisTemplate<K,V> bootKeyRedisTemplate;
	
	
	public BootRedisOperation(JedisConnectionFactory factory) {
		redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(RedisTemplateConfig.getJedisConnectionFactory(factory));
		this
		.keySerializerWithString()
		.valueSerializerWithFastJson()
		.hashKeySerializerWithString()
		.hashValueSerializerWithFastJson();
		init();
		redisTemplate.afterPropertiesSet();
	}
	
	public BootRedisOperation(RedisTemplate<K, V> redisTemplate) {
		this.redisTemplate = redisTemplate;
		init();
	}
	
	public void init() {
		bootHRedisTemplate = new BootHRedisTemplate<>();
		bootKeyRedisTemplate = new BootKeyRedisTemplate<>();
		limit = 100;
		count = 10000;
	}
	
	public RedisTemplate<K, V> getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate<K, V> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	
	
	public BootRedisOperation<K,V> count(int count) {
		this.count = count;
		return this;
	}

	public BootRedisOperation<K,V> limit(int limit) {
		this.limit = limit;
		return this;
	}
	
	/**
	 * 获取默认fastjson 序列化
	 * @return
	 */
	private FastJsonRedisSerializer getFastJsonRedisSerializer() {
		FastJsonRedisSerializer serializer = new FastJsonRedisSerializer(Object.class);
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		serializer.setFastJsonConfig(fastJsonConfig);
		return serializer;
	}
	
	
	///key
	public BootRedisOperation<K,V> keySerializer(RedisSerializer redisSerializer) {
		redisTemplate.setKeySerializer(redisSerializer);
		return this;
	}
	
	public BootRedisOperation<K,V> keySerializerWithFastJson() {
		return keySerializer(getFastJsonRedisSerializer());
	}
	
	public BootRedisOperation<K,V> keySerializerWithString() {
		redisTemplate.setKeySerializer(getFastJsonRedisSerializer());
		return keySerializer(new StringRedisSerializer());
	}
	////hashKey
	public BootRedisOperation<K,V> hashKeySerializer(RedisSerializer redisSerializer) {
		redisTemplate.setHashKeySerializer(redisSerializer);
		return this;
	}
	public BootRedisOperation<K,V> hashKeySerializerWithFastJson() {
		return hashKeySerializer(getFastJsonRedisSerializer());
	}
	
	public BootRedisOperation<K,V> hashKeySerializerWithString() {
		return hashKeySerializer(new StringRedisSerializer());
	}
	
	///value
	public BootRedisOperation<K,V> valueSerializer(RedisSerializer redisSerializer) {
		redisTemplate.setValueSerializer(redisSerializer);
		return this;
	}
	
	public BootRedisOperation<K,V> valueSerializerWithFastJson() {
		return valueSerializer(getFastJsonRedisSerializer());
	}
	
	public BootRedisOperation<K,V> valueSerializerWithString() {
		return valueSerializer(new StringRedisSerializer());
	}
	
	/////hashValue
	
	public BootRedisOperation<K,V> hashValueSerializer(RedisSerializer redisSerializer) {
		redisTemplate.setHashValueSerializer(redisSerializer);
		return this;
	}
	
	public BootRedisOperation<K,V> hashValueSerializerWithFastJson() {
		return hashValueSerializer(getFastJsonRedisSerializer());
	}
	
	
	public BootRedisOperation<K,V> hashValueSerializerWithString() {
		return hashValueSerializer(new StringRedisSerializer());
	}
	
	
	

	/**
	 * 从哈希数据类型中获取数据 使用scan方式
	 * @param redisTemplate
	 * @param key
	 * @param pattern
	 * @param limit
	 * @param page
	 * @param count
	 * @return
	 */
	public RedisPage getHashPage(K key,String pattern,int page) {
		return bootHRedisTemplate.getHPage(key, pattern, page, redisTemplate, limit, count);
	}
	
	/**
	 * 使用scan获取全部
	 * @param key
	 * @param pattern
	 * @return
	 */
	public Map<Object,Object> getHashAll(K key,String pattern) {
		return bootHRedisTemplate.getHMapObject(key, pattern, redisTemplate, limit, count);
	}

	/**
	 * 分页查询  使用scan方法
	 * @param pattern
	 * @param page
	 * @return
	 */
	public RedisPage keyPages(String pattern,int page) {
		 return bootKeyRedisTemplate.keyPages(pattern, page, redisTemplate, limit, count);
	 }
	
}
