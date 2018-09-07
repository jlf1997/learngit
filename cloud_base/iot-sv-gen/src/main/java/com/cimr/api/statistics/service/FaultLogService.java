package com.cimr.api.statistics.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimr.api.statistics.dao.FaultLogDao;
import com.cimr.api.statistics.exception.TimeTooLongException;
import com.cimr.boot.comm.model.HttpResult;

@Service
public class FaultLogService  {
	
	
	private static final Logger log = LoggerFactory.getLogger(FaultLogService.class);

	@Autowired
	private FaultLogDao faultLogDao;
	
	/**
	 * 获取所有预警信息
	 * @param bTime
	 * @param endTime
	 * @param terId
	 * @param code
	 * @param status
	 * @return
	 */
	public HttpResult findAll(Long bTime,Long endTime,
			String terId,String code,Boolean status){
		HttpResult result;
		try {
			List<Map<String,Object>> page = faultLogDao.findAll(bTime, endTime, terId,code,status);
			result = new HttpResult(true,"");
			result.setData(page);
			return result;
		} catch (TimeTooLongException e) {
			result = new HttpResult(true,e.getMessage());
			return result;
		}
		
	}
//	
//	
//	
//	/**
//	 * 将需要更新与新增的数据分离
//	 * @param resutl
//	 * @return
//	 */
//	public List<FaultLog> getPreList(List<FaultLog> resutl){
//		List<FaultLog> updList = new ArrayList<>();
//		Iterator<FaultLog> iterator = resutl.iterator();
//		FaultLog faultLog;
//		while(iterator.hasNext()) {
//			faultLog = iterator.next();
//			if(faultLog.getYear()!=null) {
//				updList.add(faultLog);
//				iterator.remove();
//			}
//		}
//		return updList;
//	}
//
	public Long getCount(Date bTime, Date eTime) {
		// TODO Auto-generated method stub
		return faultLogDao.getCount(bTime,eTime);
	}




	
	
	
}
