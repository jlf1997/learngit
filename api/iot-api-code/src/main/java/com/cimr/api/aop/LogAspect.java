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
import com.cimr.api.comm.model.HttpResult;
import com.cimr.boot.utils.LogsUtil;

@Aspect  
@Component  
public class LogAspect {
	
	
	private static final Logger logs = LoggerFactory.getLogger(LogAspect.class);

	
	@Autowired
	private ApiAccessLogService apiAccessLogService;
	
	 @Pointcut("execution(public * com.cimr.api.*.controller.*.*(..))")  
	    public void webLog(){}  
	  
//	    @Before("webLog()")  
//	    public void deBefore(JoinPoint joinPoint) throws Throwable {  
//	        // 接收到请求，记录请求内容  
//	        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();  
//	        HttpServletRequest request = attributes.getRequest();  
//	        // 记录下请求内容  
//	        System.out.println("URL : " + request.getRequestURL().toString());  
//	        System.out.println("HTTP_METHOD : " + request.getMethod());  
//	        System.out.println("IP : " + request.getRemoteAddr());  
//	        System.out.println("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());  
//	        System.out.println("ARGS : " + Arrays.toString(joinPoint.getArgs()));  
//	  
//	    }  
//	  
//	    @AfterReturning(returning = "ret", pointcut = "webLog()")  
//	    public void doAfterReturning(Object ret) throws Throwable {  
//	        // 处理完请求，返回内容  
//	        System.out.println("方法的返回值 : " + ret);  
//	    }  
//	  
//	    //后置异常通知  
//	    @AfterThrowing("webLog()")  
//	    public void throwss(JoinPoint jp){  
//	        System.out.println("方法异常时执行.....");  
//	    }  
//	  
//	    //后置最终通知,final增强，不管是抛出异常或者正常退出都会执行  
//	    @After("webLog()")  
//	    public void after(JoinPoint jp){  
//	        System.out.println("方法最后执行.....");  
//	    }  
	  
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
	        }  
	        return c;  
	    }  
}
