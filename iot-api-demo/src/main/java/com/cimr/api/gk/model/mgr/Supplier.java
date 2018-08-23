package com.cimr.api.gk.model.mgr;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.cimr.api.comm.Base;




/**
 * 供应商
 * @author Administrator
 *
 */
@Table(name="td_supplier")
@org.hibernate.annotations.Table(comment="供应商表", appliesTo = "td_supplier") 
@Entity
public class Supplier extends Base{

	@Id
	@Column(name="id",columnDefinition="bigint COMMENT '主键 '")
	public Long id;
	
	/**
	 * 供应商名称
	 */
	public String supplierName;
	
	
	/**
	 * 具体地址
	 */
	public String address;
	
	@ManyToOne
	@JoinColumn(name="industry_id",columnDefinition="bigint COMMENT '行业类别 '")
	public Industry industry;

	
	@OneToMany(mappedBy="supplier",targetEntity=Terminal.class)
	public List<Terminal> terminals;
	
	
	
}
