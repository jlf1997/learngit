package com.cimr.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cimr.api.statistics.dao.StatisticsDailyLogDao;
import com.cimr.api.statistics.model.StatisticsDailyLog;
import com.cimr.boot.utils.TimeUtil;

public abstract class AbstractGenLogTimeRang implements GenLogTimeRang{
	
	private static final Logger log = LoggerFactory.getLogger(AbstractGenLogTimeRang.class);
	
	@Resource(name="executorServiceForMongo")
	public ExecutorService executorService;
	
	@Autowired
	private StatisticsDailyLogDao statisticsDailyLogDao;
	
	/**
	 * 获取此次统计的开始时间 结束时间 数组0为开始 1为结束
	 * @param type
	 * @return
	 */
	protected  abstract Date[] getTimeRange();
	
	/**
	 * 获取时间范围内的原始数据
	 * @param bTime
	 * @param eTime
	 * @return
	 */
	protected abstract List<Map<String,Object>> getDateFromSource(Date bTime,Date eTime);
	
	protected abstract Long getCount(Date bTime,Date eTime);
	
	/**
	 * 获取未结束的任务
	 * @param type
	 * @return
	 */
	protected abstract List<Map<String,Object>>  getUnfinished();
	
	/**
	 * 处理原始数据列表
	 * @param map
	 * @param terMap
	 * @param finalResult
	 */
	protected abstract void parseFalutList(List<Map<String,Object>> list,List<Map<String,Object>> listun,List<Object> finalResult);

	
	
	
	
	/**
	 * 更新数据
	 * @param finalResult
	 */
	protected abstract void update(List<Object> finalResult);


	private ThreadLocal<Date> bthreadLocal = new ThreadLocal<>();
	
	private ThreadLocal<Date> ethreadLocal = new ThreadLocal<>();
	
	protected abstract boolean timeError(Date bTime,Date eTime);
	
	
	public final void genLog() {
		//获取需要处理的时间范围
		Date[] range = getTimeRange();
		if(range==null || range.length!=2 || range[0]==null || range[1] ==null) {
			log.info("no date to gen");
			return;
		}
		genLog(range[0],range[1]);
		
	}
	
	/**
	 * 单次最大处理数
	 */
	private Long maxDealRecordSize = 100000L;
	
	private Long maxGetDateFromDbRecordSize = 10000L;
	
	
	private final void genLog(Date bTime,Date eTime) {
		//保存最后的结果
		List<Object> finalResult = new ArrayList<>();
		
		bthreadLocal.set(bTime);
		ethreadLocal.set(eTime);
		
		log.debug("begin get the log:"+bTime+"~"+eTime);
		if(timeError(bTime,eTime)) {
			log.error("time error");
			return;
		}
		long count = getCount(getbTime(),geteTime());
		log.debug("cout size:"+count);
		List<Map<String,Object>> faultMapList = Collections.synchronizedList(new ArrayList<>());
//		List<Map<String,Object>> temp = null;
		if(count>maxDealRecordSize) {
			log.debug("超过单次处理上限:"+maxDealRecordSize);
			//降级为每日处理
			eTime=TimeUtil.getEndTime(bTime);	
			ethreadLocal.set(eTime);
			log.debug("b:{},e:{}",getbTime(),geteTime());
			count = getCount(getbTime(),geteTime());
			log.debug("cout size:"+count);
		}
		if(count>maxGetDateFromDbRecordSize) {
			int numSize = (int) (count/maxDealRecordSize);
			log.debug("cout too much:"+count);
			int rangeTime = (int) ((eTime.getTime()-bTime.getTime())/(numSize*1000));
			List<Callable<Boolean>> tasks = new ArrayList<>();
			log.debug("numSize:"+numSize);
			for(int i=0;i<numSize;i++) {
				Date tempB = TimeUtil.getSecond(getbTime(), rangeTime*i);
				Date tempE = TimeUtil.getSecond(getbTime(), rangeTime*(i+1)-1);
				tasks.add(new Callable<Boolean>() {
					@Override
					public Boolean call() throws Exception {
						log.debug("b:{},e:{}",tempB,tempE);
						long eee = new Date().getTime();
						List<Map<String,Object>> temp = getDateFromSource(tempB,tempE);
						long bbb = new Date().getTime();
						log.debug("cost:"+(bbb-eee)/1000+" size:"+temp.size());
						faultMapList.addAll(temp);
						return true;
					}
				});
			}
			try {
				executorService.invokeAll(tasks);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}else {//处理一天的数据
			faultMapList.addAll(getDateFromSource(getbTime(),geteTime()));
		}
		
		//新的需要处理的数据
		 
		log.debug("new list size:"+faultMapList.size());
		
		//获取之前未完结的数据
		List<Map<String,Object>> listun = getUnfinished();
		log.debug("unfinsh list size:"+listun.size());
		
		//处理数据
		parseFalutList(faultMapList,listun,finalResult);
	
	
		//更新数据
		update(finalResult);
		
		if(isNeedUpdate(faultMapList)) {
			//更新时间
			log.info("update time");
			updateDate();
		}else {
			log.info("no need to update time ");
		}
		
		
	}

	/**
	 * 判断是否需要更新更新时间
	 * @param faultMapList
	 * @return
	 */
	private  boolean isNeedUpdate(List<Map<String, Object>> faultMapList) {
		if(faultMapList.size()>0) {
			return true;
		}else {
			//已经超过一天 则默认没有数据
			if(new Date().getTime()-geteTime().getTime()>TimeUtil.DAY_1) {
				return true;
			}else {
				return false;
			}
		}
	}
	

	protected Date getbTime() {
		return bthreadLocal.get();
		
	}

	protected Date geteTime() {
		return ethreadLocal.get();
	}

	/**
	 * 用于保存记录统计时间表中表示不同类型
	 * @return
	 */
	protected abstract String getTimeSaveType() ;
	
	
	/**
	 * 获取上次更新时间
	 * @return
	 */
	protected Date getPreTime() {
		// TODO Auto-generated method stub
		String type = getTimeSaveType();
		StatisticsDailyLog log = statisticsDailyLogDao.getDate(type);
		if(log!=null) {
			return log.getsDate();
		}
		return null;
	}
	
	/**
	 * 更新时间
	 * @param listun
	 */
	private  void updateDate() {
		String type = getTimeSaveType();
		statisticsDailyLogDao.updateDate(type, this.geteTime());
	}
	
	
}
