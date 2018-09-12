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

@Table(name="td_sys_role")
@Entity
@org.hibernate.annotations.Table(comment="角色表", appliesTo = "td_sys_role") 
public class Role extends Base{

	@Id
	public String id;
	
	
	
	 @OneToMany(mappedBy="role")
	 public List<UserRole> userRoles;
	
	@ManyToOne
	@JoinColumn(name="customer_id",columnDefinition="varchar(255) COMMENT '所属客户 '")
	public Customer customer;
	
	 @OneToMany(mappedBy="role")
	 public List<PermissionRole> permissionRoles;
	
	/**
	 * 角色名称
	 */
    @Column(name="role_name",columnDefinition="varchar(20) COMMENT '角色名称 '")
	public String roleName;
	
	/**
	 * 角色关键字
	 */
    @Column(name="role_key",columnDefinition="varchar(20) COMMENT '角色关键字 '")
	public String roleKey;

	/**
	 * 说明
	 */
    @Column(name="comment",columnDefinition="varchar(100) COMMENT '说明 '")
	public String comment;
	
	
	/**
	 * 排序值
	 */
    @Column(name="order_id",columnDefinition="float COMMENT '排序值 '")
	public Float orderId;
	
	
}
