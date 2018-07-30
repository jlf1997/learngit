package com.cimr.api.comm.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("iot.sv.info")
public class ProjectPropertities {

	private String projectId ="P00001";
	
	private String singalRealData = "";
	
	private String singalFault = "503447856";

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getSingalRealData() {
		return singalRealData;
	}

	public void setSingalRealData(String singalRealData) {
		this.singalRealData = singalRealData;
	}

	public String getSingalFault() {
		return singalFault;
	}

	public void setSingalFault(String singalFault) {
		this.singalFault = singalFault;
	}
	
	
}
