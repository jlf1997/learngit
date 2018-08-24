package com.cimr.api.gk.model.mgr;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.cimr.api.comm.Base;
import com.cimr.api.comm.BaseEntity;

@Table(name="td_terminal")
@Entity
@org.hibernate.annotations.Table(comment="终端表", appliesTo = "td_terminal") 
public class Terminal extends Base{

	@Id
	public String id;
	
	@ManyToOne
	@JoinColumn(name="appliction_id",columnDefinition="varchar(255) COMMENT '所属应用 '")
	public ApplicationInfo applicationInfo;
	
	@ManyToOne
	@JoinColumn(name="customer_id",columnDefinition="varchar(255) COMMENT '所属客户 '")
	public Customer customer;
	
	@OneToOne
	@JoinColumn(name="sim_card_id",columnDefinition="varchar(255) COMMENT 'sim卡 '")
	public SimCard simCard;
	
	@ManyToOne
	@JoinColumn(name="supplier_id",columnDefinition="varchar(255) COMMENT '供应商信息 '")
	public Supplier supplier;
	
	@ManyToOne
	@JoinColumn(name="terminal_type_id",columnDefinition="varchar(255) COMMENT '终端类型信息 '")
	public TerminalType terminalType;
	
	
	/**
	 * 状态
	 */
	@Column(name="status",columnDefinition="int COMMENT '状态 '",nullable=false)
	public Integer status;
	
	
}
