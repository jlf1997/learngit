package com.cimr.api.code.sender;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;



public class KafkaSender implements MessageSender{
	
	public KafkaSender(KafkaTemplate kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	private KafkaTemplate kafkaTemplate;

	@SuppressWarnings("unchecked")
	@Override
	public void send(String key, Object message, SendFallBack fallBack) {
		// TODO Auto-generated method stub
		ListenableFuture<SendResult<String, String>>  future = kafkaTemplate.send(key, message);
		ListenableFutureCallback callback = new ListenableFutureCallback<SendResult<String, String>>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
//                log.info("send success {}", result.getProducerRecord().value());
                
            	fallBack.onSuccess();
            }

            @Override
            public void onFailure(Throwable ex) {
            	fallBack.onFaild(ex.getMessage());
            }

        };
        future.addCallback(callback);
	}
		
	
	
	

}
