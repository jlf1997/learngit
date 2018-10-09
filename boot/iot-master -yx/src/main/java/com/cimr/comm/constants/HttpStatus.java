package com.cimr.comm.constants;

public enum HttpStatus {
	SUCCESS("SUCCESS", "成功"), FAIL("FAIL", "失败");

	private String key;
	private String name;

	private HttpStatus(String key, String name) {
		this.key = key;
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
