package com.cimr.api.log.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimr.api.log.dao.TelLogDao;
import com.cimr.boot.utils.TimeUtil;

@Service
public class TelLogService {

	
	@Autowired
	private TelLogDao telLogDao;
	
	
	/**
	 * 查询某天的
	 * @param faultTime
	 * @return
	 */
	public List<Map<String,Object>> findAllByDay(Date btime,Date eTime){
		String year = TimeUtil.getYear(btime);
		return telLogDao.findAllByDay(year,btime,eTime);
	}
	public Long getCount(Date btime,Date eTime){
		String year = TimeUtil.getYear(btime);
		return telLogDao.getCount(year,btime,eTime);
	}
	
}
