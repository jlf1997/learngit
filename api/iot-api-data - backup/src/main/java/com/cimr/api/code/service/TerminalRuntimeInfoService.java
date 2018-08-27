package com.cimr.api.code.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimr.api.code.dao.TerminalRuntimeInfoDao;
import com.cimr.api.comm.model.TerimalModel;

@Service
public class TerminalRuntimeInfoService {
	
	@Autowired
	private TerminalRuntimeInfoDao terminalRuntimeInfoDao;
	
	/**
	 * 查询redis 中RuntimeInfo数据，统计其中的boolean 类型数据
	 * @param termimals
	 * @param includeType
	 * @param fields
	 * @param countIncludeType
	 * @param countFields
	 * @return
	 */
	public List<Map<String, Object>> getDataBoolean(List<TerimalModel> termimals, String includeType,
			String[] fields, String countIncludeType, String[] countFields) {
		return terminalRuntimeInfoDao.getDateBoolean(termimals, includeType, fields,countIncludeType,countFields);
	}
	
	/**
	 * 查询redis中RuntimeInfo数据
	 * @param termimals
	 * @param includeType
	 * @param fields
	 * @return
	 */
	public List<Map<String, Object>> getData(List<TerimalModel> termimals, String includeType,
			String[] fields) {
		return terminalRuntimeInfoDao.getData(termimals, includeType, fields);
	} 
	  
}
