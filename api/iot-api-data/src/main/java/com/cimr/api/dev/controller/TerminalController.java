package com.cimr.api.dev.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cimr.api.comm.model.TerimalModel;
import com.cimr.api.dev.model.mgr.Terminal;
import com.cimr.api.dev.service.TerminalService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


/**
 * 设备相关操作
 * @author Administrator
 *
 */

@Api(description="项目信号相关操作",tags= {"terminal"})
@RestController
@RequestMapping("/dev")
public class TerminalController {
	
	@Autowired
	private TerminalService terminalService;
	

	@ApiOperation(value = "根据id查询单台设备信息", notes = ""			
			)	  
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "path", dataType = "String", name = "terminalId", value = "信息id", required = true) }
	) 
	@RequestMapping(value="/info/{terminalId}",method=RequestMethod.GET)
	public Terminal findDevInfoById(@PathVariable(value = "terminalId") String terminalId) {
		Terminal t = new Terminal();
		t.setId(terminalId);
		Terminal res = terminalService.find(t);
		return res;
	}
	
	
	
	
	
	@ApiOperation(value = "根据id串查询设备信息", notes = ""			
			)	  
	@RequestMapping(value="/infos/ids",method=RequestMethod.POST)	
	public List<Terminal> findDevInfoByIds(	
			@RequestBody List<TerimalModel> termimals
			) {
		List<String> ids = new ArrayList<>();
		termimals.forEach(action->{
			ids.add(action.getTerId());
		});
		return terminalService.findDevsByIds(ids);
	}
	
	
	
	
	@ApiOperation(value = "条件查询设备信息", notes = "支持 设备id 终端编号 项目编号 租户编号查询"			
			)
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "query", dataType = "String", name = "terminalId", value = "设备id", required = false),
		@ApiImplicitParam(paramType = "query", dataType = "String", name = "tenantNo", value = "租户编号", required = false),
		@ApiImplicitParam(paramType = "query", dataType = "String", name = "terminalName", value = "设备名称", required = false),
		@ApiImplicitParam(paramType = "query", dataType = "String", name = "projectNo", value = "项目编号", required = false)
		}) 
	@RequestMapping(value="/infos",method=RequestMethod.GET)	
	public List<Terminal> findDevInfos(
			@RequestParam(name="terminalId",required=false) String terminalId,
			@RequestParam(name="tenantNo",required=false) String tenantNo,
			@RequestParam(name="terminalName",required=false) String terminalName,
			@RequestParam(name="projectNo",required=false) String projectNo
			) {
		Terminal t = new Terminal();
		t.setProjectNo(projectNo);
		t.setTenantNo(tenantNo);
		t.setId(terminalId);
		t.setTerminalName(terminalName);
		List<Terminal> res = terminalService.findAll(t);
		return res;
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
}
