package com.cimr.master.comm.constants;

public enum MenuTargetEnum {
	SELF("_self", "本页面"), BLANK("_blank", "新页面");

	private String key;
	private String name;

	private MenuTargetEnum(String key, String name) {
		this.key = key;
		this.name = name;
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
