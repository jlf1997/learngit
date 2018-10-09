package com.cimr.util;

import java.util.UUID;

/**
 * @类名: UUIDGenerator
 * @描述: 全球唯一标识生成器 该类用户生成全球唯一标识 UUIDGenerator
 * @版本: v1.0
 * @创建日期: 2014-11-10下午05:35:15
 * @JDK: 1.6
 */

public class UUIDGenerator {

	/**
	 * createUUID
	 * @描述: 随机生成UUID
	 * @创建时间: 2014-11-10下午05:35:15
	 * 
	 * @return UUID
	 */
	
	public static String createUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}
