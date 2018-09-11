package com.cimr.boot.aop;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cimr.boot.comm.model.HttpResult;
import com.cimr.boot.threads.BootThread;
import com.cimr.boot.utils.GsonUtil;
import com.cimr.boot.utils.LogsUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class AbstractLogAspect {
	
	private static final Logger logs = LoggerFactory.getLogger(AbstractLogAspect.class);

	
	/**
	 * 记录日志
	 * @param level 日志级别
	 * @param log 日志对象
	 */
	public abstract void saveLog(final String level,ApiAccessLog log);
	
	private  static final  ExecutorService executorService = Executors.newFixedThreadPool(10);
	/**
	 * 环绕aop处理
	 * @param pjp
	 * @return
	 */
	 public Object arround(ProceedingJoinPoint pjp) { 
		 	String level = "info";
	    	ApiAccessLog log = new ApiAccessLog();
	    	log.setApiName(pjp.getSignature().getName());
	    	 HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	         //请求url
	         String url = request.getRequestURI();
	         log.setUrl(url);
	         String cs =request.getQueryString();
	        
	         log.setQueryString(cs);
	    	Object c=null;
	    	long b = new Date().getTime();
	    	//获取真实执行时间
	        try {  
	        	c = pjp.proceed();  
	        	HttpResult httpResult = (HttpResult)c;
	            log.setRes(true);
	            if(!httpResult.isSuccess()) {
	        		level = "error";
	        		log.setResult(GsonUtil.objToJson(c));
	        		log.setRes(false);
	        	}
	        } catch (Throwable e) {  
	        	String cause = LogsUtil.getStackTrace(e);
	            log.setCauses(cause);
	            log.setRes(false);
		        c = new HttpResult(false,"发生异常");
		        level = "error";
	        }  
	        long end = new Date().getTime();
	        log.setTime(end-b);
	        executorService.execute(new BootThread(level,log) {
	        	
	        
	        	
				@Override
				public void run() {
					try {
						saveLog(this.levl,this.log);
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
	        	
	        });
	       
	        return c;  
	    }  
}
