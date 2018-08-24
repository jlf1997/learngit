package com.cimr.api.statistics.service.gen.faultGen;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimr.api.log.service.TelLogService;
import com.cimr.api.statistics.model.FaultLog;
import com.cimr.api.statistics.service.interfaces.DefaultFaultGen;


@Service
public class TerminalFaultGen extends DefaultFaultGen{
	
	@Autowired
	private TelLogService telLogService;
	
	/**
	 * 错误发生回传周期，单位毫秒
	 */
	private static Long timePro = 10000L;
	
	/**
	 * 默认错误结束时长，单位毫秒
	 */
	private static Long defaultTime = timePro;
	
	public TerminalFaultGen() {
		this.type=FaultLog.TERERROR;
		this.code="code";
		this.terminalNo="terminalNo";
		this.faultTime = "faultTime";
		
	}
	
	/**
	 * 生成错误结束时间，默认在原结束时间上增加一个周期
	 * @param faultLog
	 * @return
	 */
	private Date getRealEndTime(FaultLog faultLog) {
		return new Date(faultLog.getEndTime().getTime()+defaultTime);
	}

	/**
	 * 构造新错误对象
	 * @param bTime
	 * @param code
	 * @param terId
	 * @return
	 */
	@Override
	public FaultLog getNewFaultLog(ObjectId orgId,Date bTime,String code,String terId) {
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
		return telLogService.findAllByDay(bTime,eTime);
		
	}
	
	@Override
	protected Long getCount(Date bTime, Date eTime) {
		// TODO Auto-generated method stub
		return telLogService.getCount(bTime, eTime);
	}


	@Override
	public void parseFaultLog(Map<String, Object> map,Map<String, Object> falutMap, List<Object> finalResult) {
		// TODO Auto-generated method stub
		FaultLog faultLog = (FaultLog) falutMap.get("code");
		String code = getCode(map);
		String terId = getTerId(map);
		Date time = getTime(map);
		ObjectId orgId = getOrgId(map);
		if(faultLog==null){
			faultLog = getNewFaultLog(orgId,time,code,terId);
			falutMap.put("code", faultLog);
		}
		//判断是否异常结束
		if(time.getTime()-faultLog.getEndTime().getTime()>timePro) {
			//更新错误发生的结束时间
			faultLog.setEndTime(getRealEndTime(faultLog));
			faultLog.setStatus(1);
			finalResult.add(faultLog);
			//map中替换为新的错误记录
			falutMap.put("code",getNewFaultLog(orgId,time,code,terId));
		}else {
			//更新时间
			faultLog.setEndTime(time);
		}
	}

	@Override
	protected String getTimeSaveType() {
		// TODO Auto-generated method stub
		return "fault_ter_org";
	}


}
