package com.cimr.sysmanage.dto;

import java.util.List;

/**
 * Created by admin on 2018/3/19.
 */
public class RoleTree {
	private String id;
	private String name;
	private Integer level;
	private List<RoleTree> children;
	private List<RoleDto> subChildren;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public List<RoleTree> getChildren() {
		return children;
	}

	public void setChildren(List<RoleTree> children) {
		this.children = children;
	}

	public List<RoleDto> getSubChildren() {
		return subChildren;
	}

	public void setSubChildren(List<RoleDto> subChildren) {
		this.subChildren = subChildren;
	}
}
