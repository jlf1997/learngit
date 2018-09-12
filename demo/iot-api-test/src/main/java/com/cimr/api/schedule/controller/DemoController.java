package com.cimr.api.schedule.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cimr.api.schedule.task.HistoryCallServiceTask;

@RestController
@RequestMapping("/demo")
public class DemoController {

	@Autowired
	private HistoryCallServiceTask callServiceTask;
	
	@GetMapping("/test")
	public void test() {
		 callServiceTask.findDevInfoById();
	}
}
