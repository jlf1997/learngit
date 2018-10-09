package com.cimr.job.executor.service.jobhandler;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.cimr.job.executor.core.config.HostConstant;
import com.cimr.job.executor.util.HttpClientUtil;
import com.cimr.job.executor.util.MailUtil;
import com.cimr.job.executor.util.jwt.CIMRApi;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;

/**
 * 任务Handler示例（Bean模式）
 *
 * 开发步骤：
 * 1、继承"IJobHandler"：“com.xxl.job.core.handler.IJobHandler”；
 * 2、注册到Spring容器：添加“@Component”注解，被Spring容器扫描为Bean实例；
 * 3、注册到执行器工厂：添加“@JobHandler(value="自定义jobhandler名称")”注解，注解value值对应的是调度中心新建任务的JobHandler属性的值。
 * 4、执行日志：需要通过 "XxlJobLogger.log" 打印执行日志；
 *
 * @author tuping 2018-09-4 19:43:36
 */
@JobHandler(value="weixinTokenMonitorJobHandler")
@Component
public class WeiXinTokenUrlHandler  extends IJobHandler {

	@Autowired
	private CIMRApi cimrApi;
	@Override
	public ReturnT<String> execute(String param) throws Exception {
		getWeiXinToken();
		getDeviceAlarm();
		return SUCCESS;
	}
	
	//检测获取weixin的token是否正常
	private void getWeiXinToken() throws Exception {
		String token = HttpClientUtil.getToken(HostConstant.WEIXINTOKEN, null);
		if("".equals(token)||token==null)
		{
			System.out.println("微信token获取不正常");
			MailUtil.sendMail("15669640@qq.com", "微信token获取不正常", "请尽快解决!");
		}
		else
		{
			System.out.println("微信token获取正常");
			MailUtil.sendMail("15669640@qq.com", "微信token获取正常", "请尽快解决!");
		}
	}
	/*
	 * 设备预警监控程序
	 */
	private void getDeviceAlarm() throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		ResponseEntity<Object> result = cimrApi.getResponseEntityFromCIMR(HttpMethod.GET, "/message/wx/sendDeviceAlarmTemplateMsg?openID=obdUWwdaea2Iwg9H4UnjiulvD9Hc&title=设备报警&deviceID=XLH-9LLKD&date="+df.format(new Date())+"&content=设备报警测试&address=长沙市&info=", null);
		System.out.println("获取设备预警结果："+result.getStatusCode());
		if("200".equals(result.getStatusCode()))
		{
			
		}
		else
		{
			MailUtil.sendMail("15669640@qq.com", "设备预警监控程序出现异常!", "请尽快解决!");
		}
	}
	
}
