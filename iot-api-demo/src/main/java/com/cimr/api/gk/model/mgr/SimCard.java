package com.cimr.api.gk.model.mgr;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Table
@Entity
public class SimCard extends BaseEntity{

	@Id
	public Long id;
	
	public String cardNum;
	
	/**
	 * 进货日期
	 */
	public Date purchaseDate;
	
	/**
	 * 提醒日期
	 */
	public Date RemindingDate;
	
	
	/**
	 * 卡状态
	 */
	public Integer status;
	
	
	@OneToOne(mappedBy="simCard")
	public Terminal terminal;
}
