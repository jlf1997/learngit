package com.cimr.api.code.sender;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQSender implements MessageSender{
	
    private RabbitTemplate rabbitTemplate;
    
	
	@Autowired
	public RabbitMQSender(RabbitTemplate rabbitTemplate,RabbitCallBack callBack) {
		
		rabbitTemplate.setConfirmCallback(callBack);
		this.rabbitTemplate = rabbitTemplate;
	}





	@Override
	public void send(String id,String key, Object message) {
		CorrelationData correlationData = new CorrelationData(id);
		rabbitTemplate.convertAndSend(key,message,correlationData);
	}






	

}
