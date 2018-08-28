package com.cimr.api.statistics.service.interfaces;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cimr.boot.utils.TimeUtil;
import com.cimr.util.AbstractGenLogTimeRang;

public abstract class AbstractDailyGen  extends AbstractGenLogTimeRang{
	
	
	private static final Logger log = LoggerFactory.getLogger(AbstractDailyGen.class);


	@Override
	protected Date[] getTimeRange() {
		
		Date sTime = getPreTime();
		Date nextDay = TimeUtil.getNextSecord(sTime);
		if(nextDay!=null && TimeUtil.isToday(nextDay)) {
			return null;
		}
		//最早支持从1年前开始扫描数据
		if(nextDay==null) {
//			nextDay = TimeUtil.getTheLastYear(new Date());
			nextDay = TimeUtil.getTheLastMonth(new Date());
		}
		
		Date[] dates = new Date[2];
		dates[0] = TimeUtil.getStartTime(nextDay);
		dates[1] = TimeUtil.getEndTime(nextDay);
//		Long count = getCount(dates[0],dates[1]);
//		log.info("the count:"+count);
//		if(count>=100000) {
//			Date later = TimeUtil.getHour(dates[0], 1);
//			later = TimeUtil.getSecond(later, -1);
//			if(later.before(dates[1])) {
//				dates[1] = later;
//			}
//		}else if(count>10000){
//			Date later = TimeUtil.getHour(dates[0], 12);
//			later = TimeUtil.getSecond(later, -1);
//			if(later.before(dates[1])) {
//				dates[1] = later;
//			}
//		}
		return dates;
	}

	@Override
	protected List<Map<String, Object>> getUnfinished() {
		// 按日统计 无未完成数据
		return new ArrayList<>();
	}



	@Override
	protected boolean timeError(Date bTime, Date eTime) {
		// TODO Auto-generated method stub
		if(bTime.after(eTime)) {
			return true;
		}
		return false;
	}
	
	
}
