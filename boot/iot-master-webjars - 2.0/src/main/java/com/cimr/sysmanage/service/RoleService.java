package com.cimr.sysmanage.service;

import java.util.List;
import java.util.Set;

import com.cimr.comm.base.BaseService;
import com.cimr.sysmanage.model.Role;
import com.cimr.util.Assist;

public interface RoleService extends BaseService<Role, String> {
	/**
	 * 通过用户查找角色
	 * @param paramString
	 * @return
	 */
	Set<String> findRoleByUserId(String paramString);

	/**
	 * 查找列表
	 * @param paramString
	 * @return
	 */
	List<Role> selectList(String paramString);

	/**
	 * 通过key查找角色
	 * @param paramString
	 * @return
	 */
	List<Role> getRoleListByKey(String paramString);

	/**
	 * 通过名称查找角色
	 * @param paramString
	 * @param paramArrayOfString
	 * @return
	 */
	List<Role> getRoleListWithName(String paramString, String[] paramArrayOfString);

	/**
	 * 通过用户名查找角色
	 * @param paramString
	 * @return
	 */
	List<Role> getRoleListByUsername(String paramString);

	/**
	 * 通过用户Id查找角色
	 * @param paramString
	 * @return
	 */
	List<Role> getRoleListByUserId(String paramString);

	/**
	 * 保存角色菜单关系
	 * @param id
	 * @param menuIdsArray
	 * @return
	 */
	int saveRoleAndMenus(String id, String[] menuIdsArray);

	/**
	 * 保存角色操作关系
	 * @param id
	 * @param operationIdsArray
	 * @return
	 */
	int saveRoleAndOperations(String id, String[] operationIdsArray);
	  
	/**
	 * 角色列表:查询用户所在组织的所有下级组织的用户
	 * 必须传入用户的id
	 * @param role
	 * @return
	 */
	List<Role> selectRoleListByChildGroups(Role role);

	/**
	 * 角色列表:查询用户所在组织的所有上级组织的用户
	 * 必须传入用户的id
	 * @param role
	 * @return
	 */
	List<Role> selectRoleListByParentGroups(Role role);

	/**
	 * 查询角色列表
	 * @return
	 */
	List<Role> selectObj_commons(Assist assist);
}
