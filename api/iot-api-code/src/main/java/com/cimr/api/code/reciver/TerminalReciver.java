package com.cimr.api.code.reciver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.cimr.api.code.model.warnning.TerminalMessage;
import com.cimr.api.code.service.WarningService;
import com.cimr.boot.utils.GsonUtil;
import com.cimr.boot.utils.LogsUtil;


@Component
public class TerminalReciver {
	
	
	private static final Logger log = LoggerFactory.getLogger(TerminalReciver.class);


	@Autowired
	private WarningService warningService;

	
	//接收来自终端的消息
	@RabbitListener(queues = "DATA_PUBLISH")
    public void process(String message) {
		//约定从终端发送的消息为byte数组形式字符串
		TerminalMessage warnningMessage =null;
		try {
			String[] strs = message.split(",");
			byte[] bytes = new byte[strs.length];
			for(int i=0;i<strs.length;i++) {
				bytes[i] = Byte.parseByte(strs[i]);
			}
			String  messageJson = new String(bytes); 
			log.debug("从主题DATA_PUBLISH收到的消息"+messageJson);
			warnningMessage = GsonUtil.jsonToObj(messageJson, TerminalMessage.class);
		}catch(Exception e) {
			String causes = LogsUtil.getStackTrace(e);
			log.error("消息解析异常："+causes);
			return;
		}
		
		
		
		if(warnningMessage.getType().equals(20)) {
			if(warnningMessage.getTitle().equals(7)) {
				//终端故障消息
				warningService.parseTerWarning(warnningMessage);
				
			}
		}
		if(warnningMessage.getType().equals(10)) {
			if(warnningMessage.getTitle().equals(2)) {
				//故障预警消息
				warningService.parsePlcWarning(warnningMessage);
			}
		}
    }
	
	
 
}
