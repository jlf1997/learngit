package com.cimr.boot.comm.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.cimr.boot.exception.DefaultHandlleException;
import com.cimr.boot.exception.HandlleException;
import com.cimr.boot.mongodb.config.MongoOptionProperties;
import com.cimr.boot.websocket.DefaultWebSocketChannelInter;
import com.cimr.boot.websocket.WebSocketChannelInter;

/**
 * 加载相关属性类
 * @author Administrator
 *
 */
@Configuration
public class BootAutoConfiguration {

	
	@Bean
    @ConditionalOnMissingBean
	public MongoOptionProperties mongoOptionProperties() {
		return new MongoOptionProperties();
	}
	
	@Bean
	@Primary
	@ConditionalOnMissingBean
	public WebSocketChannelInter getWebSocketChannelInter() {
		return new DefaultWebSocketChannelInter();
	}
	
	
	@Bean
	@Primary
	@ConditionalOnMissingBean
	public HandlleException getHandlleException() {
		return new DefaultHandlleException();
	}
	
}
