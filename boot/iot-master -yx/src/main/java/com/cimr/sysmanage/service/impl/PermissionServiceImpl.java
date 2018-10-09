package com.cimr.sysmanage.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimr.comm.constants.PermissionTypeEnum;
import com.cimr.sysmanage.dao.PermissionDao;
import com.cimr.sysmanage.model.Permission;
import com.cimr.sysmanage.model.RolePermission;
import com.cimr.sysmanage.model.User;
import com.cimr.sysmanage.model.UserRole;
import com.cimr.sysmanage.service.PermissionService;
import com.cimr.sysmanage.service.RolePermissionService;
import com.cimr.sysmanage.service.UserRoleService;
import com.cimr.sysmanage.service.UserService;
import com.cimr.util.Assist;
import com.cimr.util.PageData;

@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {
	@Autowired
	private PermissionDao dao;
	
	@Autowired
	private RolePermissionService rolePermissionService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private UserService userService;

	@Override
	public Set<String> findPermissionByUserId(String userId) {
		Set<String> permissionKeySet = new HashSet<>();
		List<RolePermission> rolePermissionList = null;
		Permission permission = null;
		Assist assist = new Assist();
		assist.setRequires(Assist.andEq("user_id", userId));
		List<UserRole> userRoleList = userRoleService.selectObj_common(assist);
		for (UserRole userRole : userRoleList) {
			assist = new Assist();
			assist.setRequires(Assist.andEq("role_id", userRole.getRoleId()));
			rolePermissionList = rolePermissionService.selectObj_common(assist);
			for (RolePermission rolePermission : rolePermissionList) {
				permission = this.selectObjById_common(rolePermission.getPermissionId());
				if (null != permission) {
					permissionKeySet.add(permission.getPermissionKey());
				}
			}
		}
		return permissionKeySet;
	}

	@Override
	public List<Permission> getPermissionListByUserId(String userId) {
		List<RolePermission> rolePermissionList = null;
		Assist assist = new Assist();
		assist.setRequires(Assist.andEq("user_id", userId));
		List<UserRole> userRoleList = userRoleService.selectObj_common(assist);
		List<String> permissionIds = new ArrayList<>();
		for (UserRole userRole : userRoleList) {
			assist = new Assist();
			assist.setRequires(Assist.andEq("role_id", userRole.getRoleId()));
			rolePermissionList = rolePermissionService.selectObj_common(assist);
			for (RolePermission rolePermission : rolePermissionList) {
				if (!permissionIds.contains(rolePermission.getPermissionId())) {
					permissionIds.add(rolePermission.getPermissionId());
				}
			}
		}
		if (permissionIds.size() > 0) {
			assist = new Assist();
			assist.setRequires(Assist.andIn("id", permissionIds));
			List<Permission> permissionList = this.selectObj_common(assist);
			return permissionList;
		} else {
			return new ArrayList<>();
		}
	}
	
	@Override
	public List<Permission> getPermissionListWithName(String menuName, String type, String userId) {
		List<Permission> permissionList = new ArrayList<>();
		List<RolePermission> rolePermissionList = null;
		Assist assist = new Assist();
		assist.setRequires(Assist.andEq("user_id", userId));
		List<UserRole> userRoleList = userRoleService.selectObj_common(assist);
		List<String> permissionIds = new ArrayList<>();
		for (UserRole userRole : userRoleList) {
			assist = new Assist();
			assist.setRequires(Assist.andEq("role_id", userRole.getRoleId()));
			rolePermissionList = rolePermissionService.selectObj_common(assist);
			for (RolePermission rolePermission : rolePermissionList) {
				if (!permissionIds.contains(rolePermission.getPermissionId())) {
					permissionIds.add(rolePermission.getPermissionId());
				}
			}
		}
		if (permissionIds.size() > 0) {
			assist = new Assist();
			assist.setRequires(Assist.andEq("permission_type", type));
			assist.setRequires(Assist.andIn("id", permissionIds));
			assist.setRequires(Assist.andLike("permission_name", menuName));
			permissionList = this.selectObj_common(assist);
			return permissionList;
		} else {
			return new ArrayList<>();
		}
	}
	
	@Override
	public PageData<Permission> getPermissionListWithName(String menuName, String type, String userId, int page, int limit) {
		List<RolePermission> rolePermissionList = null;
		Assist assist = new Assist();
		assist.setRequires(Assist.andEq("user_id", userId));
		List<UserRole> userRoleList = userRoleService.selectObj_common(assist);
		List<String> permissionIds = new ArrayList<>();
		for (UserRole userRole : userRoleList) {
			assist = new Assist();
			assist.setRequires(Assist.andEq("role_id", userRole.getRoleId()));
			rolePermissionList = rolePermissionService.selectObj_common(assist);
			for (RolePermission rolePermission : rolePermissionList) {
				if (!permissionIds.contains(rolePermission.getPermissionId())) {
					permissionIds.add(rolePermission.getPermissionId());
				}
			}
		}
		if (permissionIds.size() > 0) {
			assist = new Assist();
			assist.setRequires(Assist.andEq("permission_type", type));
			assist.setRequires(Assist.andIn("id", permissionIds));
			assist.setRequires(Assist.andLike("permission_name", menuName));
			PageData<Permission> pageData = this.selectObj_common(assist, page, limit);
			return pageData;
		} else {
			PageData<Permission> pageData = new PageData<Permission>();
			pageData.setCount(0);
			pageData.setList(null);
			return pageData;
		}
	}

	@Override
	public List<Permission> getPermissionsByKey(String key, String type) {
		Assist assist = new Assist();
		assist.setRequires(Assist.andEq("permission_key", key));
		assist.setRequires(Assist.andEq("permission_type", type));
		List<Permission> permissionList = this.selectObj_common(assist);
		return permissionList;
	}

	@Override
	public List<Permission> getMenuList() {
		Assist assist = new Assist();
		assist.setRequires(Assist.andEq("permission_type", PermissionTypeEnum.MENU.getKey()));
		List<Permission> permissionList = this.selectObj_common(assist);
		return permissionList;
	}

	@Override
	public List<Permission> getMenuListByUserId(String userId) {
		List<RolePermission> rolePermissionList = null;
		Assist assist = new Assist();
		assist.setRequires(Assist.andEq("user_id", userId));
		List<UserRole> userRoleList = userRoleService.selectObj_common(assist);
		List<String> permissionIds = new ArrayList<>();
		for (UserRole userRole : userRoleList) {
			assist = new Assist();
			assist.setRequires(Assist.andEq("role_id", userRole.getRoleId()));
			rolePermissionList = rolePermissionService.selectObj_common(assist);
			for (RolePermission rolePermission : rolePermissionList) {
				if (!permissionIds.contains(rolePermission.getPermissionId())) {
					permissionIds.add(rolePermission.getPermissionId());
				}
			}
		}
		if (permissionIds.size() > 0) {
			assist = new Assist();
			assist.setRequires(Assist.andIn("id", permissionIds));
			assist.setRequires(Assist.andEq("permission_type", PermissionTypeEnum.MENU.getKey()));
			List<Permission> permissionList = this.selectObj_common(assist);
			return permissionList;
		} else {
			return new ArrayList<>();
		}
	}

	@Override
	public List<Permission> getMenuListByUsername(String username) {
		List<RolePermission> rolePermissionList = null;
		User user = userService.getByUsername(username);
		Assist assist = new Assist();
		assist.setRequires(Assist.andEq("user_id", user.getId()));
		List<UserRole> userRoleList = userRoleService.selectObj_common(assist);
		List<String> permissionIds = new ArrayList<>();
		for (UserRole userRole : userRoleList) {
			assist = new Assist();
			assist.setRequires(Assist.andEq("role_id", userRole.getRoleId()));
			rolePermissionList = rolePermissionService.selectObj_common(assist);
			for (RolePermission rolePermission : rolePermissionList) {
				if (!permissionIds.contains(rolePermission.getPermissionId())) {
					permissionIds.add(rolePermission.getPermissionId());
				}
			}
		}
		if (permissionIds.size() > 0) {
			assist = new Assist();
			assist.setRequires(Assist.andIn("id", permissionIds));
			assist.setRequires(Assist.andEq("permission_type", PermissionTypeEnum.MENU.getKey()));
			List<Permission> permissionList = this.selectObj_common(assist);
			return permissionList;
		} else {
			return new ArrayList<>();
		}
	}

	@Override
	public List<Permission> getMenuListByRoleId(String roleId) {
		Assist assist = new Assist();
		assist.setRequires(Assist.andEq("role_id", roleId));
		List<RolePermission> rolePermissionList = rolePermissionService.selectObj_common(assist);
		List<String> permissionIds = new ArrayList<>();
		for (RolePermission rolePermission : rolePermissionList) {
			permissionIds.add(rolePermission.getPermissionId());
		}
		if (permissionIds.size() > 0) {
			assist = new Assist();
			assist.setRequires(Assist.andIn("id", permissionIds));
			assist.setRequires(Assist.andEq("permission_type", PermissionTypeEnum.MENU.getKey()));
			List<Permission> permissionList = this.selectObj_common(assist);
			return permissionList;
		} else {
			return new ArrayList<>();
		}
	}
	
	@Override
	public List<Permission> getOperationListByUserId(String userId) {
		List<RolePermission> rolePermissionList = null;
		Assist assist = new Assist();
		assist.setRequires(Assist.andEq("user_id", userId));
		List<UserRole> userRoleList = userRoleService.selectObj_common(assist);
		List<String> permissionIds = new ArrayList<>();
		for (UserRole userRole : userRoleList) {
			assist = new Assist();
			assist.setRequires(Assist.andEq("role_id", userRole.getRoleId()));
			rolePermissionList = rolePermissionService.selectObj_common(assist);
			for (RolePermission rolePermission : rolePermissionList) {
				if (!permissionIds.contains(rolePermission.getPermissionId())) {
					permissionIds.add(rolePermission.getPermissionId());
				}
			}
		}
		if (permissionIds.size() > 0) {
			assist = new Assist();
			assist.setRequires(Assist.andIn("id", permissionIds));
			assist.setRequires(Assist.andEq("permission_type", PermissionTypeEnum.OPERATION.getKey()));
			List<Permission> permissionList = this.selectObj_common(assist);
			return permissionList;
		} else {
			return new ArrayList<>();
		}
	}

	@Override
	public List<Permission> getOperationListByUsername(String username) {
		List<RolePermission> rolePermissionList = null;
		User user = userService.getByUsername(username);
		Assist assist = new Assist();
		assist.setRequires(Assist.andEq("user_id", user.getId()));
		List<UserRole> userRoleList = userRoleService.selectObj_common(assist);
		List<String> permissionIds = new ArrayList<>();
		for (UserRole userRole : userRoleList) {
			assist = new Assist();
			assist.setRequires(Assist.andEq("role_id", userRole.getRoleId()));
			rolePermissionList = rolePermissionService.selectObj_common(assist);
			for (RolePermission rolePermission : rolePermissionList) {
				if (!permissionIds.contains(rolePermission.getPermissionId())) {
					permissionIds.add(rolePermission.getPermissionId());
				}
			}
		}
		if (permissionIds.size() > 0) {
			assist = new Assist();
			assist.setRequires(Assist.andIn("id", permissionIds));
			assist.setRequires(Assist.andEq("permission_type", PermissionTypeEnum.OPERATION.getKey()));
			List<Permission> permissionList = this.selectObj_common(assist);
			return permissionList;
		} else {
			return new ArrayList<>();
		}
	}

	@Override
	public List<Permission> getOperationListByRoleId(String roleId) {
		Assist assist = new Assist();
		assist.setRequires(Assist.andEq("role_id", roleId));
		List<RolePermission> rolePermissionList = rolePermissionService.selectObj_common(assist);
		List<String> permissionIds = new ArrayList<>();
		for (RolePermission rolePermission : rolePermissionList) {
			permissionIds.add(rolePermission.getPermissionId());
		}
		if (permissionIds.size() > 0) {
			assist = new Assist();
			assist.setRequires(Assist.andIn("id", permissionIds));
			assist.setRequires(Assist.andEq("permission_type", PermissionTypeEnum.OPERATION.getKey()));
			List<Permission> permissionList = this.selectObj_common(assist);
			return permissionList;
		} else {
			return new ArrayList<>();
		}
	}
	
	@Override
	public int deleteByPermissionId(String permissionId) {
		Assist assist = null;
		List<String> permissionIds = new ArrayList<>();
		permissionIds.add(permissionId);
		//查找子项(共两级)
		assist = new Assist();
		assist.setRequires(Assist.andEq("parent_id", permissionId));
		List<Permission> childList = this.selectObj_common(assist);
		for (Permission child : childList) {
			permissionIds.add(child.getId());
		}
		//删除角色关联记录
		assist = new Assist();
		assist.setRequires(Assist.andIn("permission_id", permissionIds));
		rolePermissionService.deleteObj_common(assist);
		//删除权限
		assist = new Assist();
		assist.setRequires(Assist.andIn("id", permissionIds));
		return this.deleteObj_common(assist);
	}

	//公共方法--------开始

	@Override
	public long getObjRowCount_common(Assist assist) {
		if (null == assist) {
    		assist = new Assist();
    	}
		assist.setRequires(Assist.andEq("del_flag", 0));
		return dao.getObjRowCount_common(assist);
	}

	@Override
	public List<Permission> selectObj_common(Assist assist) {
		if (null == assist) {
    		assist = new Assist();
    	}
		assist.setRequires(Assist.andEq("del_flag", 0));
		assist.setOrder(Assist.order("order_id, id", true));
		return dao.selectObj_common(assist);
	}

	@Override
	public PageData<Permission> selectObj_common(Assist assist, int page, int limit) {
		if (null == assist) {
    		assist = new Assist();
    	}
		PageData<Permission> pageData = new PageData<>();
		Long count = this.getObjRowCount_common(assist);
		int startRow = limit * (page - 1);
		assist.setStartRow(startRow);
		assist.setRowSize(limit);
		assist.setOrder(Assist.order("order_id, id", true));
		List<Permission> list = dao.selectObj_common(assist);
		pageData.setCount(count.intValue());
		pageData.setList(list);
		return pageData;
	}

	@Override
	public Permission selectObjByObj_common(Permission obj) {
		return dao.selectObjByObj_common(obj);
	}

	@Override
	public Permission selectObjById_common(String id) {
		return dao.selectObjById_common(id);
	}

	@Override
	public int insertObj_common(Permission value) {
		value.preInsert();
		return dao.insertObj_common(value);
	}

	@Override
	public int insertNonEmptyObj_common(Permission value) {
		value.preInsert();
		return dao.insertNonEmptyObj_common(value);
	}

	@Override
	public int insertObjByBatch_common(List<Permission> value) {
		for (Permission temp : value) {
			temp.preInsert();
		}
		return dao.insertObjByBatch_common(value);
	}

	@Override
	public int deleteObjById_common(String id) {
		Permission temp = new Permission();
		temp.setId(id);
		temp.setDelFlag(1);
		temp.preUpdate();
		return dao.updateNonEmptyObjById_common(temp);
	}

	@Override
	public int deleteObj_common(Assist assist) {
		Permission temp = new Permission();
		temp.setDelFlag(1);
		temp.preUpdate();
		return dao.updateNonEmptyObj_common(temp, assist);
	}

	@Override
	public int updateObjById_common(Permission enti) {
		enti.preUpdate();
		return dao.updateObjById_common(enti);
	}

	@Override
	public int updateObj_common(Permission value, Assist assist) {
		value.preUpdate();
		return dao.updateObj_common(value, assist);
	}

	@Override
	public int updateNonEmptyObjById_common(Permission enti) {
		enti.preUpdate();
		return dao.updateNonEmptyObjById_common(enti);
	}

	@Override
	public int updateNonEmptyObj_common(Permission value, Assist assist) {
		value.preUpdate();
		return dao.updateNonEmptyObj_common(value, assist);
	}

	@Override
	public int deleteObjById_common(Permission enti) {
		return 0;
	}

	@Override
	public int deleteObj_common(Permission enti, Assist assist) {
		return 0;
	}

	//公共方法--------结束
}
