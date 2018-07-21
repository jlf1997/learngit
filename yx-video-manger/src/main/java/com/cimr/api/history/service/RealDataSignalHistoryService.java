package com.cimr.api.history.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimr.api.history.dao.RealdataSignalHistoryDao;

@Service
public class RealDataSignalHistoryService {


	@Autowired
	protected RealdataSignalHistoryDao realdataSignalHistoryDao;
	

	

	public List<Map<String, Object>> findTersAllRealData(String singal,String projectId, String terid, Long beg, Long end,String[] sortBy,String sortType) {
		return realdataSignalHistoryDao.findAllDataByTimeAndSingal(singal,projectId,terid, beg, end,sortBy,sortType,0);
	} 
	
	public List<Map<String, Object>> findTersAllRealDataInclude(String singal,String projectId, String terid, Long beg, Long end,String[] sortBy,String sortType,String...fields) {
		return realdataSignalHistoryDao.findAllDataByTimeAndSingal(singal,projectId,terid, beg, end,sortBy,sortType,1,fields);
	} 
	
	public List<Map<String, Object>> findTersAllRealDataExculde(String singal,String projectId, String terid, Long beg, Long end,String[] sortBy,String sortType,String...fields) {
		return realdataSignalHistoryDao.findAllDataByTimeAndSingal(singal,projectId,terid, beg, end,sortBy,sortType,-1,fields);
	}
	
	
	
	public List<Map<String, Object>> findTersAllRealDataBooleanCount(String singal,String projectId, String terid, Long beg, Long end,
			String[] sortBy, String sortType, String includeType, String[] fields, String countIncludeType,
			String[] countFields) {
		// TODO Auto-generated method stub
		return realdataSignalHistoryDao.findTersAllRealDataBooleanCount(singal,projectId, terid,beg,end,sortBy,sortType,includeType,fields,countIncludeType,countFields);
	} 
		
}
