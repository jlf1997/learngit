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
import com.cimr.api.code.sender.MQCallBack;
import com.cimr.api.code.sender.MessageSender;
import com.cimr.api.code.sender.RabbitMQSender;
import com.cimr.api.code.sender.SendFallBack;
import com.cimr.boot.model.BaseModel;
import com.cimr.boot.utils.GsonUtil;
import com.cimr.boot.utils.LogsUtil;

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
	
	@Autowired
	private MQCallBack mqCallBack;
	

	
	
	@Autowired
	private RabbitMQSender rabbitMQSender;
	@Autowired
	private KafkaSender kafkaSender;
	
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
	 * 向中终端发送指令,并在发送结束后，反馈结果
	 * @param message
	 */
	public Long sendCodeToTerminal(String message,CodeSenderObject codeSenderObject) {
		String mqtype = "";
		if(codeProperties.getSenderType()==0) {
			mqtype = "kafka";
		}else {
			mqtype = "rabbit";
		}
		Long resId = commandsService.saveHistory(mqtype,"",codeProperties.getTopicAppToTer(),message,codeSenderObject);
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				MessageSender messageSender;
				if(codeProperties.getSenderType()==1) {
					messageSender =rabbitMQSender;
				}else {
					messageSender = kafkaSender;
				}
				try {
					messageSender.send(resId+"",codeProperties.getTopicAppToTer(),message);
				}catch(Exception e) {
					String cause = LogsUtil.getStackTrace(e);
					log.error("发送消息失败："+cause);
					mqCallBack.senMessageFaild(resId, cause);
				}
			}
		});
		return resId;
	}
	
	

	
	
	
	
}
