package com.cimr.sysmanage.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.cimr.comm.base.BaseDao;
import com.cimr.sysmanage.model.RolePermission;

public interface RolePermissionDao extends BaseDao<RolePermission, String> {
	
	int insertRoleAndPermissions(List<RolePermission> paramList);
	  
	int deleteRoleAndPermissionsByIds(@Param("ids") String[] paramArrayOfString);
	  
	int deleteRoleAndPermissionsByRoleId(@Param("roleId") String paramString);
	  
	List<RolePermission> getRoleAndPermissionsByRoleId(@Param("roleId") String paramString1, @Param("permissionType") String paramString2);
}
