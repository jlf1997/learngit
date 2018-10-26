package com.cimr.master.comm.base;

import java.io.Serializable;
import java.util.Date;

import com.cimr.util.UUIDGenerator;
import com.cimr.util.UserUtil;
 
/**
 * 类描述:实体类共有属性
 * 作者:admin
 * 创建时间:2018年4月16日 下午4:02:27
 */
public abstract class BaseEntity implements Serializable{
 
	private static final long serialVersionUID = 1L;
	
	private String id;

	private String createBy;
	
	private Date createTime;
	
	private String updateBy;
	
	private Date updateTime;
	
	private Integer delFlag;
	
	/**
	 * 在插入记录前,填充实体记录 
	 */
	public void preInsert(){
		if (UserUtil.isLogin()) {
			this.createBy = UserUtil.getLoginUserId();
		} else {
			this.createBy = "1";
		}
		this.delFlag = 0;
		this.createTime = new Date();
		//统一id样式，在此处插入id
		setId(UUIDGenerator.createUUID());		 
	}

	/**
	 * 在修改记录前，填充实体记录
	 */
	public void preUpdate(){
		if (UserUtil.isLogin()) {
			this.updateBy = UserUtil.getLoginUserId();
		} else {
			this.updateBy = "1";
		}
		this.updateTime = new Date();
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

}
