package com.cimr.api.statistics.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.stereotype.Service;

import com.cimr.api.statistics.dao.FaultQueryDao;
import com.cimr.boot.comm.model.HttpResult;
import com.cimr.boot.utils.TimeUtil;

/**
 * 提供fault 统计查询
 * @author Administrator
 *
 */
@Service
public class FaultStatisticsService {
	
	@Autowired
	private FaultQueryDao faultQueryDao;


	public HttpResult findAllByDay(List<String> terIds, Long bTime, Long eTime,String codes) {
		// TODO Auto-generated method stub
		if(bTime!=null) {
			bTime = TimeUtil.getStartTime(new Date(bTime)).getTime();
		}
		if(eTime!=null) {
			eTime = TimeUtil.getEndTime(new Date(eTime)).getTime();
		}
		List<Map<String,Object>> data = faultQueryDao.findAll(terIds, new Date(bTime), new Date(eTime),null);
		HttpResult res = new HttpResult(true,"");
		res.setData(data);
		return res;
	}

	public HttpResult findAllByMonth(List<String> terIds, Long bTime, Long eTime,String codes) {
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
//			List<FaultField> fields = faultFieldSercvice.findAll();
//			for(FaultField field: fields) {
//				aggregationOil = aggregationOil.sum(field.getFaultKey()).as(field.getFaultKey());
//			}
		}
		aggregations.add(aggregationOil);
		List<Map<String,Object>> data = faultQueryDao.findAll(terIds, new Date(bTime), new Date(eTime),aggregations);
		HttpResult res = new HttpResult(true,"");
		res.setData(data);
		return res;
	}

	public HttpResult findAllByYear(List<String> terIds, Long bTime, Long eTime,String codes) {
		// TODO Auto-generated method stub
		if(bTime!=null) {
			Date b = new Date(bTime);
			b = TimeUtil.getTheFirstDayOfYear(b);
			bTime = b.getTime();
		}
		if(eTime!=null) {
			Date e = new Date(eTime);
			e = TimeUtil.getTheLastDayOfYear(e);
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
//			List<FaultField> fields = faultFieldSercvice.findAll();
//			for(FaultField field: fields) {
//				aggregationOil = aggregationOil.sum(field.getFaultKey()).as(field.getFaultKey());
//			}
		}
		aggregations.add(aggregationOil);
		List<Map<String,Object>> data = faultQueryDao.findAll(terIds, new Date(bTime), new Date(eTime),aggregations);
		HttpResult res = new HttpResult(true,"");
		res.setData(data);
		return res;
	}

	

}
