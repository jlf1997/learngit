package com.cloud.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.dao.UserDao;
import com.cloud.entity.User;
import com.cloud.service.UserService;
/**
 * 查询用户数据业务实现
 * @author Administrator
 *
 */
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserDao userDao;
	
	/**
	 * 根据的stype 用户类型字段 获取所有用户数据
	 * @param stype 用户类型 1 2 
	 * @return
	 */
	@Override
	public List<User> findList(String stype) {
		return userDao.findList(stype);
	}

}
