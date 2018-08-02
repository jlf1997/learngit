package com.cloud.subsystem.app.service.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.cloud.subsystem.app.model.PushModel;
import com.cloud.subsystem.app.properties.GetuiProperties;
import com.cloud.subsystem.app.properties.KeyConstants;
import com.cloud.subsystem.app.service.AppPushService;
import com.cloud.subsystem.app.util.GeiTuiUtil;
import com.cloud.subsystem.app.util.HttpResult;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.ITemplate;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;


/**
 * 个推推送实现类
 * @author Administrator
 *
 */
@Service
public class AppPushServiceImpl implements AppPushService{
	
	
	private static final Logger log = LoggerFactory.getLogger(AppPushServiceImpl.class);
	
	/**
	 * 单次最大推送数
	 */
	private static final int PUSH_COUNT_EACH = 100;

	
	@Autowired
	private IGtPush push;
	
	@Autowired
	private GetuiProperties getuiProperties;
	
	@Resource(name="executorServiceForPushApp")
	private ExecutorService executorService;

	@Override
	public HttpResult pushToAliasList(List<String> aliasList,PushModel pushModel) {
		// TODO Auto-generated method stub
		List<Target> targets = new ArrayList<>();
		Target target;
		for(String alias:aliasList) {
			 target = new Target();
			 target.setAppId(KeyConstants.appid);
		     target.setAlias(alias);
		     if(targets.size()>=PUSH_COUNT_EACH) {
		    	 sendTask(pushModel,targets);
		    	 targets =  new ArrayList<>();
		     }else {
		    	 targets.add(target);
		     }
		}
		
		return null;
	}

	@Override
	public HttpResult pushToCidList(List<String> cids,PushModel pushModel) {
		// TODO Auto-generated method stub
		List<Target> targets = new ArrayList<>();
		Target target;
		for(String cid:cids) {
			 target = new Target();
			 target.setAppId(KeyConstants.appid);
		     target.setClientId(cid);
		     targets.add(target);
		     if(targets.size()>=PUSH_COUNT_EACH) {
		    	 //开始发送
		    	 sendTask(pushModel,targets);
		    	 targets =  new ArrayList<>();
		     }
		}
		//发送剩余的任务
		if(targets.size()>0) {
			// sendTask(pushModel,targets);
		}
		
		return null;
	}

	@Override
	public HttpResult CancelPush(List<String> taskids) {
		// TODO Auto-generated method stub
	
		return null;
	}
	
	
	
	/**
	 * 处理需要发送的任务列表，如去重等
	 */
	private void takeTaskList() {
		
	}
	
	
	/**
	 * 多线程发送任务
	 * @param title
	 * @param context
	 * @param targets
	 */
	private void sendTask(PushModel pushModel,List<Target> targets) {
		executorService.execute(new Runnable() {
 			@Override
 			public void run() {
 				// TODO Auto-generated method stub
 				push(pushModel,targets);
 			}
 			
 		});
	}
	
	
	/**
	 * 指定title contenx发送
	 * @param title
	 * @param context
	 * @param targets
	 */
	private void push(PushModel pushMode,List<Target> targets) {
		ITemplate template = pushMode.getTemplate();
		
		if(template==null) {
			log.error("pushMode 类型错误");
			return ;
		}
	   ListMessage message = GeiTuiUtil.getDefaultListMessage(template); 
	   push(targets,message);
	}
	
	
	
	/**
	 * 单次批量推送，调用此接口，建议targets大小为50到100，最大不可超过1000
	 * @param name
	 * @param title
	 * @param context
	 * @param type
	 */
	private void push(List<Target> targets,ListMessage message) {
	   Assert.notNull(targets, "推送目标不能为空");	     
	   IPushResult ret = null;
       // taskId用于在推送时去查找对应的message
       String taskId = push.getContentId(message);
       try {
           ret = push.pushMessageToList(taskId, targets);
       } catch (RequestException e) {
           e.printStackTrace();
          
       }
       if (ret != null) {
    	   log.info(ret.getResponse().toString());
       } else {
    	   log.error("服务器响应异常");
       }
	}

}
