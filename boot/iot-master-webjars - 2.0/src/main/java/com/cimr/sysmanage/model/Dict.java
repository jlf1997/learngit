package com.cimr.sysmanage.model;
import com.cimr.comm.base.BaseEntity;
/**
 * 
 * <p>Title: SysDict</p>
 * @author lailichun
 */
public class Dict extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 键值
	 */
	private String value;

	/**
	 * 标签
	 */
	private String label;

	/**
	 * 字典类型
	 */
	private String type;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 排序
	 */
	private Float orderId;

	/**
	 * 父级ID
	 */
	private String parentId;
	/**
	 * 备注
	 */
	private String remarks;

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

	public Float getOrderId() {
		return orderId;
	}

	public void setOrderId(Float orderId) {
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

}