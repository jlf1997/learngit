package com.cimr.api.code.po;

import java.util.List;

import com.cimr.api.comm.model.TerimalModel;

public class CodeSenderObject {

	/**
	 * 终端列表
	 */
	private List<TerimalModel> telIds;
	/**
	 * 结果反馈 地址
	 */
	private String notify_url;
	
	/**
	 * 回调用：指定对应的codeid
	 */
	private String codeId;
	
	
	/**
	 * 指令内容
	 */
	private String cmdContents;

	

	public List<TerimalModel> getTelIds() {
		return telIds;
	}

	public void setTelIds(List<TerimalModel> telIds) {
		this.telIds = telIds;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getCodeId() {
		return codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	public String getCmdContents() {
		return cmdContents;
	}

	public void setCmdContents(String cmdContents) {
		this.cmdContents = cmdContents;
	}


	
	
	
	
	
}
