package com.cimr.api.auth.controller;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cimr.api.auth.service.AuthenticationModelService;
import com.cimr.boot.comm.model.HttpResult;


/**
 * 生成appkey appid appsecrect
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/gen")
public class GenKeyController {
	
	@Autowired
	public AuthenticationModelService authenticationModelService;

	@GetMapping("/key")
	public HttpResult genKey() {
		
		try {
			HttpResult res =  authenticationModelService.genKey();
			return res;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			HttpResult res = new HttpResult(false,"");
			e.printStackTrace();
			return res;
		}
	}
}
