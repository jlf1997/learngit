package com.cimr.api.code.sender;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Component
public class MessageSenderBeanSetting {

	@Autowired
	private KafkaTemplate kafkaTemplate;
	
	@Autowired
    private RabbitTemplate rabbitTemplate;
	
	@Bean("kafkaSender")
	@Qualifier
	public MessageSender getkafkaSender() {
		return new KafkaSender(kafkaTemplate);
	}
	
	@Bean("RabbitSender")
	public MessageSender getRabbitMQSenderSender() {
		return new RabbitMQSender(rabbitTemplate);
	}
}
