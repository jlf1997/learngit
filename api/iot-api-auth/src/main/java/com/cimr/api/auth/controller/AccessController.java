package com.cimr.api.auth.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cimr.api.auth.model.AuthenticationModel;
import com.cimr.api.auth.service.AuthenticationModelService;
import com.cimr.api.auth.service.JWTService;
import com.cimr.boot.comm.model.HttpResult;

@RestController
@RequestMapping("/validate")
public class AccessController {
	
	@Autowired
	private AuthenticationModelService authenticationModelService;
	@Autowired
	private JWTService jwtService;

	@GetMapping("/token")
	public HttpResult getAccessToken(
			@RequestParam(required=false) String appKey,
			@RequestParam(required=true) String appSecret,
			@RequestParam(required=true) String appid) {
		//验证appi 与 appSecret ；appkey暂时不使用 
		AuthenticationModel t = new AuthenticationModel();
		t.setAppid(appid);
		t.setAppSecret(appSecret);
		t = authenticationModelService.find(t);
		HttpResult result = new HttpResult();
		if(t==null) {
			result.setSuccess(false);
			result.setCode(1001);
			return result;
		}
		//生成token
		Map<String, Object> map = new HashMap<>();
		map.put("appid", appid);
		String token = jwtService.genJwtToken(map);
		result.setData(token);
		result.setSuccess(true);
		return result;
	}
	
	@GetMapping("/check")
	public Map parseToken(@RequestParam(required=false) String token) {
		return jwtService.check(token);
	}
	
}
