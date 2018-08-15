package com.cimr.api.statistics.model;

import java.util.Date;

import javax.persistence.Id;

public class TerminalRealDataStatistics {

	@Id
	private Long id;
	
	/**
	 * 油耗量
	 */
	private String FQ_OIL;
	
	/**
	 * 终端id
	 */
	private String treId;
	/**
	 * 统计日期
	 */
	private Date statisticsDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFQ_OIL() {
		return FQ_OIL;
	}

	public void setFQ_OIL(String fQ_OIL) {
		FQ_OIL = fQ_OIL;
	}

	public Date getStatisticsDate() {
		return statisticsDate;
	}

	public void setStatisticsDate(Date statisticsDate) {
		this.statisticsDate = statisticsDate;
	}

	public String getTreId() {
		return treId;
	}

	public void setTreId(String treId) {
		this.treId = treId;
	}
	
	
	
	
}
