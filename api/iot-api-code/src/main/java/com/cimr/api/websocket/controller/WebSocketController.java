package com.cimr.api.websocket.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.cimr.api.websocket.model.SocketMessage;

import io.swagger.annotations.Api;

@Api(description="websocket 注册消息",tags= {"websocket"})
@Controller
public class WebSocketController {
	
	
	private static final Logger log = LoggerFactory.getLogger(WebSocketController.class);

	
	 @Autowired
	    private SimpMessagingTemplate messagingTemplate;
	 
	    @GetMapping("/test")
	    public String test() {
	        return "test";
	    }

	    @GetMapping("/")
	    public String index() {
	        return "index";
	    }

	    @MessageMapping("/send")
	    @SendTo("/topic/send")
	    public SocketMessage send(SocketMessage message) throws Exception {
	        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        message.date = df.format(new Date());
//	        message.message+="###";
	        return message;
	        
	    }
	    
	    
//	    //获取实时数据
//	    @MessageMapping("/getRealData")
//	    public void sendToOne(String terid) throws Exception {
//	    	//修改对应map中的
////	    	realTimeDateService.getData(terid);
////	    	SocketMessage so = WebSocketInterImple.getSessionObjectByTerId(terid);
////	    	long now = new Date().getTime();
//////	    	long _fiveMin = 5*60*1000;
////	    	long _fiveMin = 1000;
////	    	so.setDeadLine(new Date(now+_fiveMin));
////	    	so.setRealTime(true);
////	    	WebSocketInterImple.updataSet(so);
//	    	log.debug("getRealData");
//	    }

//	    //广播信息
//	    @Scheduled(fixedRate = 1000)
//	    public void callback() throws Exception {
//
//	    	
//	    	Map<String, SessionObject> sessionMap = WebSocketInterImple.getSeesionMap();
//	    	Set<String> terMap = new HashSet<>();
//	    	for(SessionObject so : sessionMap.values()) {
//	    		String terId = so.getTerid();
//	    		terMap.add(terId);
//	    	}
//	    	for(String terId : terMap) {
//	    		messagingTemplate.convertAndSendToUser(terId,"/callback", realTimeDateService.getData(terId));
//	    	}
//	       
//	    }
	    
	    
//	    @Scheduled(fixedRate = 1000)
//	    public void heartbeart() throws Exception {
//	    	Map<String, SessionObject> sessionMap = WebSocketInterImple.getSeesionMap();
//	    	for(SessionObject so : sessionMap.values()) {
//	    		messagingTemplate.convertAndSend(so.getSa().getDestination());
//	    		
//	    	}
//	    }
	    
	
	    
}
