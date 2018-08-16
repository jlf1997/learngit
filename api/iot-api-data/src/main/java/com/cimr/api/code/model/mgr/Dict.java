package com.cimr.api.code.model.mgr;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cimr.api.dev.model.mgr.BaseEntity;
/**
 * 
 * <p>Title: SysDict</p>
 * @author lailichun
 */
@Entity
@Table(name="sys_dict")
public class Dict extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@Id
	private String id;
	
	/**
	 * 键值
	 */;
	private String value;

	/**
	 * 标签
	 */;
	private String label;

	/**
	 * 字典类型
	 */;
	private String type;

	/**
	 * 描述
	 */;
	private String description;

	/**
	 * 排序
	 */;
	private Integer orderId;

	/**
	 * 父级ID
	 */;
	private String parentId;
	/**
	 * 备注
	 */;
	private String remarks;

	private String allType;
	private String descriptions;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getAllType() {
		return allType;
	}

	public void setAllType(String allType) {
		this.allType = allType;
	}

	public String getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}
}