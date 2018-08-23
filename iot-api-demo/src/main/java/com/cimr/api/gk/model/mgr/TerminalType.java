package com.cimr.api.gk.model.mgr;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.cimr.api.comm.Base;
import com.cimr.api.comm.BaseEntity;

@Table(name="tb_terminal_type")
@Entity
@org.hibernate.annotations.Table(comment="终端类型字典表", appliesTo = "tb_terminal_type") 
public class TerminalType extends Base{

	@Id
	public Long id;
	
	@OneToMany(mappedBy="terminalType",targetEntity=Terminal.class)
	public List<Terminal> terminals;
	
	/**
	 * 终端型号
	 */
	@Column(name="type",columnDefinition="varchar(255) COMMENT '终端型号 '",nullable=false)
	public String type;
	
	/**
	 * 类型名称
	 */
	@Column(name="typeName",columnDefinition="varchar(255) COMMENT '类型名称 '",nullable=false)
	public String typeName;
	
	
	/**
	 * 型号参数
	 */
	@Column(name="typeParm",columnDefinition="varchar(255) COMMENT '型号参数 '")
	public String typeParm;
	
	/**
	 * 状态
	 */
	@Column(name="status",columnDefinition="int COMMENT '状态 '",nullable=false)
	public Integer status;
	
	
}
