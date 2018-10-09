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
	@Column(name="id",columnDefinition="varchar(255) COMMENT '主键 '")
	public String id;
	
	/**
	 * 供应商名称
	 */
	@Column(name="supplier_name",columnDefinition="varchar(255) COMMENT '供应商名称'")
	public String supplierName;
	
	@Column(name="province",columnDefinition="varchar(10) COMMENT '省份编码'")
	public String province;
	@Column(name="city",columnDefinition="varchar(10) COMMENT '市编码'")
	public String city;
	@Column(name="area",columnDefinition="varchar(10) COMMENT '区域编码'")
	public String area;
	/**
	 * 具体地址
	 */
	@Column(name="address",columnDefinition="varchar(255) COMMENT '具体地址'")
	public String address;
	
	@Column(name="industry",columnDefinition="varchar(20) COMMENT '所属行业'")
	public String industry;
	
	@Column(name="addr_lat",columnDefinition="double COMMENT '纬度'")
	public double addr_lat;
	
	@Column(name="addr_lng",columnDefinition="double COMMENT '经度'")
	public double addr_lng;

	
	@OneToMany(mappedBy="supplier",targetEntity=Terminal.class)
	public List<Terminal> terminals;
	
	@Column(name="contacts_name",columnDefinition="varchar(50) COMMENT '联系人名称'")
	public String contactsName;
	
	@Column(name="contacts_phone",columnDefinition="varchar(50) COMMENT '联系人电话'")
	public String contactsPhone;
	
	@Column(name="charge_name",columnDefinition="varchar(50) COMMENT '负责人名称'")
	public String chargeName;
	
	@Column(name="charge_phone",columnDefinition="varchar(50) COMMENT '负责人电话'")
	public String chargePhone; 
	
	
}
