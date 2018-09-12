package com.cimr.api.statistics.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.stereotype.Service;

import com.cimr.api.statistics.dao.RealDataSignalDao;
import com.cimr.boot.comm.model.HttpResult;
import com.cimr.boot.utils.TimeUtil;

@Service
public class RealDataSignalService {

	@Autowired
	private RealDataSignalDao realDataSignalDao;
	
	
	/**
	 * 查询时间范围内的日统计数据 不分页
	 * @param signal
	 * @param pageNumber
	 * @param pageSize
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
		Sort sort = new Sort(Sort.Direction.ASC, "logTime");
		
		
		List<AggregationOperation> aggregations = new ArrayList<>();
		
		aggregations.add(Aggregation.sort(sort));
		List<Map<String,Object>> data = realDataSignalDao.findByTerAndTimeWithPage(signal, bTime, endTime, terid,aggregations);
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
	public HttpResult getStatisticsDataMonth(String signal,Long bTime,Long endTime,String terid){
		if(bTime!=null) {
			bTime = TimeUtil.getStartTime(new Date(bTime)).getTime();
		}
		if(endTime!=null) {
			endTime = TimeUtil.getStartTime(new Date(endTime)).getTime();
		}
		List<AggregationOperation> aggregations = new ArrayList<>();
		GroupOperation aggregationOil = Aggregation.group("year","month","terId");
		aggregations.add(aggregationOil.sum("FQ_DAY_OIL").as("oil").sum("KQ_DAY_WORK").as("work"));
		List<Map<String,Object>> data = realDataSignalDao.findByTerAndTimeWithPage(signal, bTime, endTime, terid,aggregations);
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
	public HttpResult getStatisticsDataYear(String signal,Long bTime,Long endTime,String terid){
		if(bTime!=null) {
			bTime = TimeUtil.getStartTime(new Date(bTime)).getTime();
		}
		if(endTime!=null) {
			endTime = TimeUtil.getStartTime(new Date(endTime)).getTime();
		}
		List<AggregationOperation> aggregations = new ArrayList<>();
		GroupOperation aggregationOil = Aggregation.group("year","terId");
		aggregations.add(aggregationOil.sum("FQ_DAY_OIL").as("oil").sum("KQ_DAY_WORK").as("work"));
		
		List<Map<String,Object>> data = realDataSignalDao.findByTerAndTimeWithPage(signal, bTime, endTime, terid,aggregations);
		HttpResult res = new HttpResult(true,"");
		res.setData(data);
		return res;
	}


	public HttpResult getStatisticsDataYear(String signal, Long bTime, Long eTime, List<String> terIds, String codes) {
		// TODO Auto-generated method stub
		if(bTime!=null) {
			Date b = new Date(bTime);
			b = TimeUtil.getTheFirstDayOfMonth(b);
			bTime = b.getTime();
		}
		if(eTime!=null) {
			Date e = new Date(eTime);
			e = TimeUtil.getTheLastDayOfMonth(e);
			eTime = e.getTime();
		}
		List<AggregationOperation> aggregations = new ArrayList<>();
		GroupOperation aggregationOil = Aggregation.group("year","terId");
		if(codes!=null && !StringUtils.isBlank(codes)) {
			String[] codeStrs = codes.split(",");
			for(String code: codeStrs) {
				aggregationOil = aggregationOil.sum(code).as(code);
			}
		}else {
			
		}
		aggregations.add(aggregationOil);
		List<Map<String,Object>> data = realDataSignalDao.findAll(signal,terIds, new Date(bTime), new Date(eTime),aggregations);
		HttpResult res = new HttpResult(true,"");
		res.setData(data);
		return res;
	}


	public HttpResult getStatisticsDataMonth(String signal, Long bTime, Long eTime, List<String> terIds, String codes) {
		// TODO Auto-generated method stub
				if(bTime!=null) {
					Date b = new Date(bTime);
					b = TimeUtil.getTheFirstDayOfMonth(b);
					bTime = b.getTime();
				}
				if(eTime!=null) {
					Date e = new Date(eTime);
					e = TimeUtil.getTheLastDayOfMonth(e);
					eTime = e.getTime();
				}
				List<AggregationOperation> aggregations = new ArrayList<>();
				GroupOperation aggregationOil = Aggregation.group("year","month","terId");
				if(codes!=null && !StringUtils.isBlank(codes)) {
					String[] codeStrs = codes.split(",");
					for(String code: codeStrs) {
						aggregationOil = aggregationOil.sum(code).as(code);
					}
				}else {
					
				}
				aggregations.add(aggregationOil);
				List<Map<String,Object>> data = realDataSignalDao.findAll(signal,terIds, new Date(bTime), new Date(eTime),aggregations);
				HttpResult res = new HttpResult(true,"");
				res.setData(data);
				return res;
	}


	public HttpResult getStatisticsDataDay(String signal, Long bTime, Long eTime, List<String> terIds, String codes) {
		// TODO Auto-generated method stub
		if(bTime!=null) {
			Date b = new Date(bTime);
			b = TimeUtil.getTheFirstDayOfMonth(b);
			bTime = b.getTime();
		}
		if(eTime!=null) {
			Date e = new Date(eTime);
			e = TimeUtil.getTheLastDayOfMonth(e);
			eTime = e.getTime();
		}
		List<Map<String,Object>> data = realDataSignalDao.findAll(signal,terIds, new Date(bTime), new Date(eTime),null);
		HttpResult res = new HttpResult(true,"");
		res.setData(data);
		return res;
	}
	

}
