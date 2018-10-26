package com.cimr.master.comm.aop;

import com.cimr.comm.aop.model.AccessLogEntity;
import com.cimr.comm.enums.SysEnums;
import com.cimr.sysmanage.model.User;
import com.cimr.util.LogUtil;
import com.cimr.util.UserUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;


@Component
@Aspect
public class AccessLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(AccessLogAspect.class);

    //配置接入点
    @Pointcut("execution(* com.cimr.*.controller..*.*(..))")
    private void controllerAspect(){}//定义一个切入点

    @Pointcut("execution(* com.cimr.*.controller..*.*(..))")
    private void appControllerAspect(){}//定义一个切入点



//    @Pointcut("@annotation(com.cimr.comm.aop.AccessLog)")
//   	protected void anyMethod() {
//   	}


    @Around("appControllerAspect()")
    public Object appAround(ProceedingJoinPoint pjp) throws Throwable{
    	return around(pjp);
    }



    @Around("controllerAspect()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
//    	System.out.println("==============================a");
        //常见日志实体对象
    	AccessLogEntity log = new AccessLogEntity();
        //获取登录用户账户
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        String name = (String) request.getSession().getAttribute("USER_ID");
        String path = request.getServletPath();
        log.setPath(path);
        User user = UserUtil.getLoginUser();
        if(user!=null) {
        	 log.setUserId(user.getId());
        }
       
        //获取系统时间
//        String time = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date());
        log.setAccessTime(new Date());
        
        //获取系统ip,这里用的是我自己的工具类,可自行网上查询获取ip方法
        String ip = getIpAddr(request);
        log.setIp(ip);
        
       //方法通知前获取时间,为什么要记录这个时间呢？当然是用来计算模块执行时间的
        long start = System.currentTimeMillis();
       // 拦截的实体类，就是当前正在执行的controller
       Object target = pjp.getTarget();
       // 拦截的方法名称。当前正在执行的方法
       String methodName = pjp.getSignature().getName();
       // 拦截的方法参数
       Object[] args = pjp.getArgs();
       // 拦截的放参数类型
       Signature sig = pjp.getSignature();
       MethodSignature msig = null;
       if (!(sig instanceof MethodSignature)) {
           throw new IllegalArgumentException("该注解只能用于方法");
       }
       msig = (MethodSignature) sig;
       Class[] parameterTypes = msig.getMethod().getParameterTypes();
       
       Object object = null;
       // 获得被拦截的方法
       Method method = null;
       try {
           method = target.getClass().getMethod(methodName, parameterTypes);
       } catch (NoSuchMethodException e1) {
           LogUtil.printStackTraceToLog(request,e1, logger, SysEnums.HTTP_500,null);
       } catch (SecurityException e1) {
           LogUtil.printStackTraceToLog(request,e1, logger, SysEnums.HTTP_500,null);
       }
       if (null != method) {
           // 判断是否包含自定义的注解，说明一下这里的SystemLog就是我自己自定义的注解
           if (method.isAnnotationPresent(AccessLog.class)) {
        	   AccessLog accessLog = method.getAnnotation(AccessLog.class);
               log.setModule(accessLog.module());
               log.setMethod(accessLog.methods());
           }
       }
       try {
           object = pjp.proceed();
           log.setCommit("执行成功！");
           long end = System.currentTimeMillis();
           log.setResponTime(""+(end-start));
       } catch (Throwable e) {
           // TODO Auto-generated catch block
           log.setCommit("执行失败");
           long end = System.currentTimeMillis();
           log.setResponTime(""+(end-start));
           LogUtil.printStackTraceToLog(request,e, logger, SysEnums.HTTP_500,null);
       }
       return object;
    }
    
    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");  
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("Proxy-Client-IP");  
        }  
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getRemoteAddr();  
        }  
        return ip;  
    }  


}
