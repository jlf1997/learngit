package com.cimr.api.statistics.service.gen.faultGen;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimr.api.comm.configuration.ProjectPropertities;
import com.cimr.api.comm.configuration.SignalSetting;
import com.cimr.api.history.dao.RealDataSignalHistoryDao;
import com.cimr.api.statistics.model.FaultLog;
import com.cimr.api.statistics.service.interfaces.DefaultFaultGen;

@Service
public  class PlcFaultGen extends DefaultFaultGen{
	
	@Autowired
	private RealDataSignalHistoryDao realDataSignalHistoryDao;
	
	
	private ProjectPropertities projectPropertities;
	
	@Autowired
	public PlcFaultGen(ProjectPropertities projectPropertities,SignalSetting setting) {
		this.type=FaultLog.PLCERROR;
		
		this.projectPropertities =projectPropertities;
		this.terminalNo="terminalNo";
		this.faultTime = setting.getGatherMsgTime(projectPropertities.getSingalFault());
		
	}
	
	@Override
	public FaultLog getNewFaultLog(ObjectId orgId, Date bTime, String code, String terId) {
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
		return realDataSignalHistoryDao.findAll(bTime, eTime, projectPropertities.getSingalFault());
	}
	
	

	@Override
	protected Long getCount(Date bTime, Date eTime) {
		// TODO Auto-generated method stub
		return realDataSignalHistoryDao.getCount(bTime, eTime, projectPropertities.getSingalFault());
	}

	@Override
	public void parseFaultLog(Map<String, Object> map, Map<String, Object> falutMap, List<Object> finalResult) {
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
						inValue.setStatus(1);
						finalResult.add(inValue);
						falutMap.remove(code);
					}else {//旧数据依然为错误，直接忽略
//						log.debug("the fault is not change"+key+" "+newValue);
					}
				}
			}
		}
	}



	@Override
	protected String getTimeSaveType() {
		// TODO Auto-generated method stub
		return "fault_plc_org";
	}



	

}
