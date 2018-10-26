package com.cimr.master.comm.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.cimr.sysmanage.dto.HttpResult;

/**
 * 调用api工具类
 * @author Administrator
 *
 */

@Component
public class CIMRApi {

	
	@Autowired
	private RestTemplate restTemplate;
	
	
	
	
	/**
	 * 接口调用 
	 * @param url 地址
	 * @param body body信息 默认采用json格式
	 * @return 
	 */
	public ResponseEntity<Object> getResponseEntityFromCIMR(HttpMethod method,String url,String bodyJson) throws Exception{
		   HttpHeaders headers = new HttpHeaders();
		    MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
		    headers.setContentType(type);
			ResponseEntity<Object> result = getResponseEntityFromCIMR(method,url,bodyJson,headers, Object.class);
			return result;
	}
	
	
	
	/**
	 * 从api接口服务器获取数据，返回ResponseEntity
	 * 需要自己处理异常
	 * @param method
	 * @param url
	 * @param body
	 * @param requestHeaders
	 * @param t
	 * @return
	 */
	public <T> ResponseEntity<T> getResponseEntityFromCIMR(HttpMethod method,String url,Object body,HttpHeaders requestHeaders,Class<T> t) throws Exception{
		String server = "http://"+CIMRServer.server_ip+":"+CIMRServer.server_port+"/iot";
		if(requestHeaders ==null) {
			requestHeaders = new HttpHeaders(); 
		}
		requestHeaders.add("Authorization", CIMRServer.token);
		HttpEntity requestEntity = new HttpEntity(body, requestHeaders);
		return restTemplate.exchange(server+url, method, requestEntity, t);
	}
	
	/**
	 * 从api接口服务器直接获取数据，发生异常则返回null
	 * @param method
	 * @param url
	 * @param body
	 * @param requestHeaders
	 * @param t
	 * @return
	 */
	public <T> T getObjectFromCIMR(HttpMethod method,String url,Object body,HttpHeaders requestHeaders,Class<T> t) {
		ResponseEntity<T> res = null;
		try {
			res = getResponseEntityFromCIMR(method,url, body, requestHeaders,t);
			if(HttpStatus.OK.equals(res.getStatusCode())) {
				return  res.getBody();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
		
		
	}
	/**
	 * 从服务端请求数据 返回httpResult
	 * @param method http协议方法类型 
	 * @param url 请求url
	 * @param bodyJson body体 转为json形式
	 * @return httpResult 如果发生异常则返回null
	 * @throws Exception 
	 */
	public HttpResult getObjectFromCIMR(HttpMethod method,String url,String bodyJson) {
		 HttpHeaders headers = new HttpHeaders();
		    MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
		    headers.setContentType(type);
		    HttpResult result = getObjectFromCIMR(HttpMethod.POST,url,bodyJson,headers, HttpResult.class);
			return result;
	}
}
