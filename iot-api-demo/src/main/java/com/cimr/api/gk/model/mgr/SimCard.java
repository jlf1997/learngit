package com.cimr.api.gk.model.mgr;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.cimr.api.comm.Base;

@Table(name="td_sim_card")
@Entity
@org.hibernate.annotations.Table(comment="sim卡表", appliesTo = "td_sim_card") 
public class SimCard extends Base{

	@Id
	public Long id;
	
	/**
	 * sim卡号
	 */
	public String cardNum;
	
	/**
	 * 进货日期
	 */
	public Date purchaseDate;
	
	/**
	 * 说明：计划提醒日期格式 MMdd 0812
	 * 默认值：进货日期的3月后（暂定）
	 * 触发已提醒事件：提醒状态改为已提醒
	 * 默认查询需要满足：1.未交费 2.提醒状态为未提醒 3.提醒日期（当前年份+计划提醒的月 日）在当前日期之前或当天的
	 */
	public String RemindingDate;
	
	/**
	 * 说明：冗余存储上次缴费日期
	 * 初始值：空
	 * 触发已缴费事件： 将提醒状态改为未提醒
	 * 判断是否缴费：如果当前日期-上次缴费日期>1年---未交费 ；否则----已缴费
	 */
	public Date nextPayTime;
	
	/**
	 *  说明：提醒状态 0---未提醒 ；1----已提醒 
	 *  初始值：未提醒0
	 */
	public Integer remindStatus;
	
	
	/**
	 * 卡状态
	 */
	public Integer status;
	
	
	@OneToOne(mappedBy="simCard")
	public Terminal terminal;
	
	
	/**
	 * SIM卡缴费历史记录
	 */
	@OneToMany(mappedBy="simCard")
	public List<SimCardPayHistory> simCardPayHistorys;
}
