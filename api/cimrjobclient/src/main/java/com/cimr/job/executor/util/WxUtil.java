package com.cimr.job.executor.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

public class WxUtil {

	private static String alarmUrl = "http://127.0.0.1:8053/wx/sendJobAlarmTemplateMsg";
	private static Logger logger = LoggerFactory.getLogger(WxUtil.class);
	
	public static String sendAlarm(String param){
		String resultString = "";
		//ResponseEntity<Object> result = cimrApi.getResponseEntityFromCIMR(HttpMethod.GET, "/api/realdata/latest_data/app/0/all/P00001", json);
		return resultString;
	}
	
	
	public static String sendAlarm(Map<String, String> param) {
		// 创建Httpclient对象
		
		JSONObject jsonObject = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String resultString = "";
		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(alarmUrl);
			// 创建参数列表
			if (param != null) {
				List<NameValuePair> paramList = new ArrayList<>();
				for (String key : param.keySet()) {
					paramList.add(new BasicNameValuePair(key, param.get(key)));
				}
				// 模拟表单
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, "utf-8");
				httpPost.setEntity(entity);
			}
			// 执行http请求
			response = httpClient.execute(httpPost);
			resultString = EntityUtils.toString(response.getEntity(), "utf-8");
			jsonObject = JSONObject.fromObject(resultString.toString());
			resultString = jsonObject.getString("success");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
		return resultString;
	}

}
