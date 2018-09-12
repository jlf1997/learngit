package com.cimr.api.code.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimr.api.code.dao.TerRealDataDao;
import com.cimr.api.code.model.TerimalModel;


@SuppressWarnings("rawtypes")
@Service
public class RealTimeDateService {
	
	private static final Logger log = LoggerFactory.getLogger(RealTimeDateService.class);

	
	
	
	@Autowired
	private TerRealDataDao terRealDataDao;
	

	
	
	
	private static ReentrantLock lock = new ReentrantLock();
	
	

	
	
    
    
      



	/**
	 * 获取全部数据
	 * @param termimals
	 * @param signal
	 * @return
	 */
	@Deprecated
	public List<Map<String,Object>> getAllDate(List<TerimalModel> termimals,String signal,String projectId){
		return terRealDataDao.getData(termimals, signal,projectId, 0);
	}
	/**
	 * 获取数据 只包含给定字段
	 * @param termimals
	 * @param signal
	 * @param fields
	 * @param projectId 
	 * @return
	 */
	@Deprecated
	public List<Map<String,Object>> getAllDateInclude(List<TerimalModel> termimals,String signal,String projectId,String...fields){
		return terRealDataDao.getData(termimals, signal,projectId, 1,fields);
	} 
	
	/**
	 * 获取数据 排除给定字段
	 * @param termimals
	 * @param signal
	 * @param fields
	 * @return
	 */
	@Deprecated
	public List<Map<String,Object>> getAllDateExclude(List<TerimalModel> termimals,String signal,String projectId,String...fields){
		return terRealDataDao.getData(termimals, signal,projectId, -1,fields);
	}



	/**
	 * 获取实时数据 并统计其中的boolean类型
	 * @param termimals 终端列表
	 * @param signal 信号量
	 * @param includeType 查询字段排除规则 include 或则exclude
	 * @param fields 需要查询或排除的字段 
	 * @param countIncludeType 统计字段排除规则
	 * @param countFields 需要统计或者排除统计的字段
	 * @return
	 */
	public List<Map<String, Object>> getDataBoolean(List<TerimalModel> termimals, String signal,String projectId, String includeType,
			String[] fields, String countIncludeType, String[] countFields) {
		// TODO Auto-generated method stub
		return terRealDataDao.getDataBoolean(termimals, signal,projectId, includeType, fields,countIncludeType,countFields);
	} 
	
	/**
	 * 获取实时数据 支持指定需要查询的字段 或排除的字段
	 * @param termimals 终端列表
	 * @param signal 信号量
	 * @param includeType 查询字段排除规则 include 或则exclude
	 * @param fields  需要查询或排除的字段 
	 * @return
	 */
	public List<Map<String, Object>> getData(List<TerimalModel> termimals, String signal,String projectId, String includeType,
			String[] fields) {
		// TODO Auto-generated method stub
		return terRealDataDao.getData(termimals, signal,projectId, includeType, fields);
	} 
	  
    
   
}
