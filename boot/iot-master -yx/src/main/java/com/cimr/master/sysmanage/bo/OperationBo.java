package com.cimr.master.sysmanage.bo;

import java.io.Serializable;

public class OperationBo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String operationKey;
	private String operationName;
	private String comment;
	private Float orderId;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Float getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Float orderId) {
		this.orderId = orderId;
	}

	public String getOperationKey() {
		return this.operationKey;
	}

	public void setOperationKey(String operationKey) {
		this.operationKey = operationKey;
	}

	public String getOperationName() {
		return this.operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
}
