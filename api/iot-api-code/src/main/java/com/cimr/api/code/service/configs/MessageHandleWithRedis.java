package com.cimr.api.code.service.configs;

import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.hash.HashMapper;

import com.alibaba.fastjson.JSON;
import com.cimr.api.code.config.RedisProperties;
import com.cimr.api.code.model.mgr.Message;
import com.cimr.boot.redis.RedisTemplateConfig;
import com.cimr.boot.redis.utils.MyHashMapper;
import com.cimr.boot.utils.IdGener;

/**
 * 使用redis缓存 kafka消息
 * 目前可能会频繁查询redis 相应key
 * @author Administrator
 *
 */
public class MessageHandleWithRedis extends AbstractMessageHandle{
	
	private HashMapper<String, String, String> mapper;
	private RedisTemplate<String, String> redisTemplate;
	
	@Autowired
	private RedisProperties redisProperties;
	
	
	@Autowired
	private RedisTemplateConfig redisTemplateConfig;
	
    @PostConstruct
    public void demoPostConstruct() {
    	redisTemplate =
        		redisTemplateConfig.getFastJsonStringTemplate(redisProperties.getMessageTempIndex(),String.class);
        mapper =  MyHashMapper.getInstance(String.class, String.class, String.class);
    }

	@Override
	public void saveMessage(String message) {
		// TODO Auto-generated method stub
		Message me = JSON.parseObject(message,Message.class);
		redisTemplate.opsForValue().set("message_temp_:"+IdGener.getInstance().getNormalId()+":"+me.getProducerId(), message);
	}

	@Override
	public void hanleMessage() {
		// TODO Auto-generated method stub
		Set<String> keys = redisTemplate.keys("message_temp_:*");
		for(String key : keys) {
			System.out.println(key);
			Message m = getMessage(redisTemplate.opsForValue().get(key));
			sendMessage(m);
			redisTemplate.delete(key);
		}
	}
	
	private Message getMessage(String message) {
		Message me = JSON.parseObject(message,Message.class);
		return me;
	}

}
