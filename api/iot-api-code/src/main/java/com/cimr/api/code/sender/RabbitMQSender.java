package com.cimr.api.code.sender;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;

public class RabbitMQSender implements MessageSender,RabbitTemplate.ConfirmCallback{
	
    private RabbitTemplate rabbitTemplate;
	
	
	private SendFallBack fallBack;
	

	public RabbitMQSender(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}



	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		// TODO Auto-generated method stub
		if(ack) {
			fallBack.onSuccess();
		}else {
			fallBack.onFaild();
		}
	}



	@Override
	public void send(String key, Object message, SendFallBack fallBack) {
		// TODO Auto-generated method stub
		this.fallBack = fallBack;
		 CorrelationData correlationData = new CorrelationData(message.toString());
	}



	

}
