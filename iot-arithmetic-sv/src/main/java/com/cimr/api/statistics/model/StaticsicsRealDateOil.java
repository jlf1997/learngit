package com.cimr.api.statistics.model;

import java.util.Date;

import com.cimr.api.statistics.model.base.StaticsicsRealDate;

public class StaticsicsRealDateOil extends StaticsicsRealDate{

	public StaticsicsRealDateOil() {
		this.FQ_DAY_OIL = 0;
		this.KQ_DAY_WORK = 0;
	}

	/**
	 * 日油耗量
	 */
	private Integer FQ_DAY_OIL;
	
	/**
	 * 日工作量
	 */
	private Integer KQ_DAY_WORK;
	
	private Long K_ENG_WORK;
	
	private Long K_DEV_WORK;
	
	private String terminalNo;
	
	private Date logTime;
	

	public Integer getFQ_DAY_OIL() {
		return FQ_DAY_OIL;
	}

	public void setFQ_DAY_OIL(Integer fQ_DAY_OIL) {
		FQ_DAY_OIL = fQ_DAY_OIL;
	}

	public Integer getKQ_DAY_WORK() {
		return KQ_DAY_WORK;
	}

	public void setKQ_DAY_WORK(Integer kQ_DAY_WORK) {
		KQ_DAY_WORK = kQ_DAY_WORK;
	}

	public Long getK_ENG_WORK() {
		return K_ENG_WORK;
	}

	public void setK_ENG_WORK(Long k_ENG_WORK) {
		K_ENG_WORK = k_ENG_WORK;
	}

	public Long getK_DEV_WORK() {
		return K_DEV_WORK;
	}

	public void setK_DEV_WORK(Long k_DEV_WORK) {
		K_DEV_WORK = k_DEV_WORK;
	}

	public String getTerminalNo() {
		return terminalNo;
	}

	public void setTerminalNo(String terminalNo) {
		this.terminalNo = terminalNo;
	}

	public Date getLogTime() {
		return logTime;
	}

	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}

	


	
	

}
