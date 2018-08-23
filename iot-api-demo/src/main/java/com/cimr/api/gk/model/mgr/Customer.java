package com.cimr.api.gk.model.mgr;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.cimr.api.comm.Base;
import com.cimr.api.comm.BaseEntity;


@Table(name="td_customer")
@Entity
@org.hibernate.annotations.Table(comment="客户表", appliesTo = "td_customer") 
public class Customer extends Base{

	/**
	 * 用户id
	 */
	@Id
	private Long id;
	
	/**
	 * 用户编号
	 */
	public String userNum;
	
	/**
	 * 客户名称
	 */
	public String userName;
	
	
	@ManyToMany(mappedBy="customers",targetEntity=ApplicationInfo.class)
	public List<ApplicationInfo> applicationInfos;
	
	
	
	@OneToMany(mappedBy="customer",targetEntity=Terminal.class)
	public List<Terminal> terminals;
	
	
	
	
	/**
	 * 当组织删除时 所有角色 用户全部删除
	 */
	@OneToMany(mappedBy="customer")
	public List<User> users;
	
	@OneToMany(mappedBy="customer")
	public List<Role> roles;
	
	
	
	@ManyToOne
	@JoinColumn(name="area_id",columnDefinition="varchar(255) COMMENT '终端类型信息 '")
	public Area area;
	
	/**
	 * 客户类型
	 */
	public Integer type;
	
	/**
	 * 父节点id
	 */
	public Long parentId;
}
