package com.cimr.comm.base;

import com.cimr.util.Assist;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * 实体增删改查的dao基类
 * @author 38464
 * @param <T>
 */
public interface IotBaseDao<T, PK extends Serializable>{

	/**
	 * 获得Obj数据的总行数,可以通过辅助工具Assist进行条件查询,如果没有条件则传入null
	 * @param assist
	 * @return
	 */
    long getObjRowCountCommon(Assist assist);

	/**
	 * 公用列表查询
	 * @param assist 公用assis查询辅助类
	 * @return
	 */
	List<T> selectListCommon(Assist assist);

	/**
	 * 获得一个Obj对象,以参数Obj对象中不为空的属性作为条件进行查询
	 * @param obj
	 * @return
	 */
    T selectObjByObjCommon(T obj);
	/**
	 * 通过Obj的id获得Obj对象
	 * @param id
	 * @return
	 */
    T selectObjByIdCommon(PK id);
	/**
	 * 插入Obj到数据库,包括null值
	 * @param value
	 * @return
	 */
    int insertObjCommon(T value);
	/**
	 * 插入Obj中属性值不为null的数据到数据库
	 * @param value
	 * @return
	 */
    int insertNonEmptyObjCommon(T value);
	/**
	 * 批量插入Obj到数据库,包括null值
	 * @param value
	 * @return
	 */
    int insertObjByBatchCommon(List<T> value);
	/**
	 * 通过Obj的id删除Obj
	 * @param id(物理删除)
	 * @return
	 */
    int deleteObjByIdCommon(PK id);
	/**
	 * 通过辅助工具Assist的条件删除Obj
	 * @param assist(物理删除)
	 * @return
	 */
    int deleteObjCommon(Assist assist);
	/**
	 * 通过Obj的id更新Obj中的数据,包括null值
	 * @param enti
	 * @return
	 */
    int updateObjByIdCommon(T enti);
 	/**
	 * 通过辅助工具Assist的条件更新Obj中的数据,包括null值
	 * @param value
	 * @param assist
	 * @return
	 */
    int updateObjCommon(@Param("enti") T value, @Param("assist") Assist assist);
	/**
	 * 通过Obj的id更新Obj中属性不为null的数据
	 * @param enti
	 * @return
	 */
    int updateNonEmptyObjByIdCommon(T enti);
 	/**
	 * 通过辅助工具Assist的条件更新Obj中属性不为null的数据
	 * @param value
	 * @param assist
	 * @return
	 */
    int updateNonEmptyObjCommon(@Param("enti") T value, @Param("assist") Assist assist);
}
