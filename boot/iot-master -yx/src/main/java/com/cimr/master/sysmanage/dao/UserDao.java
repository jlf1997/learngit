package com.cimr.sysmanage.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.cimr.comm.base.BaseDao;
import com.cimr.sysmanage.model.User;

public abstract interface UserDao extends BaseDao<User, String> {
	
//	User getByUsername(@Param("username") String paramString);
//
//	User login(Map<String, Object> paramMap);
//
//	List<User> selectList(@Param("fullname") String paramString, @Param("groupIds") String[] paramArrayOfString);
//
//	User getUserById(@Param("id") String paramString);
//
//	int insertObject(@Param("id") String paramString1, @Param("username") String paramString2, @Param("pswd") String paramString3,@Param("tenantId") String tenantId,@Param("projectId") String projectId, @Param("status") int paramInt, @Param("fullname") String paramString4, @Param("phone") String paramString5, @Param("email") String paramString6, @Param("comment") String paramString7, @Param("orderId") Float paramFloat, @Param("theme") String paramString8, @Param("avatar") String paramString9);
//
//	int updateObject(@Param("id") String paramString1, @Param("username") String paramString2, @Param("pswd") String paramString3, @Param("tenantId") String tenantId,@Param("projectId") String projectId,@Param("status") int paramInt, @Param("fullname") String paramString4, @Param("phone") String paramString5, @Param("email") String paramString6, @Param("comment") String paramString7, @Param("orderId") Float paramFloat, @Param("theme") String paramString8, @Param("avatar") String paramString9);
//
//	int updateUserTheme(@Param("id") String paramString1, @Param("theme") String paramString2);
//
//	int deleteObjectById(@Param("id") String paramString);
//
//	User verifyUsernamePassword(@Param("username") String paramString1, @Param("pswd") String paramString2);
//
//	int updatePassword(@Param("username") String paramString1, @Param("pswd") String paramString2);
//
//	List<User> selectUserList(User user);

	/**
	 * 用户列表:查询用户所在组织的所有下级组织的用户
	 * 必须传入用户的id
	 * @param user
	 * @return
	 */
	List<User> selectUserListByChildGroups(User user);

	/**
	 * 用户列表:查询用户所在组织的所有上级组织的用户
	 * 必须传入用户的id
	 * @param user
	 * @return
	 */
	List<User> selectUserListByParentGroups(User user);
}
