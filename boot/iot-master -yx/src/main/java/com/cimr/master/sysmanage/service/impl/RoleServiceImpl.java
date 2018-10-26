package com.cimr.master.sysmanage.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimr.comm.constants.PermissionTypeEnum;
import com.cimr.sysmanage.dao.RoleDao;
import com.cimr.sysmanage.model.Role;
import com.cimr.sysmanage.model.RolePermission;
import com.cimr.sysmanage.model.User;
import com.cimr.sysmanage.model.UserRole;
import com.cimr.sysmanage.service.GroupService;
import com.cimr.sysmanage.service.PermissionService;
import com.cimr.sysmanage.service.RolePermissionService;
import com.cimr.sysmanage.service.RoleService;
import com.cimr.sysmanage.service.UserRoleService;
import com.cimr.sysmanage.service.UserService;
import com.cimr.util.Assist;
import com.cimr.util.PageData;
import com.cimr.util.StringUtil;
import com.cimr.util.UserUtil;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao dao;

	@Autowired
	private PermissionService permissionService;

	@Autowired
	private GroupService groupService;

	@Autowired
	private RolePermissionService rolePermissionService;

	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private UserService userService;

	public Set<String> findRoleByUserId(String userId) {
		Set<String> roleKeySet = new HashSet<>();
		Assist assist = new Assist();
		assist.setRequires(Assist.andEq("user_id", userId));
		List<UserRole> userRoleList = userRoleService.selectObj_common(assist);
		Role role = null;
		for (UserRole userRole : userRoleList) {
			role = this.selectObjById_common(userRole.getRoleId());
			if (null != role) {
				roleKeySet.add(role.getRoleKey());
			}
		}
		return roleKeySet;
	}

	public List<Role> selectList(String roleKey) {
		/*User currentUser = TokenUtil.getToken();*/
		String userId = UserUtil.getLoginUserId();
		User currentUser = userService.selectObjById_common(userId);
		if("0".equals(currentUser.getGroupId())){
			currentUser.setGroupId(null);
		}

		Assist assist = new Assist();
		assist.setRequires(Assist.andLike("role_key", roleKey));
		assist.setRequires(Assist.andEq("group_id", currentUser.getGroupId()));
		return this.selectObj_common(assist);
	}

	public List<Role> getRoleListByKey(String roleKey) {
		Assist assist = new Assist();
		assist.setRequires(Assist.andEq("role_key", roleKey));
		List<Role> roleList = this.selectObj_common(assist);
		return roleList;
	}

	public List<Role> getRoleListWithName(String roleName, String[] groupIds) {
		if (roleName != null) {
			roleName = roleName.trim();
		}
		if ((groupIds != null) && (groupIds.length > 0)) {
			Assist assist = new Assist();
			assist.setRequires(Assist.andLike("role_name", roleName));
			assist.setRequires(Assist.andIn("group_id", groupIds));
			return this.selectObj_common(assist);
		}
		return null;
	}

	/*public int insertRole(String id, String roleKey, String roleName, String tenantId, String projectId, String comment, Float orderId) {
		User currentUser = TokenUtil.getToken();
		if ((currentUser != null) && (currentUser.getGroupId() != null) && (!Objects.equals("", currentUser.getGroupId()))) {
			this.dao.insertObject(id, roleKey, roleName, tenantId, projectId, comment, orderId, currentUser.getGroupId());
		}
		return 0;
	}

	public int updateRole(String id, String roleKey, String roleName, String tenantId, String projectId, String comment, Float orderId) {
		User currentUser = TokenUtil.getToken();
		if ((currentUser != null) && (currentUser.getGroupId() != null) && (!Objects.equals("", currentUser.getGroupId()))) {
			this.dao.updateObject(id, roleKey, roleName, tenantId, projectId, comment, orderId, currentUser.getGroupId());
		}
		return 0;
	}

	public int deleteRoleById(String id) {
		int resultCount = 0;
		resultCount = this.dao.deleteObjectById(id);
		this.rolePermissionDao.deleteRoleAndPermissionsByRoleId(id);
		this.userRoleDao.deleteUserAndRolesByRoleId(id);
		return resultCount;
	}*/

	@Override
	public List<Role> getRoleListByUsername(String username) {
		Assist assist = new Assist();
		List<UserRole> userRoleList = null;
		List<Role> roleList = null;
		List<String> roleIdList = new ArrayList<>();
		User user = userService.getByUsername(username);
		if (null != user) {
			assist.setRequires(Assist.andEq("user_id", user.getId()));
			userRoleList = userRoleService.selectObj_common(assist);
			for (UserRole userRole : userRoleList) {
				roleIdList.add(userRole.getRoleId());
			}
			if (null != roleIdList && roleIdList.size() > 0) {
				assist.setRequires(Assist.andIn("id", roleIdList));
				roleList = this.selectObj_common(assist);
			}
		}
		return roleList;
	}

	@Override
	public List<Role> getRoleListByUserId(String userId) {
		Assist assist = new Assist();
		List<UserRole> userRoleList = null;
		List<Role> roleList = null;
		List<String> roleIdList = new ArrayList<>();
		User user = userService.selectObjById_common(userId);
		if (null != user) {
			assist.setRequires(Assist.andEq("user_id", user.getId()));
			userRoleList = userRoleService.selectObj_common(assist);
			for (UserRole userRole : userRoleList) {
				roleIdList.add(userRole.getRoleId());
			}
			if (null != roleIdList && roleIdList.size() > 0) {
				assist = new Assist();
				assist.setRequires(Assist.andIn("id", roleIdList));
				roleList = this.selectObj_common(assist);
			}
		}
		return roleList;
	}
	
	@Override
	public int saveRoleAndMenus(String id, String[] menuIdsArray) {
		int resultCount = 0;
		if (StringUtil.empty(id)) {
			return resultCount;
		}
		Assist assist = new Assist();
		//删除原来的权限设置
		assist.setRequires(Assist.andEq("role_id", id));
		assist.setRequires(Assist.customRequire("and permission_id in (select id from sys_permission where permission_type = ", PermissionTypeEnum.MENU.getKey(), ")"));
		rolePermissionService.deleteObj_common(assist);
	    
	    if (menuIdsArray == null || menuIdsArray.length == 0) {
	    	return resultCount;
	    }
	    //添加新的权限设置
	    List<RolePermission> rolePermissionsForAdd = new ArrayList<>();
	    RolePermission rolePermission = null;
	    for (String menuId : menuIdsArray) {
	    	rolePermission = new RolePermission();
	    	rolePermission.setRoleId(id);
	    	rolePermission.setPermissionId(menuId);
	    	rolePermissionsForAdd.add(rolePermission);
	    }
	    if (rolePermissionsForAdd.size() > 0) {
	    	resultCount = rolePermissionService.insertObjByBatch_common(rolePermissionsForAdd);
	    }
	    return resultCount;
	}
	
	@Override
	public int saveRoleAndOperations(String id, String[] operationIdsArray) {
		int resultCount = 0;
		if (StringUtil.empty(id)) {
			return resultCount;
		}
		Assist assist = new Assist();
		//删除原来的权限设置
		assist.setRequires(Assist.andEq("role_id", id));
		assist.setRequires(Assist.customRequire("and permission_id in (select id from sys_permission where permission_type = ", PermissionTypeEnum.OPERATION.getKey(), ")"));
		rolePermissionService.deleteObj_common(assist);
	    
		if (operationIdsArray == null || operationIdsArray.length == 0) {
	    	return resultCount;
	    }
	    //添加新的权限设置
	    List<RolePermission> rolePermissionsForAdd = new ArrayList<>();
	    RolePermission rolePermission = null;
	    for (String menuId : operationIdsArray) {
	    	rolePermission = new RolePermission();
	    	rolePermission.setRoleId(id);
	    	rolePermission.setPermissionId(menuId);
	    	rolePermissionsForAdd.add(rolePermission);
	    }
	    if (rolePermissionsForAdd.size() > 0) {
	    	resultCount = rolePermissionService.insertObjByBatch_common(rolePermissionsForAdd);
	    }
	    return resultCount;
	}

	@Override
	public List<Role> selectRoleListByChildGroups(Role role) {
		return dao.selectRoleListByChildGroups(role);
	}

	@Override
	public List<Role> selectRoleListByParentGroups(Role role) {
		return dao.selectRoleListByChildGroups(role);
	}

	@Override
	public List<Role> selectObj_commons(Assist assist) {
		return dao.selectObj_commons(assist);
	}
	/*public int saveRoleAndMenus(String roleId, String[] menuIds) {
		int resultCount = 0;
		List<RolePermission> rolePermissionList = this.rolePermissionDao.getRoleAndPermissionsByRoleId(roleId, PermissionTypeEnum.MENU.getKey());
		if (rolePermissionList.size() > 0) {
			String[] rolePermissionIdsForDel = new String[rolePermissionList.size()];

			for (int i = 0; i < rolePermissionList.size(); i++) {
				rolePermissionIdsForDel[i] = ((RolePermission) rolePermissionList.get(i)).getId();
			}

			this.rolePermissionDao.deleteRoleAndPermissionsByIds(rolePermissionIdsForDel);
		}

		if (menuIds == null) {
			return resultCount;
		}

		List<RolePermission> rolePermissionsForAdd = new java.util.ArrayList();
		for (int j = 0; j < menuIds.length; j++) {
			RolePermission rolePermission = new RolePermission();
			rolePermission.setId(com.cimr.comm.util.IdUtil.getId() + "");
			rolePermission.setRoleId(roleId);
			rolePermission.setPermissionId(menuIds[j]);
			rolePermissionsForAdd.add(rolePermission);
		}
		if (rolePermissionsForAdd.size() > 0) {
			resultCount = this.rolePermissionDao.insertRoleAndPermissions(rolePermissionsForAdd);
		}

		return resultCount;
	}

	public int saveRoleAndOperations(String roleId, String[] menuIds) {
		int resultCount = 0;
		List<RolePermission> rolePermissionList = this.rolePermissionDao.getRoleAndPermissionsByRoleId(roleId, PermissionTypeEnum.OPERATION.getKey());
		if (rolePermissionList.size() > 0) {
			String[] rolePermissionIdsForDel = new String[rolePermissionList.size()];

			for (int i = 0; i < rolePermissionList.size(); i++) {
				rolePermissionIdsForDel[i] = ((RolePermission) rolePermissionList.get(i)).getId();
			}

			this.rolePermissionDao.deleteRoleAndPermissionsByIds(rolePermissionIdsForDel);
		}

		if (menuIds == null) {
			return resultCount;
		}

		List<RolePermission> rolePermissionsForAdd = new java.util.ArrayList();
		for (int j = 0; j < menuIds.length; j++) {
			RolePermission rolePermission = new RolePermission();
			rolePermission.setId(com.cimr.comm.util.IdUtil.getId() + "");
			rolePermission.setRoleId(roleId);
			rolePermission.setPermissionId(menuIds[j]);
			rolePermissionsForAdd.add(rolePermission);
		}
		if (rolePermissionsForAdd.size() > 0) {
			resultCount = this.rolePermissionDao.insertRoleAndPermissions(rolePermissionsForAdd);
		}
		return resultCount;
	}
*/

	//公共方法-------开始
    
    @Override
	public long getObjRowCount_common(Assist assist) {
    	if (null == assist) {
    		assist = new Assist();
    	}
    	assist.setRequires(Assist.andEq("del_flag", 0));
		return dao.getObjRowCount_common(assist);
	}

	@Override
	public List<Role> selectObj_common(Assist assist) {
		if (null == assist) {
    		assist = new Assist();
    	}
		assist.setRequires(Assist.andEq("del_flag", 0));
		assist.setOrder(Assist.order("order_id, id", true));
		return dao.selectObj_common(assist);
	}
	
	@Override
	public PageData<Role> selectObj_common(Assist assist, int page, int limit) {
		if (null == assist) {
    		assist = new Assist();
    	}
		PageData<Role> pageData = new PageData<>();
		Long count = this.getObjRowCount_common(assist);
		int startRow = limit * (page - 1);
		assist.setStartRow(startRow);
		assist.setRowSize(limit);
		assist.setOrder(Assist.order("order_id, id", true));
		List<Role> list = dao.selectObj_common(assist);
		pageData.setCount(count.intValue());
		pageData.setList(list);
		return pageData;
	}

	@Override
	public Role selectObjByObj_common(Role obj) {
		return dao.selectObjByObj_common(obj);
	}

	@Override
	public Role selectObjById_common(String id) {
		return dao.selectObjById_common(id);
	}

	@Override
	public int insertObj_common(Role value) {
		value.preInsert();
		return dao.insertObj_common(value);
	}

	@Override
	public int insertNonEmptyObj_common(Role value) {
		value.preInsert();
		return dao.insertNonEmptyObj_common(value);
	}

	@Override
	public int insertObjByBatch_common(List<Role> value) {
		for (Role temp : value) {
			temp.preInsert();
		}
		return dao.insertObjByBatch_common(value);
	}

	@Override
	public int deleteObjById_common(String id) {
		Role temp = new Role();
		temp.setId(id);
		temp.setDelFlag(1);
		temp.preUpdate();
		return dao.updateNonEmptyObjById_common(temp);
	}

	@Override
	public int deleteObj_common(Assist assist) {
		Role temp = new Role();
		temp.setDelFlag(1);
		temp.preUpdate();
		return dao.updateNonEmptyObj_common(temp, assist);
	}

	@Override
	public int updateObjById_common(Role enti) {
		enti.preUpdate();
		return dao.updateObjById_common(enti);
	}

	@Override
	public int updateObj_common(Role value, Assist assist) {
		value.preUpdate();
		return dao.updateObj_common(value, assist);
	}

	@Override
	public int updateNonEmptyObjById_common(Role enti) {
		enti.preUpdate();
		return dao.updateNonEmptyObjById_common(enti);
	}

	@Override
	public int updateNonEmptyObj_common(Role value, Assist assist) {
		value.preUpdate();
		return dao.updateNonEmptyObj_common(value, assist);
	}

	@Override
	public int deleteObjById_common(Role enti) {
		return 0;
	}

	@Override
	public int deleteObj_common(Role enti, Assist assist) {
		return 0;
	}

	//公共方法-------结束
}
