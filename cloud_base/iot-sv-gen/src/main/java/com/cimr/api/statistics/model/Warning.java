package com.cimr.api.statistics.model;


/**
 * 
 * @author Administrator
 *
 */
public class Warning {
	
	/**
	 * 预警key
	 */
	private String warningKey;
	
	/**
	 * 预警状态 预警级别
	 */
	private Integer warningStatus;
	/**
	 * 项目id
	 */
	private String projectID;
	/**
	 * 终端id
	 */
	private String terminalId;
	/**
	 * 预警发生时间
	 */
	private Long gatherMsgTime;

	public String getWarningKey() {
		return warningKey;
	}

	public void setWarningKey(String warningKey) {
		this.warningKey = warningKey;
	}


	public Integer getWarningStatus() {
		return warningStatus;
	}

	public void setWarningStatus(Integer warningStatus) {
		this.warningStatus = warningStatus;
	}

	public String getProjectID() {
		return projectID;
	}

	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public Long getGatherMsgTime() {
		return gatherMsgTime;
	}

	public void setGatherMsgTime(Long gatherMsgTime) {
		this.gatherMsgTime = gatherMsgTime;
	}


	
	
	
}
