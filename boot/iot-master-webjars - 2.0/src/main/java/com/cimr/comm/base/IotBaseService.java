package com.cimr.comm.base;

import com.cimr.sysmanage.dto.HttpResult;
import com.cimr.util.Assist;
import com.cimr.util.PageData;

import java.io.Serializable;
import java.util.List;

/**
 * 单表增删改查的service基类接口
 * @author 38464
 * @param <T>
 */
public interface IotBaseService<T, PK extends Serializable>{

	/**
	 * 获得Obj数据的总行数,可以通过辅助工具Assist进行条件查询,如果没有条件则传入null
	 * @param assist
	 * @return
	 */
    long getObjRowCountCommon(Assist assist);
	/**
	 * 获得Obj数据集合,可以通过辅助工具Assist进行条件查询,如果没有条件则传入null
	 * @param assist
	 * @return
	 */
    List<T> selectListCommon(Assist assist);
    /**
     * 分页获得Obj数据集合,可以通过辅助工具Assist进行条件查询,如果没有条件则传入null
     * @param assist
     * @return
     */
    PageData<T> selectPageCommon(Assist assist, int page, int limit) throws Exception;
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
    HttpResult insertObjCommon(T value);
	/**
	 * 插入Obj中属性值不为null的数据到数据库
	 * @param value
	 * @return
	 */
	HttpResult insertNonEmptyObjCommon(T value);
	/**
	 * 批量插入Obj到数据库
	 * @param value
	 * @return
	 */
	HttpResult insertObjByBatchCommon(List<T> value);
	/**
	 * 通过Obj的id删除Obj(物理删除)
	 * @param id
	 * @return
	 */
	HttpResult deleteObjByIdCommon(PK id);
	/**
	 * 通过辅助工具Assist的条件删除Obj(物理删除)
	 * @param assist
	 * @return
	 */
	HttpResult deleteObjCommon(Assist assist);
	/**
	 * 通过Obj的id更新Obj中的数据,包括null值
	 * @param enti
	 * @return
	 */
	HttpResult updateObjByIdCommon(T enti);
 	/**
	 * 通过辅助工具Assist的条件更新Obj中的数据,包括null值
	 * @param value
	 * @param assist
	 * @return
	 */
	HttpResult updateObjCommon(T value, Assist assist);
	/**
	 * 通过Obj的id更新Obj中属性不为null的数据
	 * @param enti
	 * @return
	 */
	HttpResult updateNonEmptyObjByIdCommon(T enti);
 	/**
	 * 通过辅助工具Assist的条件更新Obj中属性不为null的数据
	 * @param value
	 * @param assist
	 * @return
	 */
	HttpResult updateNonEmptyObjCommon(T value, Assist assist);

    /**
     * 根据实体删除(逻辑删除)
     * @param enti
     * @return
     */
	HttpResult deleteObjByIdLogicCommon(T enti);

    /**
     * 带条件根据实体删除(逻辑删除)
     * @param enti
     * @param assist
     * @return
     */
	HttpResult deleteObjLogicCommon(T enti, Assist assist);
}
