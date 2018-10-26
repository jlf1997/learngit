package com.cimr.sysmanage.service;

import java.util.List;
import java.util.Set;

import com.cimr.comm.base.BaseService;
import com.cimr.sysmanage.model.Permission;
import com.cimr.util.PageData;

public interface PermissionService extends BaseService<Permission, String> {
	/**
	 * 根据用户ID获取用户菜单,返回的是经过去重后的菜单ID集合
	 * @param paramString
	 * @return
	 */
	Set<String> findPermissionByUserId(String paramString);

	/**
	 * 根据用户ID获取用户菜单,返回的是经过去重后的菜单对象集合
	 * @param paramString
	 * @return
	 */
	List<Permission> getPermissionListByUserId(String paramString);

	List<Permission> getPermissionListWithName(String menuName, String type, String userId);
	
	PageData<Permission> getPermissionListWithName(String menuName, String type, String userId, int page, int limit);

	List<Permission> getPermissionsByKey(String key, String type);

	List<Permission> getMenuList();

	List<Permission> getMenuListByUserId(String userId);

	List<Permission> getMenuListByUsername(String username);

	List<Permission> getMenuListByRoleId(String roleId);

	List<Permission> getOperationListByUserId(String userId);

	List<Permission> getOperationListByUsername(String username);

	List<Permission> getOperationListByRoleId(String roleId);
	
	int deleteByPermissionId(String permissionId);

}
