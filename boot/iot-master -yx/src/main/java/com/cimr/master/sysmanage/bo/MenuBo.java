package com.cimr.master.sysmanage.bo;

import java.io.Serializable;
import java.util.List;

public class MenuBo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String menuKey;
	private String menuName;
	private String parentId;
	private Integer level;
	private String comment;
	private Float orderId;
	private String target;
	private String href;

	private String menuIcon;
	private List<MenuBo> children;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMenuKey() {
		return this.menuKey;
	}

	public void setMenuKey(String menuKey) {
		this.menuKey = menuKey;
	}

	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Float getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Float orderId) {
		this.orderId = orderId;
	}

	public String getTarget() {
		return this.target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getHref() {
		return this.href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public List<MenuBo> getChildren() {
		return this.children;
	}

	public void setChildren(List<MenuBo> children) {
		this.children = children;
	}

	public String getMenuIcon() {
		return menuIcon;
	}

	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}
}
