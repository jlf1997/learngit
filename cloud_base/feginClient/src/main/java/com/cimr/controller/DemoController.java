package com.cimr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cimr.remote.ClientRemote;

@RestController
public class DemoController {
	
	@Autowired
	private ClientRemote clientRemote;
	
	
	@RequestMapping("/demo")
	public String on() {
		return clientRemote.helloClient();
	}

}
