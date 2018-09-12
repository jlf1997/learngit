package com.cimr.boot.websocket;

import org.springframework.messaging.simp.stomp.StompHeaderAccessor;

public interface WebSocketChannelInter {
	 //连接成功  
    public void connect(StompHeaderAccessor sha);
  
    //断开连接  
    public void disconnect( StompHeaderAccessor sha); 
       
}
