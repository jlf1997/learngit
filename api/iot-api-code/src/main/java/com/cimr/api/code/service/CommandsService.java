package com.cimr.api.code.service;

import java.util.concurrent.ExecutorService;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.cimr.api.code.config.CodeProperties;
import com.cimr.api.code.model.base.CodeHistory;
import com.cimr.api.code.po.CodeSenderObject;
import com.cimr.api.code.sender.KafkaSender;
import com.cimr.api.code.sender.MQCallBack;
import com.cimr.api.code.sender.MessageSender;
import com.cimr.api.code.sender.RabbitMQSender;
import com.cimr.boot.model.BaseModel;
import com.cimr.boot.utils.LogsUtil;

@Service
public class CommandsService {

	

	@Autowired
	private CallBackLogService callBackLogService;

	
	
	
	@Autowired
	private KafkaTemplate kafkaTemplate;
	
	@Autowired
    private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private CodeProperties codeProperties;

	
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
