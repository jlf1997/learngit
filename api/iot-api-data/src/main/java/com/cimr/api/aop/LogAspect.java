package com.cimr.api.aop;

import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cimr.api.aop.model.base.ApiAccessLog;
import com.cimr.api.aop.service.ApiAccessLogService;
import com.cimr.boot.comm.model.HttpResult;
import com.cimr.boot.utils.LogsUtil;

@Aspect  
@Component  
public class LogAspect {
	
	
	private static final Logger logs = LoggerFactory.getLogger(LogAspect.class);

	
	@Autowired
	private ApiAccessLogService apiAccessLogService;
	
	 @Pointcut("execution(public * com.cimr.api.*.controller.*.*(..))")  
	    public void webLog(){}  
	  
	  
	    //环绕通知,环绕增强，相当于MethodInterceptor  
	    @Around("webLog()")  
	    public Object arround(ProceedingJoinPoint pjp) {  
	    	ApiAccessLog log = new ApiAccessLog();
	    	log.setApiName(pjp.getSignature().getName());
	    	Object c=null;
	    	long b = new Date().getTime();
	    	//获取真实执行时间
	        try {  
	        	
	        	c = pjp.proceed();  
	            log.setRes(true);
	        } catch (Throwable e) {  
	            log.setCauses(LogsUtil.getStackTrace(e));
	            log.setRes(false);
	            long end = new Date().getTime();
		        log.setTime(end-b);
		        apiAccessLogService.save(log);
		        HttpResult res = new HttpResult(false,"发生异常");
		        return res;
	        }  
	        return c;  
	    }  
}
