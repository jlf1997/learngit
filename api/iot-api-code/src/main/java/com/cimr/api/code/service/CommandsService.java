package com.cimr.api.code.service;

import java.util.concurrent.ExecutorService;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.cimr.api.code.config.CodeProperties;
import com.cimr.api.code.dao.CommandsDao;
import com.cimr.api.code.model.base.CallBackLog;
import com.cimr.api.code.model.base.CodeHistory;
import com.cimr.api.code.model.mgr.Commands;
import com.cimr.api.code.po.CodeResultNotiyObject;
import com.cimr.api.code.po.CodeSenderObject;
import com.cimr.api.code.sender.KafkaSender;
import com.cimr.api.code.sender.MessageSender;
import com.cimr.api.code.sender.RabbitMQSender;
import com.cimr.api.code.sender.SendFallBack;
import com.cimr.boot.model.BaseModel;
import com.cimr.boot.utils.GsonUtil;

@Service
public class CommandsService {

	
	@Autowired
	private CommandsDao commandsDao;
	@Autowired
	private CallBackLogService callBackLogService;

	
	@Autowired
	private KafkaTemplate kafkaTemplate;
	
	@Autowired
    private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private CodeProperties codeProperties;
	@Autowired
	private RestTemplate restTemplate;
	
//	@Resource(name="RabbitSender")
	private MessageSender messageSender;
	
	@Autowired
	private CodeHistoryService codeHistoryService;
	
	@Resource(name="executorServiceForSendCodeByKafka")
	private ExecutorService executorService;
	
	@Autowired
	private CommandsService commandsService;
	
	private static final Logger log = LoggerFactory.getLogger(CommandsService.class);

	
	
	/**
	 * 根据指令id获取指令内容 指令内容必须可查
	 * @param id
	 * @return
	 */
	public String getCommandsById(String id) {
		Commands t = new Commands();
		t.setId(id);
		t.setAppLicense(codeProperties.getAppLicenseCode());
		Commands res = commandsDao.find(t);
		if(res!=null) {
			return res.getContents();
		}		
		return null;
		
	}
	
	public Long saveHistory(String mqType,String uri,String topicKey,String messageJson, CodeSenderObject codeSenderObject) {
		CodeHistory codeHistory  = new CodeHistory();
		codeHistory.setId(BaseModel.genId());
		codeHistory.setStatus(0);
		codeHistory.setMessageJson(messageJson);
		codeHistory.setUrl(codeSenderObject.getNotify_url());
		codeHistory.setCodeId(codeSenderObject.getCodeId());
		codeHistory.setTopicKey(topicKey);
		codeHistory.setMqURI(uri);
		codeHistory.setMqType(mqType);
		codeHistoryService.save(codeHistory);
		return  codeHistory.getId();
	}
	
	/**
	 * 通过kafka向中终端发送指令,并在发送结束后，反馈结果
	 * @param message
	 */
	public Long sendCodeToTerminalByKafka(String message,CodeSenderObject codeSenderObject) {
		
		if(codeProperties.getSenderType()==1) {
			messageSender =new RabbitMQSender(rabbitTemplate);
		}else {
			messageSender = new KafkaSender(kafkaTemplate);
		}
		String mqtype = "";
		if(messageSender instanceof KafkaSender) {
			mqtype = "kafka";
		}
		if(messageSender instanceof RabbitMQSender) {
			mqtype = "rabbit";
		}
		Long resId = commandsService.saveHistory(mqtype,"",codeProperties.getTopicAppToTer(),message,codeSenderObject);
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				
				SendFallBack fallback = new SendFallBack() {
					
					@Override
					public void onSuccess() {
						sendMessageSuccess(resId,codeSenderObject);
					}

					@Override
					public void onFaild(String cause) {
						senMessageFaild(resId, codeSenderObject, cause);
					}
					
				};
				try {
					messageSender.send(codeProperties.getTopicAppToTer(),message,fallback);
				}catch(Exception e) {
					senMessageFaild(resId, codeSenderObject, e.getMessage());
				}
				
			}
			
		});
		return resId;
	}
	
	
	private void sendMessageSuccess(Long resId,CodeSenderObject codeSenderObject) {
		 CodeResultNotiyObject codeResult = new CodeResultNotiyObject();
         codeResult.setCodeId(codeSenderObject.getCodeId());
         codeResult.setReturn_code("SUCCESS");
        
         codeResult.setStatus(1);
         CodeHistory t = new CodeHistory();
         t.setId(resId);
         t = codeHistoryService.find(t);
         if(t!=null) {
        	 t.setStatus(1);
             codeHistoryService.save(t);
         }
         codeResult.setCodeHistory(t);
         notiyCodeResutl(codeSenderObject.getNotify_url(),codeResult);
	}
	
	private void senMessageFaild(Long resId,CodeSenderObject codeSenderObject,String cause) {
		 CodeResultNotiyObject codeResult = new CodeResultNotiyObject();
         codeResult.setCodeId(codeSenderObject.getCodeId());
         codeResult.setReturn_code("FAILD");
         codeResult.setReturn_message("发送失败:"+cause);
         codeResult.setStatus(-1);
         CodeHistory t = new CodeHistory();
         t.setId(resId);
         t = codeHistoryService.find(t);
         if(t!=null) {
        	 t.setStatus(-1);
        	 t.setCause(cause);
             codeHistoryService.save(t);
         }
         codeResult.setCodeHistory(t);
         notiyCodeResutl(codeSenderObject.getNotify_url(),codeResult);
	}
	
	
	
	/**
	 * 通知指令发送结果
	 */
	public void notiyCodeResutl(String url,CodeResultNotiyObject result) {
		HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		log.info("url:"+url);
		MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("return_code", result.getReturn_code());
        params.add("return_message", result.getReturn_message());
        params.add("codeId", result.getCodeId());
        params.add("sendStauts",result.getStatus()+"");
        HttpEntity entity = new HttpEntity( params,headers);
        ResponseEntity<String> rss =null;
        CallBackLog callBackLog = new CallBackLog();
        callBackLog.setCodeHistory(result.getCodeHistory());
        try {
        	rss =  restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        }catch(Exception e) {
        	log.error("connection faild :"+e.getMessage());
        	callBackLog.setCause(e.getMessage());
        }
        
        if(rss.getStatusCode()==HttpStatus.OK) {
        	if("success".equals(rss.getBody())){
        		callBackLog.setHttpCode(200);
        		log.info("success");
        	}else {
        		callBackLog.setHttpCode(-1);
        		log.error("error");
        	}
        }else {
        	callBackLog.setHttpCode(rss.getStatusCode().value());
        	log.error("error");
        }
        
        callBackLogService.save(callBackLog);
        
        
	}
	
	
	
	
}
