package com.cimr.api.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import com.cimr.boot.websocket.HttpSessionIdHandshakeInterceptor;
import com.cimr.boot.websocket.PresenceChannelInterceptor;
import com.cimr.boot.websocket.WebSocketChannelInter;


/**
 * websocket配置
 * @author Administrator
 *
 */


@Configuration 
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer{
	
	
	@Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/queue","/topic"); //2
        config.setApplicationDestinationPrefixes("/app");
        config.setUserDestinationPrefix("/queue");
       
    }

	/**
	 * Endpoints 设置
	 */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
    	registry.addEndpoint("/my-websocket")
    	.setAllowedOrigins("*")
    	.withSockJS()
		.setInterceptors(httpSessionIdHandshakeInterceptor()); 
    }
    
    /** 
     * 输入通道参数设置 
     */  
    @Override  
    public void configureClientInboundChannel(ChannelRegistration registration) {  
        registration.taskExecutor().corePoolSize(4) //设置消息输入通道的线程池线程数  
                .maxPoolSize(8)//最大线程数  
                .keepAliveSeconds(60);//线程活动时间  
        registration.setInterceptors(presenceChannelInterceptor());  
    }  

    @Bean  
    public PresenceChannelInterceptor presenceChannelInterceptor() {  
        return new PresenceChannelInterceptor();  
    }  
    
    @Bean  
    public HttpSessionIdHandshakeInterceptor httpSessionIdHandshakeInterceptor() {  
        return new HttpSessionIdHandshakeInterceptor();  
    } 
    
    @Bean
    public WebSocketChannelInter getWebSocketChannelInter() {
    	return new WebSocketInterImple();
    }
}




