package com.cimr.sysmanage.dao;

import com.cimr.comm.base.BaseDao;
import com.cimr.sysmanage.model.Role;
import com.cimr.util.Assist;

import java.util.List;

public interface RoleDao extends BaseDao<Role, String> {

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
