package com.cimr.boot.redis.utils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.data.redis.hash.HashMapper;

public class MyBeanUtilsHashMapper <T> implements HashMapper<T, String, String> {
	private Class<T> type;

	public MyBeanUtilsHashMapper(Class<T> type) {
		this.type = type;
	}

	public T fromHash(Map<String, String> hash) {

		T instance = org.springframework.beans.BeanUtils.instantiate(type);
		try {
			MyBeanUtils.populate(instance, hash);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.redis.hash.HashMapper#toHash(java.lang.Object)
	 */
	@Override
	public Map<String, String> toHash(T object) {
		try {

			Map<String, String> map = MyBeanUtils.describe(object);

			Map<String, String> result = new LinkedHashMap<String, String>();
			for (Entry<String, String> entry : map.entrySet()) {
				if (entry.getValue() != null) {
					result.put(entry.getKey(), entry.getValue());
				}
			}

			return result;

		} catch (Exception ex) {
			throw new IllegalArgumentException("Cannot describe object " + object, ex);
		}
	}
}
