package com.cimr.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RecursiveTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cimr.boot.utils.TimeUtil;

public class Calculator extends RecursiveTask<List<Map<String,Object>>>{
	
	
	private static final Logger log = LoggerFactory.getLogger(Calculator.class);

	private AbstractGenLogTimeRang abstractGenLogTimeRang;
	//需要计算的总数
	private long count;
	//当前计算序号
	private int index;
	
	private Date bTime;
	
	private Date eTime;
	
	private List<Map<String, Object>> faultMapList;
	
	private static final Long  maxDealRecordSize =10000L;
	
	public Calculator(long count,int index,Date bTime,Date eTime,List<Map<String, Object>> faultMapList,AbstractGenLogTimeRang abstractGenLogTimeRang) {
		this.index = index;
		this.count = count;
		this.bTime = bTime;
		this.eTime = eTime;
		if(faultMapList==null) {
			faultMapList = new ArrayList<>();
		}
		this.faultMapList = faultMapList;
		this.abstractGenLogTimeRang = abstractGenLogTimeRang;
	}

	@Override
	protected List<Map<String, Object>> compute() {

		log.debug("cout too much:"+count);
		int rangeTime = (int) ((eTime.getTime()-bTime.getTime())/(count*1000));
		Date tempB = bTime;
		Date tempE = eTime;
		log.debug("b:{},e:{}",tempB,tempE);
		long eee = new Date().getTime();
		List<Map<String,Object>> temp = abstractGenLogTimeRang.getDateFromSource(tempB,tempE);
		long bbb = new Date().getTime();
		log.debug("cost:"+(bbb-eee)/1000+" size:"+temp.size());
		faultMapList.addAll(temp);
		if(index<count) {
			tempB =  TimeUtil.getSecond(tempE, 1);
			tempE =  TimeUtil.getSecond(tempB, rangeTime);
			Calculator calculator = new Calculator(count,index+1,tempB,tempE,faultMapList,abstractGenLogTimeRang);
			calculator.fork();
			faultMapList.addAll(calculator.join());
		}
		return faultMapList;
		
	}

	

}
