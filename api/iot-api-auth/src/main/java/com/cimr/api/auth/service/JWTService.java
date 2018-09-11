package com.cimr.api.auth.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.cimr.api.token.JwtTokenFactory;

@Service
public class JWTService {

	
	@Autowired
	private JwtTokenFactory jwtTokenFactory;
	
	/**
	 * 根据map 生成对应token
	 * @param map
	 * @return
	 */
	public String genJwtToken(Map<String,Object> map) {
		Assert.notEmpty(map,"Map must contain entries");
		String jwtToken = jwtTokenFactory.createJavaWebTokenRS256(map);
		return jwtToken;
	}
	
	public Map check(String token) {
		return  jwtTokenFactory.parserJavaWebToken(token);
	}
}
