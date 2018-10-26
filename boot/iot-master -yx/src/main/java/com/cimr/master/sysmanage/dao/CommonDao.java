package com.cimr.sysmanage.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 公用服务Dao类
 * @author 38464
 *
 */
public interface CommonDao{
   
	/**
	 * 获取某个表某列的最大值 ，支持整形和有规律的字符串+数字 比如：TEL001/TEL002
	 * @param tableName
	 * @param idColName
	 * @return
	 */
	public abstract String getMaxId(@Param("idColName") String idColName,@Param("tableName") String tableName);
   
}
