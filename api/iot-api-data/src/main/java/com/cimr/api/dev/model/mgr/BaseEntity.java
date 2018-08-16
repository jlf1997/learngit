package com.cimr.api.dev.model.mgr;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
  
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity<T> implements java.io.Serializable{
 
	private static final long serialVersionUID = 1L;

	private String createBy;
	private Date createTime;
	/**
	 * 创建时间
	 */
	@CreatedDate	
	@Column(updatable=false)
	private String updateBy;
	
	/**
	 * 最后更新时间
	 */
	@LastModifiedDate
	private Date updateTime;
	private Integer delFlag;
	
	/**
	 * 在插入记录前,填充实体记录 
	 */
	public void preInsert(){
		//如果是统一id样式，可以在此处插入id
		//setId(IdGen.uuid());		 
		/*UserToken user = null;
		if (StringUtils.isNotBlank(user.getId())){
			this.updateBy = user;
			this.createBy = user;
		}*/
		this.updateTime = new Date();
		this.createTime = this.updateTime;
	}

	/**
	 * 在修改记录前，填充实体记录
	 */
	public void preUpdate(){
		/*User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getId())){
			this.updateBy = user;
		}*/
		this.updateTime = new Date();
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
