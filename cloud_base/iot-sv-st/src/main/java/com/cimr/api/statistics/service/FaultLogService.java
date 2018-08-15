package com.cimr.api.statistics.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimr.api.comm.model.HttpResult;
import com.cimr.api.comm.model.PageModel;
import com.cimr.api.statistics.dao.FaultLogDao;
import com.cimr.api.statistics.exception.TimeTooLongException;

@Service
public class FaultLogService  {
	
	@Autowired
	private FaultLogDao faultLogDao;
	
	
	
	private static final Logger log = LoggerFactory.getLogger(FaultLogService.class);


	
	/**
	 * 分页查询
	 * @param pageNumber
	 * @param pageSize
	 * @param bTime
	 * @param endTime
	 * @param terid
	 * @return
	 */
	public HttpResult findByPage(int pageNumber, int pageSize,Long bTime,Long endTime,
			String terId,String code,Boolean status){
		HttpResult result;
		try {
			PageModel<Map<String,Object>> page = faultLogDao.findByPage(pageNumber, pageSize, bTime, endTime, terId,code,status);
			result = new HttpResult(true,"");
			result.setData(page);
			return result;
		} catch (TimeTooLongException e) {
			result = new HttpResult(true,e.getMessage());
			return result;
		}catch (Exception e) {
			e.printStackTrace();
			result = new HttpResult(true,"出现异常");
			return result;
		}
		
	}
	
}
