package com.cimr.boot.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.stereotype.Component; 

@Component 
public class PresenceChannelInterceptor extends ChannelInterceptorAdapter{
	
	private static final Logger logger = LoggerFactory.getLogger(PresenceChannelInterceptor.class);
  
	@Autowired
	private WebSocketChannelInter webSocketChannelInter;

	  
    @Override  
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {  
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(message);  
        // ignore non-STOMP messages like heartbeat messages  
        if(sha.getCommand() == null) {  
            return;  
        }  

        //判断客户端的连接状态  
        switch(sha.getCommand()) {  
            case CONNECT:  
            	webSocketChannelInter.connect(sha);  
                break;  
            case CONNECTED:  
                break;  
            case DISCONNECT:  
            	webSocketChannelInter.disconnect(sha);  
                break;  
            default:  
                break;  
        }  
    }  
  
    
  
}
