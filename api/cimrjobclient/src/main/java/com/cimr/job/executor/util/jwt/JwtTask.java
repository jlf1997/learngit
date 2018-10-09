package com.cimr.job.executor.util.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import net.sf.json.JSONObject;

@Component
public class JwtTask implements ApplicationListener<ContextRefreshedEvent>{

	private static final String appid = "CIMR478510519200784384";
	
	private static final String appKey = "uw5Peo9iaG253DpFN4uI4zw2Nl0dN4";
	
	
	@Autowired
	private CIMRApi cimrApi;
	/**
	 * 从jwt服务器获取token
	 */
	public void getToken() {
		String url = "/auth/validate/token?appid="+appid+"&appSecret="+appKey;
		String r = cimrApi.getObjectFromCIMR(HttpMethod.GET,url,null,null,String.class);
		if(r!=null) {
			JSONObject obj= JSONObject.fromObject(r);
			if(obj.has("data")) {
				CIMRServer.token = obj.getString("data");
				System.out.println("get the token:"+CIMRServer.token);
			}
			
		}
		
	}


	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// TODO Auto-generated method stub
		getToken();
	}
}
