package com.cimr.master.comm.base;

import java.io.Serializable;
import java.util.List;

import com.cimr.util.Assist;
import com.cimr.util.PageData;

/**
 * 实体增删改查的service基类
 * @author 38464
 * @param <T>
 */
public interface BaseService<T, PK extends Serializable>{

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
     * 分页获得Obj数据集合,可以通过辅助工具Assist进行条件查询,如果没有条件则传入null
     * @param assist
     * @return
     */
    PageData<T> selectObj_common(Assist assist, int page, int limit);
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
	 * 批量插入Obj到数据库
	 * @param value
	 * @return
	 */
    int insertObjByBatch_common(List<T> value);
	/**
	 * 通过Obj的id删除Obj(物理删除)
	 * @param id
	 * @return
	 */
    int deleteObjById_common(PK id);
	/**
	 * 通过辅助工具Assist的条件删除Obj(物理删除)
	 * @param assist
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
    int updateObj_common(T value,  Assist assist);
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
    int updateNonEmptyObj_common(T value, Assist assist);

    /**
     * 根据实体删除
     * @param enti
     * @return
     */
	int deleteObjById_common(T enti);

    /**
     * 带条件根据实体删除
     * @param enti
     * @param assist
     * @return
     */
	int deleteObj_common(T enti,Assist assist);
}
