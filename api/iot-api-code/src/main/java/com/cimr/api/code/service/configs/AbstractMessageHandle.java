package com.cimr.api.code.service.configs;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import com.cimr.api.code.config.CodeProperties;
import com.cimr.api.code.model.mgr.Message;
import com.cimr.api.code.util.MessageUtil;
import com.cimr.api.websocket.WebSocketInterImple;
import com.cimr.api.websocket.config.WebSocketProperties;
import com.cimr.api.websocket.model.SessionObject;

public abstract class AbstractMessageHandle implements MessageHandle{
	
	
	private static final Logger log = LoggerFactory.getLogger(AbstractMessageHandle.class);

	/**
	 * 记录终端的订阅过期时间key：终端id  value：过期时间
	 */
	protected static Map<String,Date> terMaps = new ConcurrentHashMap<>();

	@Autowired
	private KafkaTemplate<String,String> kafkaTemplate;
	
	@Autowired
    private SimpMessagingTemplate messagingTemplate;
	
	@Autowired
	private CodeProperties codeProperties;
	
	@Autowired
	private  WebSocketProperties webSocketProperties;
	
	
	
	/**
	 * 获取最新的过期时间
	 * @return
	 */
	protected Date getDeadLine() {
		Long now = new Date().getTime();
		Long time = codeProperties.getSubscribeTimeMill();
		return new Date(now+time);
	}
	
	
	/**
	 * 发送kafka消息 获取数据
	 * @param telIds
	 */
	protected void sendRealDataMessage(List<String> telIds) {
		Message message = null;
		try {
			message = MessageUtil.getMessage(90,4,null, null, null, telIds);
			String messageJson=message.toJson();
			log.debug("message:"+messageJson);
			kafkaTemplate.send(codeProperties.getTopicAppToTer(),messageJson);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *发送消息到websocket
	 */
	protected void sendMessage(Message obj) {
		messagingTemplate.convertAndSendToUser(obj.getProducerId(),webSocketProperties.getCallbackUrl(), obj.toJson());
	}
	
	
	@Scheduled(fixedRate = 500)
	protected void scheduleGetData() {
		List<String> telIds = null;
		//判断是否需要重新订阅消息
		Iterator<String> iterator = terMaps.keySet().iterator();
		while(iterator.hasNext()) {
			telIds =  new ArrayList<>();
			String terId = iterator.next();
			Date time = terMaps.get(terId);
			//判断终端是否依然连接			
			if(isOnConnect(terId)) {
				//判断是否过期
				if(new Date().after(time)) {
					telIds.add(terId);
					terMaps.put(terId, getDeadLine());
				}
			}else {
				//从map中移除该终端
				log.debug(terId+"移除");
				terMaps.remove(terId);
			}
		}
		if(telIds!=null && telIds.size()>0 )  {
			sendRealDataMessage(telIds);
		}
	
		
	}
	/**
	 * 判断终端是否离线
	 * @param terId
	 * @return
	 */
	private boolean isOnConnect(String terId) {
		//TODO
		Map<String, SessionObject> map = WebSocketInterImple.getSeesionMap();
		Iterator<String> iterator = map.keySet().iterator();
		while(iterator.hasNext()) {
			String key = iterator.next();
			SessionObject so = map.get(key);
			if(terId.equals(so.getTerid())) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void getRealData(List<String> telIds) {
		//设定需要立即去获取实时数据
		for(String ter : telIds) {
			terMaps.put(ter, new Date());
		}
		
	}
	
	
}
