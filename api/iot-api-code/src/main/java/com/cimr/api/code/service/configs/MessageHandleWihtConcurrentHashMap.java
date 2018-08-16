package com.cimr.api.code.service.configs;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.fastjson.JSON;
import com.cimr.api.code.model.mgr.Message;
import com.cimr.boot.utils.IdGener;

/**
 * 使用ConcurrentHashMap 缓存数据 
 * 可用于高并发缓存
 * @author Administrator
 *
 */
public class MessageHandleWihtConcurrentHashMap extends AbstractMessageHandle{

	private Map<Long, String> messageMap = new ConcurrentHashMap<>();
	
	
	@Override
	public void saveMessage(String message) {
		// TODO Auto-generated method stub
		messageMap.put(IdGener.getInstance().getNormalId(), message);
	}

	@Override
	public void hanleMessage() {
		// TODO Auto-generated method stub
		Iterator<Long> iterator = messageMap.keySet().iterator();
		while(iterator.hasNext()) {
			Long id = iterator.next();
			String message = messageMap.get(id);
			Message m = JSON.parseObject(message,Message.class);
			sendMessage(m);
			iterator.remove();
		}
	}



}
