package com.cimr.comm.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
@Component
public class JwtSchedule {
	
	

	@Autowired
	private JwtTask jwtTask;
	
	@Scheduled(cron = "0 0/20 * * * ? ") // 间隔20分钟执行
    public void taskCycle() {
        System.out.println("使用SpringMVC框架配置定时任务");
        jwtTask.getToken();
        System.out.println("token:"+CIMRServer.token);
    }

}
