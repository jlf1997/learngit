package com.cimr.api.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cimr.boot.aop.DefaultLogAspect;

@Aspect  
@Component  
public class LogAspect {
	
	
	private static final Logger logs = LoggerFactory.getLogger(LogAspect.class);

	

	
	 @Pointcut("execution(public * com.cimr.api.*.controller.*.*(..))")  
	    public void webLog(){}  
	  
	  
	    //环绕通知,环绕增强，相当于MethodInterceptor  
	    @Around("webLog()")  
	    public Object arround(ProceedingJoinPoint pjp) { 
	    	DefaultLogAspect defaultLogAspect = new DefaultLogAspect();
	    	return defaultLogAspect.arround(pjp);
	    }


		
}
