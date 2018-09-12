package com.cimr.api.gk.model.mgr;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cimr.api.comm.Base;

/**
 * 
 * <p>Title: SysArea</p>
 * <p>Description: 国家，省/直辖市，市，区/县</p>
 * @author lkl
 * @date 2016年4月15日 上午10:33:07
 * 
 */
@Table(name="tb_area")
@Entity
@org.hibernate.annotations.Table(comment="地区字典表", appliesTo = "tb_area") 
public class Area extends Base {


	@Id
	public String id;
	/**
	 * 地区编码
	 */
	public String areaId;
	
	/**
	 * 地区名字
	 */
	public String title;
	
	/**
	 * 地区英文名（拼音）
	 */
	public String code;
	
	/**
	 * 地区父级地区编码id
	 */
	public String pid;
	
	/**
	 * bid
	 */
	public Integer bid;
	
	/**
	 * 国家区号编码
	 */
	public Long countryCode;

	/**
	 * 排序ID
	 */
	public Float orderId;

}