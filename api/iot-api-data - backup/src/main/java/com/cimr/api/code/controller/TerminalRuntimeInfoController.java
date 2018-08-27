package com.cimr.api.code.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cimr.api.code.service.TerminalRuntimeInfoService;
import com.cimr.api.comm.model.TerimalModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(description="runtimeInfo相关操作",tags= {"runtimeInfo"})
@RestController
@RequestMapping("/runtimeInfo")
public class TerminalRuntimeInfoController {
	@Autowired
	private TerminalRuntimeInfoService terminalRuntimeInfoService;
	
	
	@ApiOperation(value = "获取终端runntimeinfo最新数据,统计其中boolean类型的数量",notes="countIncludeType与includeType默认为exclude "		
			)	
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "query", dataType = "string", name = "includeType", value = "查询字段类型：排除或包含", required = false,allowableValues="INCLUDE,EXCLUDE"),
		@ApiImplicitParam(paramType = "query", dataType = "string", name = "fields", value = "需要查询的字段或排除的字段", required = false,allowMultiple=true),
		@ApiImplicitParam(paramType = "query", dataType = "string", name = "countIncludeType", value = "统计字段类型：排除或包含", required = false,allowableValues="INCLUDE,EXCLUDE"),
		@ApiImplicitParam(paramType = "query", dataType = "string", name = "countFields", value = "需要统计的字段或排除的字段", required = false,allowMultiple=true)
		}) 
	@RequestMapping(value="/app/booleancount/all",method=RequestMethod.POST)
	public List<Map<String,Object>> getAllDataBoolean(
			@RequestBody List<TerimalModel> termimals,
			@RequestParam(name="includeType",required=false) String includeType,
			@RequestParam(name="fields",required=false) String[] fields,
			@RequestParam(name="countIncludeType",required=false) String countIncludeType,
			@RequestParam(name="countFields",required=false) String[] countFields) {
	
		return terminalRuntimeInfoService.getDataBoolean(termimals,includeType,fields,countIncludeType,countFields);
	}
	
	@ApiOperation(value = "获取全部runtimeinfo数据",notes="includeType默认为exclude "		
			)	
	@ApiImplicitParams({ 
		@ApiImplicitParam(paramType = "query", dataType = "string", name = "includeType", value = "查询字段类型：排除或包含", required = false,allowableValues="INCLUDE,EXCLUDE"),
		@ApiImplicitParam(paramType = "query", dataType = "string", name = "fields", value = "需要查询的字段或排除的字段", required = false,allowMultiple=true),
		@ApiImplicitParam(paramType = "query", dataType = "string", name = "countIncludeType", value = "统计字段类型：排除或包含", required = false,allowableValues="INCLUDE,EXCLUDE"),
		@ApiImplicitParam(paramType = "query", dataType = "string", name = "countFields", value = "需要统计的字段或排除的字段", required = false,allowMultiple=true)
		}) 
	@RequestMapping(value="/app/all",method=RequestMethod.POST)
	public List<Map<String,Object>> getData(
			@RequestBody List<TerimalModel> termimals,
			@RequestParam(name="includeType",required=false) String includeType,
			@RequestParam(name="fields",required=false) String[] fields
			) {
	
		return terminalRuntimeInfoService.getData(termimals,includeType,fields);
	}
}
