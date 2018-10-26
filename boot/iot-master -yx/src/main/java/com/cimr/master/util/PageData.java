package com.cimr.master.util;

import java.util.List;

/**
 * 类描述:分页对象
 * 作者:admin
 * 创建时间:2018年4月20日 下午3:48:27
 */
public class PageData<T> {

	private int count;
	
	private List<T> list;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
	
}
