package com.cimr.boot.aop;

/**
 * 接口访问记录
 * @author Administrator
 *
 */

public class ApiAccessLog {
	
	

	private Long id;
	/**
	 * 调用方法名
	 */
	private String apiName;
	
	private String causes;
	
	/**
	 * 调用结果
	 */
	private boolean res;
	
	/**
	 * 调用消耗时间
	 */
	private Long time;
	
	
	/**
	 * 返回对象 json格式
	 */
	private String result;
	
	/**
	 * 请求url
	 */
	private String url;
	
	/**
	 * url请求参数
	 */
	private String queryString;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	public String getCauses() {
		return causes;
	}

	public void setCauses(String causes) {
		this.causes = causes;
	}

	public boolean isRes() {
		return res;
	}

	public void setRes(boolean res) {
		this.res = res;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	
	
	
	
	
	

}
