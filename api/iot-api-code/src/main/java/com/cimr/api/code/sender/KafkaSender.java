package com.cimr.api.code.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.cimr.boot.utils.LogsUtil;

import io.jsonwebtoken.lang.Assert;


@Component
public class KafkaSender implements MessageSender{
	
	
	@Autowired
	private MQCallBack mqCallBack;
	
	
	private KafkaTemplate kafkaTemplate;
	
	
	private static final Logger log = LoggerFactory.getLogger(KafkaSender.class);
	
	private ListenableFutureCallback<SendResult<String, String>> callback;

	@Autowired
	public KafkaSender(KafkaTemplate kafkaTemplate) {
		
		this.kafkaTemplate = kafkaTemplate;
		
	}



	@SuppressWarnings("unchecked")
	@Override
	public void send(String id,String key, Object message) {
		
		ListenableFuture<SendResult<String, String>>  future = kafkaTemplate.send(key, message);
		Long resId = Long.parseLong(id);
		ListenableFutureCallback<SendResult<String, String>>  callback = new ListenableFutureCallback<SendResult<String, String>>() {

	          @Override
	          public void onSuccess(SendResult<String, String> result) {
//	              log.info("send success {}", result.getProducerRecord().value());
	              try {
	            	  mqCallBack.sendMessageSuccess(resId);
	              }catch(Exception e) {
	              	String cause = LogsUtil.getStackTrace(e);
	              	log.error("kafka回调异常："+cause);
	              }
	          }

	          @Override
	          public void onFailure(Throwable ex) {
	          	 try {
	          		 String cause = LogsUtil.getStackTrace(ex);
	          		 mqCallBack.senMessageFaild(resId, cause);
	               }catch(Exception e) {
	               	String cause = LogsUtil.getStackTrace(e);
	               	log.error("kafka回调异常："+cause);
	               }
	          }

	      };
		if(callback!=null) {
			 future.addCallback(callback);
		}
      
	}

	
		
	
	
	

}
