package com.cimr.sysmanage.service;

import com.cimr.comm.base.BaseService;
import com.cimr.sysmanage.dto.UserDto;
import com.cimr.sysmanage.model.User;

import java.util.List;

public interface UserService extends BaseService<User, String> {
	
	//登录
	User login(String username, String password);

	//通过用户名获取用户
	User getByUsername(String username);

	//保存用户和角色
	int saveUserAndRoles(String userId, String[] roleIdsArray);

	/**
	 * 用户列表:查询用户所在组织的所有下级组织的用户
     * 必须传入用户的id
	 * @param user
	 * @return
	 */
	List<User> selectUserListByChildGroups(UserDto user);
//
    /**
     * 用户列表:查询用户所在组织的所有上级组织的用户
     * 必须传入用户的id
     * @param user
     * @return
     */
	List<User> selectUserListByParentGroups(UserDto user);
	}
