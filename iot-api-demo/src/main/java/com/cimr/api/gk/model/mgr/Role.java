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

@Table(name="td_sys_role")
@Entity
@org.hibernate.annotations.Table(comment="角色表", appliesTo = "td_sys_role") 
public class Role extends Base{

	@Id
	public Long id;
	
	
	
	@ManyToMany
	public List<User> users;
	
	@ManyToOne
	@JoinColumn(name="customer_id",columnDefinition="bigint COMMENT '所属客户 '")
	public Customer customer;
	
	 @ManyToMany(mappedBy="roles")
	 public List<Permission> permissions;
	
	/**
	 * 角色名称
	 */
	public String roleName;
	
	
}
