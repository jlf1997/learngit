package com.cimr.sysmanage.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.cimr.comm.base.BaseDao;
import com.cimr.sysmanage.model.UserRole;

public interface UserRoleDao extends BaseDao<UserRole, String> {
	
	int insertUserAndRoles(List<UserRole> paramList);
	  
	int deleteUserAndRolesByIds(@Param("ids") String[] paramArrayOfString);
	  
	int deleteUserAndRolesByRoleId(@Param("roleId") String paramString);
	  
	List<UserRole> getUserAndRolesByUserId(@Param("userId") String paramString);
}
