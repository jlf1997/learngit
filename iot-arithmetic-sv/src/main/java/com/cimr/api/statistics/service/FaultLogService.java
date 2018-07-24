package com.cimr.api.statistics.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.cimr.api.comm.model.HttpResult;
import com.cimr.api.comm.model.PageModel;
import com.cimr.api.statistics.dao.FaultLogDao;
import com.cimr.api.statistics.exception.TimeTooLongException;

@Service
public class FaultLogService  {
	
	@Autowired
	private FaultLogDao faultLogDao;

	
	/**
	 * 分页查询
	 * @param pageNumber
	 * @param pageSize
	 * @param bTime
	 * @param endTime
	 * @param terid
	 * @return
	 */
	public HttpResult findByPage(int pageNumber, int pageSize,Long bTime,Long endTime,String terid){
		HttpResult result;
		try {
			PageModel<Map<String,Object>> page = faultLogDao.findByPage(pageNumber, pageSize, bTime, endTime, terid);
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
