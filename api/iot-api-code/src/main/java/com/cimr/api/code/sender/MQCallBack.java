package com.cimr.api.code.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.cimr.api.code.model.base.CallBackLog;
import com.cimr.api.code.model.base.CodeHistory;
import com.cimr.api.code.po.CodeResultNotiyObject;
import com.cimr.api.code.po.CodeSenderObject;
import com.cimr.api.code.service.CallBackLogService;
import com.cimr.api.code.service.CodeHistoryService;
import com.cimr.boot.utils.LogsUtil;

@Component
public class MQCallBack {
	
	
	private static final Logger log = LoggerFactory.getLogger(MQCallBack.class);

	
	@Autowired
	private CodeHistoryService codeHistoryService;
	
	@Autowired
	private CallBackLogService callBackLogService;
	
	@Autowired
	private RestTemplate restTemplate;

	public void sendMessageSuccess(Long resId) {
		 CodeResultNotiyObject codeResult = new CodeResultNotiyObject();
//        codeResult.setCodeId(codeSenderObject.getCodeId());
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
        codeResult.setCodeId(t.getCodeId());
        notiyCodeResutl(t.getUrl(),codeResult);
	}
	
	public void senMessageFaild(Long resId,String cause) {
		 CodeResultNotiyObject codeResult = new CodeResultNotiyObject();
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
        codeResult.setCodeId(t.getCodeId());
        codeResult.setCodeHistory(t);
        notiyCodeResutl(t.getUrl(),codeResult);
	}
	
	
	
	/**
	 * 通知指令发送结果
	 */
	private void notiyCodeResutl(String url,CodeResultNotiyObject result) {
		HttpHeaders headers = new HttpHeaders();
//       headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		log.info("回调地址url:"+url);
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
       	if(rss.getStatusCode()==HttpStatus.OK) {
           	if("success".equals(rss.getBody())){
           		callBackLog.setHttpCode(200);
           	}else {
           		callBackLog.setHttpCode(-1);
           	}
           }else {
           	callBackLog.setHttpCode(rss.getStatusCode().value());
           }
       }catch(Exception e) {
       	String cause = LogsUtil.getStackTrace(e);
			log.error("回调发送错误："+cause);
       	callBackLog.setCause(cause);
       }
       callBackLogService.save(callBackLog);
       
       
	}
}
