package com.cimr.api.code.model.base;

import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cimr.boot.model.BaseModel;

/**
 * 回调记录
 * @author Administrator
 *
 */
@Entity
@Table(name="td_callback_log")
public class CallBackLog extends BaseModel{

	@Id
	@GeneratedValue
	private Long callBackId;
	
	@ManyToOne
	@JoinColumn(name = "historyId",foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
	private CodeHistory codeHistory;
	
	private Integer HttpCode;
	
	
	
	@Lob
	private String cause;

	public Long getCallBackId() {
		return callBackId;
	}

	public void setCallBackId(Long callBackId) {
		this.callBackId = callBackId;
	}

	

	public CodeHistory getCodeHistory() {
		return codeHistory;
	}

	public void setCodeHistory(CodeHistory codeHistory) {
		this.codeHistory = codeHistory;
	}

	public Integer getHttpCode() {
		return HttpCode;
	}

	public void setHttpCode(Integer httpCode) {
		HttpCode = httpCode;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}
	
	
}
