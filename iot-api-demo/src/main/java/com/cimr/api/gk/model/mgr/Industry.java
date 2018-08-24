package com.cimr.api.gk.model.mgr;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.cimr.api.comm.Base;

@Table(name="tb_industry")
@Entity
@org.hibernate.annotations.Table(comment="行业类型字典表", appliesTo = "tb_industry") 
public class Industry extends Base{

	/**
	 * 行业名称
	 */
	@Column(name="industry_name",columnDefinition="varchar(50) COMMENT '行业名称 '")
	public String industryName;
	
	
	@Id
	public String id;
	
	@OneToMany(mappedBy="industry")
	public List<Supplier> suppliers;

	@OneToMany(mappedBy="industry")
	public List<ApplicationInfo> applicationInfos;
	
}
