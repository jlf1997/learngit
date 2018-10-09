package com.cimr.api.gk.model.mgr;

import java.util.List;

import javax.persistence.Column;
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
	private String id;
	
	/**
	 * 用户编号
	 */
	@Column(name="user_num",columnDefinition="varchar(100) COMMENT '用户编号 '",nullable=false)
	public String userNum;
	
	/**
	 * 客户名称
	 */
	@Column(name="user_name",columnDefinition="varchar(100) COMMENT '客户名称 '",nullable=false)
	public String userName;
	
	
	
	
	@OneToMany(mappedBy="customer")
	public List<ApplicationCustomer> applicationCustomers;
	
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
	@JoinColumn(name="area_id",columnDefinition="varchar(255) COMMENT '地区信息 '")
	public Area area;
	
	/**
	 * 客户类型
	 */
	@Column(name="type",columnDefinition="int COMMENT '客户类型 '",nullable=false)
	public Integer type;
	
	/**
	 * 父节点id
	 */
	@Column(name="parent_id",columnDefinition="varchar(255) COMMENT '父节点id '",nullable=false)
	public String parentId;
	
	
}
