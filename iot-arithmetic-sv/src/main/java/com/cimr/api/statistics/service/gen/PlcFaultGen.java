package com.cimr.api.statistics.service.gen;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimr.api.history.dao.RealDataFalutHistoryDao;
import com.cimr.api.statistics.model.FaultLog;
import com.cimr.api.statistics.service.interfaces.DefaultFaultGen;

@Service
public  class PlcFaultGen extends DefaultFaultGen{
	
	@Autowired
	private RealDataFalutHistoryDao realDataFalutHistoryDao;
	
	public PlcFaultGen() {
		this.type=FaultLog.PLCERROR;
		this.terminalNo="terminalNo";
		this.faultTime = "gatherMsgTime";
		
	}
	
	@Override
	public FaultLog getNewFaultLog(ObjectId orgId, Date bTime, String code, String terId) {
		// TODO Auto-generated method stub
		FaultLog faultLog = new FaultLog();
		faultLog.setbTime(bTime);
		faultLog.setEndTime(bTime);
		faultLog.setCode(code);
		faultLog.setId(getId());
		faultLog.setTerId(terId);
		faultLog.setFaultType(type);
		return faultLog;
	}

	@Override
	public List<Map<String, Object>> getDateFromSource(Date bTime, Date eTime) {
		// TODO Auto-generated method stub
		return realDataFalutHistoryDao.findFaultList(bTime,eTime);
	}

	@Override
	public void parseFaultLog(Map<String, Object> map, Map<String, Object> falutMap, List<Object> finalResult) {
		// TODO Auto-generated method stub
		String terId = getTerId(map);
		Date time = getTime(map);
		ObjectId orgId = getOrgId(map);
		Iterator<String> iterator = map.keySet().iterator();
		while(iterator.hasNext()) {
			String code = iterator.next();
			Object value = map.get(code);
			
			if(value instanceof Boolean) {
				Boolean newValue = (Boolean) value;
				FaultLog inValue = (FaultLog) falutMap.get(code);
				if(inValue==null) {//新的错误
					if(newValue) {
						falutMap.put(code, getNewFaultLog(orgId,time,code,terId));
					}else {//如果新的数据为false，直接忽略
						//查询最早的数据，如果存在则结束
//						log.debug("the fault is not change:"+key+" "+newValue);
					}
				}else {//已有的错误
					if(!newValue) {
						inValue.setEndTime(time);
						finalResult.add(inValue);
						falutMap.remove(code);
					}else {//旧数据依然为错误，直接忽略
//						log.debug("the fault is not change"+key+" "+newValue);
					}
				}
			}
		}
	}

	

}
