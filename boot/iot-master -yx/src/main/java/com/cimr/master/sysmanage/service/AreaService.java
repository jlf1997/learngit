package com.cimr.sysmanage.service;

import com.cimr.comm.base.BaseService;
import com.cimr.sysmanage.model.Area;

/**
 * 
 * <p>
 * Title: TenantService
 * </p>
 * <p>
 * Description: 地区数据Service接口
 * </p>
 */
public interface AreaService extends BaseService<Area, String> {

	int addNewArea(Area area);

	int updateArea(Area area);

	//根据ID删除地区信息(删除所有子项)
	void deleteAreaById(String id);

	/**
	 * 地区条件list查询
	 * 
	 * @return List<SysArea>
	 */
	/*
	 * List<Area> getList(Area area);
	 *//**
	 * 地区条件查询数量
	 * 
	 * @param pid
	 *            地区父级id
	 * @param areaId
	 *            地区id
	 * @param countryCode
	 *            国家区号
	 * @param title
	 *            地区名字
	 * @param code
	 *            地区英文名（拼音）
	 * @return Integer
	 */
	/*
	 * Integer getListCount(String pid, String areaId, Long countryCode, String
	 * title, String code);
	 *//**
	 * 管理后台添加一个地区
	 *
	 * @param pid
	 *            地区父级id
	 * @param areaId
	 *            地区id
	 * @param title
	 *            地区名字
	 * @param code
	 *            地区英文名（拼音）
	 * @param countryCode
	 *            国家区号
	 */
	/*
	 * void insert(String pid, String areaId, String title, String code, Long
	 * countryCode, Float orderId);
	 *//**
	 * 管理后台更新一个地区
	 * 
	 * @param id
	 *            主键
	 * @param pid
	 *            地区父级id
	 * @param areaId
	 *            地区id
	 * @param title
	 *            地区名字
	 * @param code
	 *            地区英文名（拼音）
	 * @param countryCode
	 *            国家区号
	 */
	/*
	 * void update(String id, String pid, String areaId, String title, String
	 * code, Long countryCode,Float orderId);
	 *//**
	 * 根据areaId、国家地区code获取地区信息
	 * 
	 * @param areaId
	 *            地区No
	 * @param countryCode
	 *            国家区号
	 * @return SysArea
	 */
	/*
	 * Area getAreaByAreaId(String areaId, Long countryCode);
	 * 
	 * void deleteById(String id);
	 * 
	 * Area getById(String id);
	 */
}
