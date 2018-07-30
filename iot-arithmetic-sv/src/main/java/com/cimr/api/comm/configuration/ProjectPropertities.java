package com.cimr.api.comm.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("iot.sv.info")
public class ProjectPropertities {
	
	private String role = "";

	private String projectId ="P00001";
	
	private String singalOil = "503447824";
	
	private String singalFault = "503447856";

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}



	public String getSingalOil() {
		return singalOil;
	}

	public void setSingalOil(String singalOil) {
		this.singalOil = singalOil;
	}

	public String getSingalFault() {
		return singalFault;
	}

	public void setSingalFault(String singalFault) {
		this.singalFault = singalFault;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	
	
}
