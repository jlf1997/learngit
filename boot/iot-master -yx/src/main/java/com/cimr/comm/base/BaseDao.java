package com.cimr.comm.base;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cimr.util.Assist;

/**
 * 实体增删改查的dao基类
 * @author 38464
 * @param <T>
 */
public interface BaseDao<T, PK extends Serializable>{

	/**
	 * 获得Obj数据的总行数,可以通过辅助工具Assist进行条件查询,如果没有条件则传入null
	 * @param assist
	 * @return
	 */
    long getObjRowCount_common(Assist assist);
	/**
	 * 获得Obj数据集合,可以通过辅助工具Assist进行条件查询,如果没有条件则传入null
	 * @param assist
	 * @return
	 */
    List<T> selectObj_common(Assist assist);
	/**
	 * 获得一个Obj对象,以参数Obj对象中不为空的属性作为条件进行查询
	 * @param obj
	 * @return
	 */
    T selectObjByObj_common(T obj);
	/**
	 * 通过Obj的id获得Obj对象
	 * @param id
	 * @return
	 */
    T selectObjById_common(PK id);
	/**
	 * 插入Obj到数据库,包括null值
	 * @param value
	 * @return
	 */
    int insertObj_common(T value);
	/**
	 * 插入Obj中属性值不为null的数据到数据库
	 * @param value
	 * @return
	 */
    int insertNonEmptyObj_common(T value);
	/**
	 * 批量插入Obj到数据库,包括null值
	 * @param value
	 * @return
	 */
    int insertObjByBatch_common(List<T> value);
	/**
	 * 通过Obj的id删除Obj
	 * @param id(一般为逻辑删除，更新字段del_flag为1)
	 * @return
	 */
    int deleteObjById_common(PK id);
	/**
	 * 通过辅助工具Assist的条件删除Obj
	 * @param assist(一般为逻辑删除，更新字段del_flag为1)
	 * @return
	 */
    int deleteObj_common(Assist assist);
	/**
	 * 通过Obj的id更新Obj中的数据,包括null值
	 * @param enti
	 * @return
	 */
    int updateObjById_common(T enti);
 	/**
	 * 通过辅助工具Assist的条件更新Obj中的数据,包括null值
	 * @param value
	 * @param assist
	 * @return
	 */
    int updateObj_common(@Param("enti") T value, @Param("assist") Assist assist);
	/**
	 * 通过Obj的id更新Obj中属性不为null的数据
	 * @param enti
	 * @return
	 */
    int updateNonEmptyObjById_common(T enti);
 	/**
	 * 通过辅助工具Assist的条件更新Obj中属性不为null的数据
	 * @param value
	 * @param assist
	 * @return
	 */
    int updateNonEmptyObj_common(@Param("enti") T value, @Param("assist") Assist assist);
}
