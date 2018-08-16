package com.cimr.api.code.service;

import java.util.concurrent.ExecutorService;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.cimr.api.code.config.CodeProperties;
import com.cimr.api.code.dao.CommandsDao;
import com.cimr.api.code.model.base.CodeHistory;
import com.cimr.api.code.model.mgr.Commands;
import com.cimr.api.code.po.CodeResultNotiyObject;
import com.cimr.api.code.po.CodeSenderObject;
import com.cimr.api.code.sender.KafkaSender;
import com.cimr.api.code.sender.MessageSender;
import com.cimr.api.code.sender.RabbitMQSender;
import com.cimr.api.code.sender.SendFallBack;
import com.cimr.boot.model.BaseModel;

@Service
public class CommandsService {

	
	@Autowired
	private CommandsDao commandsDao;
	
	
	
	@Autowired
	private CodeProperties codeProperties;
	@Autowired
	private RestTemplate restTemplate;
	
	@Resource(name="kafkaSender")
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
		
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				
				SendFallBack fallback = new SendFallBack() {

					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
						CodeResultNotiyObject codeResult = new CodeResultNotiyObject();
		                codeResult.setCodeId(codeSenderObject.getCodeId());
		                codeResult.setReturn_code("SUCCESS");
		                codeResult.setReturn_message("发送成功");
		                notiyCodeResutl(codeSenderObject.getNotify_url(),codeResult);
					}

					@Override
					public void onFaild() {
						// TODO Auto-generated method stub
			              CodeResultNotiyObject codeResult = new CodeResultNotiyObject();
			              codeResult.setCodeId(codeSenderObject.getCodeId());
			              codeResult.setReturn_code("FAILD");
			              codeResult.setReturn_message("发送失败");
			              notiyCodeResutl(codeSenderObject.getNotify_url(),codeResult);
					}
					
				};
				messageSender.send(codeProperties.getTopicAppToTer(),message,fallback);
//				messageSender.send("testTopic",message,fallback);
			}
			
		});
		String mqtype = "";
		if(messageSender instanceof KafkaSender) {
			mqtype = "kafka";
		}
		if(messageSender instanceof RabbitMQSender) {
			mqtype = "rabbit";
		}
		return commandsService.saveHistory(mqtype,"",codeProperties.getTopicAppToTer(),message,codeSenderObject);

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
        HttpEntity entity = new HttpEntity( params,headers);
        ResponseEntity<String> rss =null;
        try {
        	rss =  restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        }catch(Exception e) {
//        	e.printStackTrace();
        	log.error("connection faild :"+e.getMessage());
        	return ;
        }
        CodeHistory t = new CodeHistory();
        t.setCodeId(result.getCodeId());
        t = codeHistoryService.find(t);
        
        if(rss.getStatusCode()==HttpStatus.OK) {
        	if("success".equals(rss.getBody())){
        		t.setStatus(200);
        		
        		log.info("success");
        	}else {
        		t.setStatus(-1);
        		log.error("error");
        	}
        }else {
        	t.setStatus(rss.getStatusCode().value());
        	log.error("error");
        }
        codeHistoryService.save(t);
	}
	
	
	
	
}
