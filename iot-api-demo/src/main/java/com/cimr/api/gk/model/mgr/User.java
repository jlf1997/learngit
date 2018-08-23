	package com.cimr.api.gk.model.mgr;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cimr.api.comm.Base;

@Table(name="td_sys_user")
@Entity
@org.hibernate.annotations.Table(comment="用户表", appliesTo = "td_sys_user")
public class User extends Base{

	@Id
	public Long id;
	
	@ManyToOne
	@JoinColumn(name="customer_id",columnDefinition="bigint COMMENT '所属客户 '")
	public Customer customer;
	
	@ManyToMany(mappedBy="users")
	public List<Role> roles;
	
	
	
	/**
	 * 生成租赁商时 需生成一个对应的管理员
	 */
	public String username;
	
	/**
	 * 
	 */
	public transient String pswd;
	
	public Integer status;
	
	
	public String fullname;
	
	public String phone;
	
	public String email;
	
	public String comment;
	
	public Date lastLoginTime;
	
	public Float orderId;
	
	
	public String avatar;
	
	
	
	
}
