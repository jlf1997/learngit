package com.cimr.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {
	
	@Value("${server.port}")
	private String port;

	@GetMapping("/test")
	public String demo() {
		return "test"+port;
	}
}
