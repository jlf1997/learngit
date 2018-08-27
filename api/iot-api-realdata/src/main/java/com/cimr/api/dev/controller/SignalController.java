package com.cimr.api.dev.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@Api(description="设备相关操作",tags= {"signal"})
@RestController
@RequestMapping("/dev/signal")
public class SignalController {
//	@Autowired
//	private SignalService signalService;
	
	
//	@ApiOperation(value = "根据projectId查询信号信息", notes = ""			
//			)	  
//	@ApiImplicitParams({ 
//		@ApiImplicitParam(paramType = "path", dataType = "String", name = "projectId", value = "信息id", required = true) }
//	) 
//	@RequestMapping(value="/info/{projectId}",method=RequestMethod.GET)
//	public List<Signal> findDevInfoById(@PathVariable(value = "projectId") String projectId) {
//		Signal t = new Signal();
//		t.setProjectId(projectId);
//		List<Signal> res = signalService.findAll(t);
//		return res;
//	}
}
