package com.cimr.sysmanage.service;

import java.util.List;

import com.cimr.comm.base.BaseService;
import com.cimr.sysmanage.model.Group;

public interface GroupService extends BaseService<Group, String> {
	
	List<Group> getGroupAndSonsByGroupId(String paramString);

	//根据用户ID获取组织列表
	List<Group> getGroupListByUserId(String userId, String groupId);

	/**
	 * 根据条件查询组织所有子级组织
	 * @param groupName
	 * @return
	 */
	List<Group> selectSon_common(String groupId,String groupName);

	/**
	 * 根据条件查询组织所有父级组织
	 * @param groupId
	 * @return
	 */
	List<Group> selectFather_common(String groupId);

	/**
	 * 修改时根据条件查询组织所有父级组织
	 * @param groupId
	 * @return
	 */
	List<Group> selectUpdate_common(String groupId,String updateId);

	/**
	 * 组织重名判断
	 * @param groupName
	 * @return
	 */
	boolean isGroupName(String groupName);
}
