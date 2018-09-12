	package com.cimr.api.gk.model.mgr;

import java.util.Date;
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

@Table(name="td_sys_user")
@Entity
@org.hibernate.annotations.Table(comment="用户表", appliesTo = "td_sys_user")
public class User extends Base{

	@Id
	public String id;
	
	@ManyToOne
	@JoinColumn(name="customer_id",columnDefinition="varchar(255) COMMENT '所属客户 '")
	public Customer customer;
	
	 @OneToMany(mappedBy="user")
	 public List<UserRole> userRoles;
	
	
	
	/**
	 * 用户登录名
	 * 生成租赁商时 需生成一个对应的管理员
	 */
	@Column(name="username",columnDefinition="varchar(100) COMMENT '登录名'")
	public String username;
	
	/**
	 * 密码
	 */
	@Column(name="pswd",columnDefinition="varchar(255) COMMENT '密码'")
	public  String pswd;
	/**
	 * 用户状态
	 */
	@Column(name="status",columnDefinition="int COMMENT '用户状态'")
	public Integer status;
	
	/**
	 * 用户名称
	 */
	@Column(name="fullname",columnDefinition="varchar(255) COMMENT '用户名称'")
	public String fullname;
	/**
	 * 用户电话
	 */
	@Column(name="phone",columnDefinition="varchar(20) COMMENT '用户电话'")
	public String phone;
	
	
	/**
	 * 备注
	 */
	@Column(name="comment",columnDefinition="varchar(255) COMMENT '备注'")
	public String comment;
	
	/**
	 * 上次登录时间
	 */
	@Column(name="last_login_time",columnDefinition="datetime COMMENT '上次登录时间'")
	public Date lastLoginTime;
	
	/**
	 * 排序id
	 */
	@Column(name="order_id",columnDefinition="float COMMENT '排序id'")
	public Float orderId;
	/**
	 * 头像地址
	 */
	@Column(name="avatar",columnDefinition="varchar(255) COMMENT '头像'")
	public String avatar;
	
	
	@ManyToOne
	@JoinColumn(name="area_id",columnDefinition="varchar(255) COMMENT '地区信息 '")
	public Area area;
	
}
