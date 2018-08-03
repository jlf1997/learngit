package com.cimr.api.statistics.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.cimr.api.comm.model.HttpResult;
import com.cimr.api.comm.model.PageModel;
import com.cimr.api.statistics.dao.FaultLogDao;
import com.cimr.api.statistics.dao.StaticsticsLogDao;
import com.cimr.api.statistics.exception.TimeTooLongException;
import com.cimr.api.statistics.model.FaultLog;
import com.cimr.api.statistics.model.StaticsticsLog;
import com.cimr.boot.utils.TimeUtil;

@Service
public class FaultLogService  {
	
	@Autowired
	private FaultLogDao faultLogDao;
	
	@Autowired
	private StaticsticsLogDao staticsticsLogDao;
	
	
	private static final Logger log = LoggerFactory.getLogger(FaultLogService.class);


	
	/**
	 * 分页查询
	 * @param pageNumber
	 * @param pageSize
	 * @param bTime
	 * @param endTime
	 * @param terid
	 * @return
	 */
	public HttpResult findByPage(int pageNumber, int pageSize,Long bTime,Long endTime,
			String terId,String code,Boolean status){
		HttpResult result;
		try {
			PageModel<Map<String,Object>> page = faultLogDao.findByPage(pageNumber, pageSize, bTime, endTime, terId,code,status);
			result = new HttpResult(true,"");
			result.setData(page);
			return result;
		} catch (TimeTooLongException e) {
			result = new HttpResult(true,e.getMessage());
			return result;
		}catch (Exception e) {
			e.printStackTrace();
			result = new HttpResult(true,"出现异常");
			return result;
		}
		
	}
	
	
	/**
	 * 获取开始时间 结束时间
	 * 规则：1.默认开始时间为数据库中上次结束时间
	 *       2.默认结束时间为当前时间
	 *       3.开始时间与结束时间不跨年
	 *       4.最大间隔如果超过一天，则结束时间为开始时间那天最后时间
	 * @return
	 */
	public Date[] getDefaultTimeRange(Integer type) {
		//记录是否是之前的故障延续
		//查询出存在异常的记录
		//统计结束时间
		Date faultEndDate = new Date();
		//上次统计时间
		Date faultStartTime = staticsticsLogDao.getDate("fault_"+type);
		//第一次统计 
		if(faultStartTime==null) {
			faultStartTime = TimeUtil.getTheLastYear(new Date());
		}else {
			faultStartTime = TimeUtil.getNextSecord(faultStartTime);
			//如果时间跨年，则此次统计只统计到上一年最后一刻
			if(faultStartTime!=null && faultEndDate!=null ) {
				
				//如果本次查询时间超过一天，则只查询从开始到结束的时间
//				if(faultEndDate.getTime()-faultStartTime.getTime()>TimeUtil.DAY_1) {
//					faultEndDate = TimeUtil.getEndTime(faultStartTime);
//				}
				
			}
		}
		if(TimeUtil.getYearSpan(faultStartTime, faultEndDate)>=1) {
			faultEndDate = TimeUtil.getTheLastYear(faultEndDate);
			faultEndDate = TimeUtil.getTheLastDayOfYear(faultEndDate);
		}
		log.debug("begin get the log:"+faultStartTime+"~"+faultEndDate);
		return new Date[]{faultStartTime,faultEndDate};
		
	}
	
	/**
	 * 将需要更新与新增的数据分离
	 * @param resutl
	 * @return
	 */
	public List<FaultLog> getPreList(List<FaultLog> resutl){
		List<FaultLog> updList = new ArrayList<>();
		Iterator<FaultLog> iterator = resutl.iterator();
		FaultLog faultLog;
		while(iterator.hasNext()) {
			faultLog = iterator.next();
			if(faultLog.getYear()!=null) {
				updList.add(faultLog);
				iterator.remove();
			}
		}
		return updList;
	}
}
