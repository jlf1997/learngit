package com.cimr.boot.redis.model;

import java.util.Set;

public class RedisPage {

	
	private Set<String> keys;
	
	private Integer count;

	public Set<String> getKeys() {
		return keys;
	}

	public void setKeys(Set<String> keys) {
		this.keys = keys;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
	
}
