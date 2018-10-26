package com.cimr.master.comm.constants;

public enum PermissionTypeEnum {
	MENU("menu", "菜单"), OPERATION("operation", "操作");

	private String key;
	private String name;

	private PermissionTypeEnum(String key, String name) {
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
