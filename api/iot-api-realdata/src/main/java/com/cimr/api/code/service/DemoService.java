package com.cimr.api.code.service;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import com.cimr.boot.redis.dao.BootHRedisTemplate;

@Service
public class DemoService {
	
	
	private static final Logger log = LoggerFactory.getLogger(DemoService.class);


	@Resource(name="fastJsonredisTemplate")
	private RedisTemplate<Object, Object> redisTemplate ;
	
	private BootHRedisTemplate bootHRedisTemplate;
	
	@PostConstruct
	public void init() {
		if(redisTemplate==null) {
			log.error("null===========");
		}
		bootHRedisTemplate = new BootHRedisTemplate(redisTemplate);
		bootHRedisTemplate.hashValueSerializer(new FastJsonRedisSerializer(Object.class));
	}
	
	public void put(String key,String hk,Object value) {
		bootHRedisTemplate.put(key, hk, value);
	}
	
	public Map<Object,Object> find(String key,String p) {
		bootHRedisTemplate.limit(100);
		return bootHRedisTemplate.getHMapObject(key, "*"+p+"*");
	}
	
	
	
}
