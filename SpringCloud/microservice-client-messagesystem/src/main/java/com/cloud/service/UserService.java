package com.cloud.service;

import java.util.List;

import com.cloud.entity.User;
/**
 * 查询用户数据业务
 * @author Administrator
 *
 */
public interface UserService {
	/**
	 * 根据userstation表里的stype 用户类型字段 获取所有用户数据
	 * @param stype 用户类型 1 商砼 2 物流
	 * @return
	 */
	public List<User> findList(String stype);
}