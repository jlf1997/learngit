package com.cimr.api.statistics.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimr.api.statistics.dao.FaultLogDao;
import com.cimr.api.statistics.exception.TimeTooLongException;
import com.cimr.boot.comm.PageModel;
import com.cimr.boot.comm.model.HttpResult;
import com.cimr.boot.utils.LogsUtil;

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
			result = new HttpResult(false,"查询时间范围超过限制");
			return result;
		}catch (Exception e) {
			String cause = LogsUtil.getStackTrace(e);
			log.error(cause);
			result = new HttpResult(false,"服务器发生异常");
			return result;
		}
		
	}
	
}
