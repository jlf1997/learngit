package com.cimr.api.gk.model.mgr;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cimr.api.comm.Base;

@Table(name="td_sim_card_pay_history")
@Entity
@org.hibernate.annotations.Table(comment="sim卡缴费历史表", appliesTo = "td_sim_card_pay_history") 
public class SimCardPayHistory extends Base{

	
	@Id
	public String id;
	
	
	@ManyToOne
	@JoinColumn(name="sim_card_id",columnDefinition="varchar(255) COMMENT 'sim卡 '")
	public SimCard simCard; 
	
	
	/**
	 * 缴费金额
	 */
	@Column(name="account",columnDefinition="varchar(255) COMMENT '缴费金额'")
	public String account;
	
	
}
