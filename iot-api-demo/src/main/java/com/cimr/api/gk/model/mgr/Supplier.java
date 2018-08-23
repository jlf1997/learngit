package com.cimr.api.gk.model.mgr;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;




/**
 * 供应商
 * @author Administrator
 *
 */
@Table(name="td_supplier")
@org.hibernate.annotations.Table(comment="表注释", appliesTo = "td_supplier") 
@Entity
public class Supplier extends BaseEntity{

	@Id
	@Column(name="id",columnDefinition="bigint COMMENT '主键 '")
	public Long id;
	
	public String supplierName;
	
	
	public String address;
	
	@ManyToOne
	@JoinColumn(name="industry_id",columnDefinition="bigint COMMENT '行业类别 '")
	public Industry industry;

	

	
	
	
}
