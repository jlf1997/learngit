package com.cimr.api.statistics.service.interfaces;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.cimr.api.statistics.model.FaultLog;
import com.cimr.api.statistics.model.StaticsicsRealDateOil;
import com.cimr.boot.statistics.AbstractGenLogTimeRang;
import com.cimr.boot.utils.TimeUtil;

public abstract class AbstractDailyGen  extends AbstractGenLogTimeRang{

	@Override
	protected Date[] getTimeRange() {
		// 时间设定为前一天 从开始到结束
		Date[] dates = new Date[2];
		Date date = TimeUtil.getDay(-1);
		dates[0] = TimeUtil.getStartTime(date);
		dates[1] = TimeUtil.getEndTime(date);
		return dates;
	}

	@Override
	protected List<Map<String, Object>> getUnfinished() {
		// 按日统计 无未完成数据
		return new ArrayList<>();
	}



	@Override
	protected void updateDate(List<Map<String, Object>> listun) {
		// 按日统计无需更新时间
		
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
