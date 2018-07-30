package com.cimr.api.statistics.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.stereotype.Service;

import com.cimr.api.comm.model.HttpResult;
import com.cimr.api.comm.model.PageModel;
import com.cimr.api.statistics.dao.RealDataSignalDao;
import com.cimr.boot.utils.TimeUtil;

@Service
public class RealDataSignalService {

	@Autowired
	private RealDataSignalDao realDataSignalDao;
	
	
	/**
	 * 查询时间范围内的日统计数据 分页
	 * @param signal
	 * @param pageNumber
	 * @param pageSize
	 * @param bTime
	 * @param endTime
	 * @param terid
	 * @return
	 */
	public HttpResult getStatisticsDataDayPage(String signal,int pageNumber, int pageSize,Long bTime,Long endTime,String terid){
		if(bTime!=null) {
			bTime = TimeUtil.getStartTime(new Date(bTime)).getTime();
		}
		if(endTime!=null) {
			endTime = TimeUtil.getStartTime(new Date(endTime)).getTime();
		}
		PageModel<Map<String,Object>> data = realDataSignalDao.findByPageAgg(signal, pageNumber, pageSize, bTime, endTime, terid);
		HttpResult res = new HttpResult(true,"");
		res.setData(data);
		return res;
	}
	
	/**
	 * 查询时间范围内全部的日统计数据 
	 * @param signal
	 * @param bTime
	 * @param endTime
	 * @param terid
	 * @return
	 */
	public HttpResult getStatisticsDataDay(String signal,Long bTime,Long endTime,String terid){
		if(bTime!=null) {
			bTime = TimeUtil.getStartTime(new Date(bTime)).getTime();
		}
		if(endTime!=null) {
			endTime = TimeUtil.getStartTime(new Date(endTime)).getTime();
		}
		List<Map<String,Object>> data = realDataSignalDao.findAll(signal, bTime, endTime, terid);
		HttpResult res = new HttpResult(true,"");
		res.setData(data);
		return res;
	}
	
	/**
	 * 统计月数据
	 * @param signal
	 * @param pageNumber
	 * @param pageSize
	 * @param bTime
	 * @param endTime
	 * @param terid
	 * @return
	 */
	public HttpResult getStatisticsDataMonthPage(String signal,int pageNumber, int pageSize,Long bTime,Long endTime,String terid){
		if(bTime!=null) {
			bTime = TimeUtil.getStartTime(new Date(bTime)).getTime();
		}
		if(endTime!=null) {
			endTime = TimeUtil.getStartTime(new Date(endTime)).getTime();
		}
		AggregationOperation [] aggregations = new AggregationOperation[1];
		aggregations[0] = Aggregation.group("year","month","terminalNo").sum("FQ_DAY_OIL").as("oil");
		PageModel<Map<String,Object>> data = realDataSignalDao.findByPageAgg(signal, pageNumber, pageSize, bTime, endTime, terid,aggregations);
		HttpResult res = new HttpResult(true,"");
		res.setData(data);
		return res;
	}
	
	/**
	 * 
	 * @param signal
	 * @param pageNumber
	 * @param pageSize
	 * @param bTime
	 * @param endTime
	 * @param terid
	 * @return
	 */
	public HttpResult getStatisticsDataYearAll(String signal,Long bTime,Long endTime,String terid){
		if(bTime!=null) {
			bTime = TimeUtil.getStartTime(new Date(bTime)).getTime();
		}
		if(endTime!=null) {
			endTime = TimeUtil.getStartTime(new Date(endTime)).getTime();
		}
		AggregationOperation [] aggregations = new AggregationOperation[1];
		aggregations[0] = Aggregation.group("year","terminalNo").sum("FQ_DAY_OIL").as("oil");
		List<Map<String,Object>> data = realDataSignalDao.findAllAgg(signal, bTime, endTime, terid,aggregations);
		HttpResult res = new HttpResult(true,"");
		res.setData(data);
		return res;
	}

}
