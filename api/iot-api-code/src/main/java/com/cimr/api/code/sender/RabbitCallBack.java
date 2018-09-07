package com.cimr.api.code.sender;

import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitCallBack implements ConfirmCallback{
	
	
	
	@Autowired
	private MQCallBack mqCallBack;
	
	public RabbitCallBack(){
	}

	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		String id = correlationData.getId();
		Long resId = Long.parseLong(id);
		if(ack) {
			mqCallBack.sendMessageSuccess(resId);
		}else {
			mqCallBack.senMessageFaild(resId, cause);
		}
	}

}
