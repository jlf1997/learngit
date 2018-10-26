package com.cimr.sysmanage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimr.sysmanage.dao.CommonDao;
import com.cimr.sysmanage.service.CommonService;

@Service("commonService")
public class CommonServiceImpl implements CommonService{

	@Autowired
	private CommonDao commonDao;
	
	
	@Override
	public String getId(String tableName, String idColName,String idRule) {
		String currentMaxId = this.commonDao.getMaxId(idColName, tableName);
		return this.getNextId(tableName,currentMaxId,idRule);
		}

	/*
	 * 获取新纪录的id
	 * 1： 获取最大id
	 * 2：根据id规则("TEL%04d")分离数字
	 * 3： 数字+1
	 * 4：根据id规则拼凑最新id
	 */
	private String getNextId(String tableName,String currentMaxId,String idRule) {
		//截取id数字，进行+1操作
		int ruleLen = idRule.split("%")[0].length();
		int nextNo =1;
		if(!currentMaxId.equals("0")){ //不是第一条记录
			 nextNo = Integer.parseInt(currentMaxId.substring(ruleLen)) + 1;	
			}
		return String.format(idRule, nextNo); 				
  	}
 
}