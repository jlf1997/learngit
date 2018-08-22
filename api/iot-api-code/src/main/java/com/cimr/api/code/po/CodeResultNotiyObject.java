package com.cimr.api.code.po;

import com.cimr.api.code.model.base.CodeHistory;

public class CodeResultNotiyObject {
	
	private CodeHistory codeHistory;

	private String return_code;
	
	private String return_message;
	
	private Integer status;
	
	private String codeId;

	public String getReturn_code() {
		return return_code;
	}

	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}

	public String getReturn_message() {
		return return_message;
	}

	public void setReturn_message(String return_message) {
		this.return_message = return_message;
	}

	public String getCodeId() {
		return codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	

	public CodeHistory getCodeHistory() {
		return codeHistory;
	}

	public void setCodeHistory(CodeHistory codeHistory) {
		this.codeHistory = codeHistory;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
