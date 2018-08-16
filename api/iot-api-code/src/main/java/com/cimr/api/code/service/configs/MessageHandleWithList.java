package com.cimr.api.code.service.configs;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import com.alibaba.fastjson.JSON;
import com.cimr.api.code.model.mgr.Message;


/**
 * 使用list 共享数据
 * 弱并发支持
 * @author Administrator
 *
 */
public class MessageHandleWithList extends  AbstractMessageHandle{
	private static final Logger log = LoggerFactory.getLogger(MessageHandleWithList.class);

	private static List<String> messageList = new LinkedList<>();
	
//	private static Map<String,Date> terMaps = new HashMap<>();
	
	@Override
	public void saveMessage(String message) {
		// TODO Auto-generated method stub
		messageList.add(message);
	}

	@Override
	public void hanleMessage() {
		// TODO Auto-generated method stub
		Iterator<String> iterator = messageList.iterator();
		while(iterator.hasNext()) {
			String message = iterator.next();
			Message m = JSON.parseObject(message,Message.class);
			sendMessage(m);
			iterator.remove();
		}
	}


	
	
	
	
	
	


	

}
