package com.cimr.api.code.reciver;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
public class DemoReciver {

	
	@KafkaListener(topics="SYS_MANAGE_CENTER")
	private void listener(String message) {
		System.out.println("getMessge:"+message);
	}
	
	
	
}
