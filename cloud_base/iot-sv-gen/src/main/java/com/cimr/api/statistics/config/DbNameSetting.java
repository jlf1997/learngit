package com.cimr.api.statistics.config;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.cimr.api.comm.configuration.ProjectPropertities;
import com.cimr.boot.utils.TimeUtil;

@Component
public class DbNameSetting {
	
	
	private static  String type;
	
	@Value(value = "${iot.dbname.setting.type:DEMO}")
	public void setLogBrokerList(String typec) {
		type = "_"+typec;
	}
	
	private static final String ddl = "_D_";
	
	private static final String middle = "_M_";
	
	private static final String statistics = "_S_";
	
	//======================元数据================================
	/**
	 *元数据: 存储统计的时间点
	 * @return
	 */
	public static String getStatisticsDailyLogName() {
		return "DAILY_LOG"+ddl+ProjectPropertities.projectId+type;
	}
	//======================中间数据================================
	
	
	
	/**
	 * 中间数据:处理后的预警日志表
	 * @param year
	 * @return
	 */
	public static String getFaultLogName(String year) {
		return "TEL_FAULT_"+year+"_"+middle+ProjectPropertities.projectId+type;
	}
	
	//=========================统计数据==========================================
	/**
	 * 统计数据:错误统计表
	 * @return
	 */
	public static String getFaultStatic() {
		return "TEL_FAULT"+statistics+ProjectPropertities.projectId+type;
	}
	
	/**
	 * 统计数据:实时数据统计表
	 * @param proId
	 * @param signal
	 * @return
	 */
	public static String getRealDateStatisticsDbName(String signal) {
		return "Real_Date_Signal_"+signal+statistics+ProjectPropertities.projectId+type;
	}

	//=================原始数据===================================================
	/**
	 * 原始数据:实时数据，如日油耗 日工作量
	 * @param proId
	 * @param signal
	 * @param date
	 * @return
	 */
	public static String getRealDateSignalDbName(String signal,Date date) {
		return "REALDATA_SIGNAL_"+ProjectPropertities.projectId+"_"+signal+"_"+TimeUtil.getYearAndMonth(date);
	}
	

	/**
	 * 原始数据:预警信息原始数据表
	 * @param year
	 * @return
	 */
	public static String getTelFaultDbName(String year) {
		return "TEL_FAULT_"+year;
	}
}
