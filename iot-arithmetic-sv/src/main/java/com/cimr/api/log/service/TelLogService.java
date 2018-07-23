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
	public List<Map<String,Object>> findAllByDay(Long faultTime){
		Date faultEndTime = TimeUtil.getEndTime(new Date(faultTime));
		Date faultStartTime = TimeUtil.getStartTime(new Date(faultTime));
		String year = TimeUtil.getYear(new Date(faultTime));
		return telLogDao.findAllByDay(year,faultStartTime,faultEndTime);
	}
	
	
}
