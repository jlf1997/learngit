package com.cimr.api.comm.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("iot.sv.info")
public class ProjectPropertities {
	
	public static Long OIL = 1L;
	
	public static Long FAULT = 2L;
	
	private Long role = 0L;

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

	public Long getRole() {
		return role;
	}

	public void setRole(Long role) {
		this.role = role;
	}

	
	public boolean isAllRole() {
		if(role.equals(-1L)) {
			return true;
		}else {
			return false;
		}
	}
	
	
	
}
