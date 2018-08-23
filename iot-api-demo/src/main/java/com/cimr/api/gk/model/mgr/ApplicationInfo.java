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

@Table(name="tb_application_info")
@Entity
@org.hibernate.annotations.Table(comment="应用表", appliesTo = "tb_application_info") 
public class ApplicationInfo extends Base{

	@Id
	public Long id;
	
	
	/**
	 * 项目名称
	 */
	@Column(name="app_name",columnDefinition="varchar(255) COMMENT '项目名称 '",nullable=false,unique=true)
	public String appName;
	
	@Column(name="app_no",columnDefinition="varchar(255) COMMENT '项目编号 '",nullable=false,unique=true)
	public String appNo;
	
	@ManyToMany
	public List<Customer> customers;
	
	@OneToMany(mappedBy="applicationInfo",targetEntity=Terminal.class)
	public List<Terminal> terminals;
	
	
	@ManyToOne
	@JoinColumn(name="industry_id",columnDefinition="bigint COMMENT '行业类别 '")
	public Industry industry;
	
	/**
	 * 备注
	 */
	@Column(name="remark",columnDefinition="tinytext COMMENT '备注 '")
	public String remark;
	
	/**
	 * 负责人电话
	 */
	@Column(name="headPhone",columnDefinition="tinytext COMMENT '负责人电话 '",nullable=false)
	public String headPhone;
	
	/**
	 * 负责人姓名
	 */
	@Column(name="headName",columnDefinition="tinytext COMMENT '负责人姓名 '",nullable=false)
	public String headName;
	
	
	
	
	
}
