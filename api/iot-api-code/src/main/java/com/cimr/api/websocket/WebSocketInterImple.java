package com.cimr.api.websocket;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.messaging.simp.stomp.StompHeaderAccessor;

import com.cimr.api.websocket.model.SessionObject;
import com.cimr.boot.websocket.WebSocketChannelInter;

public class WebSocketInterImple implements WebSocketChannelInter{

	
	
	//记录连接websocket的终端
	private static Map<String,SessionObject> seesionMap = new ConcurrentHashMap<>();

	@Override
	public void connect(StompHeaderAccessor sha) {
		List<String> terids = sha.getNativeHeader("terid");
		SessionObject so = new SessionObject();
		if(terids!=null) {
			String terid = terids.get(0);	
			so.setTerid(terid);
		}else {
			
		}
		so.setSa(sha);		
		seesionMap.put(sha.getSessionId(),so);
	}

	@Override
	public void disconnect(StompHeaderAccessor sha) {
		seesionMap.remove(sha.getSessionId());
	}

	public static Map<String, SessionObject> getSeesionMap() {
		return seesionMap;
	}

	public static void setSeesionMap(Map<String, SessionObject> seesionMap) {
		WebSocketInterImple.seesionMap = seesionMap;
	}

	
	
	
	
}
