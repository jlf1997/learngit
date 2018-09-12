package com.cimr.boot.jpafinder;

public enum Oper {

	/** 等于*/
	 EQ ,
	 /** like*/
	 LIKE ,
	 /** 大于等于*/
	 GE,
	 /** 小于等于*/
	 LE,
	 /** 小于*/
	 LT,
	 /** 大于*/
	 GT,
	 /** 二进制位 代表状态 存在某一个*/
	 BIT_EXIST_ANY,
	 /** 二进制位 代表状态 某些不存在*/
	 BIT_NOT_EXIST_ALL,
	 /** 二进制位 代表状态 均存在*/
	 BIT_EXIST_ALL,
	 /** 二进制位 代表状态 均不存在*/
	 BIT_NOT_EXIST_ANY,
	 /** 不等于*/
	 NOT_EQUAL,
	 /** 范围*/
	 BETWEEN,
	 /** 空值*/
	 IS_NULL
	 
	
}
