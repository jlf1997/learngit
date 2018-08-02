package com.cloud.constant;

/*
 * 返回客户端错误编码表
 */
public class HttpRespStatusConstant {
	public static final String  STATIONID_IS_NULL_CODE = "1001";
	public static final String  STATIONID_IS_NULL_MSG = "客户编号不能为空或获取不到客户编号";
	
	public static final String RECOREDNUM_IS_NULL_CODE = "1002";
	public static final String RECOREDNUM_IS_NULL_MSG = "分页需查询行数不能为空";
	
	public static final String PAGENUM_IS_NULL_CODE="1003";
	public static final String PAGENUM_IS_NULL_MSG="分页当前行数不能为空";
	
	public static final String DEVICE_LIST_FAILED_CODE="1004";
	public static final String DEVICE_LIST_FAILED_MSG="查询设备信息失败";
	
	public static final String TERMINALID_IS_NULL_CODE="1005";
	public static final String TERMINALID_IS_NULL_MSG="设备编号不能为空或获取不到设备编号";
	
	public static final String START_TIME_IS_NULL_CODE = "1006";
	public static final String START_TIME_IS_NULL_MSG = "开始时间不能为空";
	
	public static final String END_TIME_IS_NULL_CODE="1007";
	public static final String END_TIME_IS_NULL_MSG="结束时间不能为空";
	
	public static final String STATION_NOT_TERMINAL_CODE="1008";
	public static final String STATION_NOT_TERMINAL_MSG="客户没有对应的设备";
	
	public static final String TOKEN_ERROR_CODE = "1009";
	public static final String TOKEN_ERROR_MSG = "token异常";
	
	public static final String PARAM_IS_NULL_CODE="1010";
	public static final String PARAM_IS_NULL_MSG="参数“p”不能为空";
	
	public static final String  FUSE_CODE="1011";
	public static final String  FUSE_MSG="熔断";
	
	public static final String RUNTIME_EXCEPTION_CODE="1999";
	public static final String RUNTIME_EXCEPTION_MSG="系统运行异常";
	public static final String  OUT_OF_DATE_CODE="1012";
	public static final String  OUT_OF_DATE_MSG="时间范围超出该服务限定时间";
	
	public static final String GPS_LIST_FAILED_CODE="1013";
	public static final String GPS_LIST_FAILED_MSG="查询gps信息失败";
	
	
	public static final String EQUIPLOG_LIST_FAILED_CODE="1014";
	public static final String EQUIPLOG_LIST_FAILED_MSG="查询设备工作数据信息失败";
	
	public static final String STYPE_LIST_FAILED_CODE="1015";
	public static final String STYPE_LIST_FAILED_MSG="用户类型不能为空";
	

	public static final String TIME_IS_NULL_CODE = "1016";
	public static final String TIME_IS_NULL_MSG = "时间不能为空";
	
	public static final String SUCCESS="0";
	
	public static final String TIME_IS_OUT_CODE = "1017";
	public static final String TIME_IS_OUT_MSG = "超出时间限制，不能查询超出当前时间或历史时间三个月";
	
}
