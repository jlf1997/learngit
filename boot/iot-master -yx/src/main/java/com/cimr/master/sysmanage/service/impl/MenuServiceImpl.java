package com.cimr.master.sysmanage.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimr.comm.constants.PermissionTypeEnum;
import com.cimr.comm.token.TokenUtil;
import com.cimr.sysmanage.bo.MenuBo;
import com.cimr.sysmanage.model.Permission;
import com.cimr.sysmanage.service.MenuService;
import com.cimr.sysmanage.service.PermissionService;
import com.cimr.util.Assist;
import com.cimr.util.PageData;
import com.cimr.util.StringUtil;
import com.xiaoleilu.hutool.bean.BeanUtil;

@Service("menuService")
public class MenuServiceImpl implements MenuService {

	@Autowired
	private PermissionService permissionService;

	@Override
	public List<MenuBo> getMenuListWithName(String menuName) {
		List<Permission> list = permissionService.getPermissionListWithName(menuName, PermissionTypeEnum.MENU.getKey(), TokenUtil.getUserId());
		MenuBo menu = null;
		List<MenuBo> menuList = new ArrayList<>();
		for (Permission permission : list) {
			menu = new MenuBo();
			BeanUtil.copyProperties(permission, menu);
			menu.setMenuKey(permission.getPermissionKey());
			menu.setMenuName(permission.getPermissionName());
			menuList.add(menu);
		}
		return menuList;
	}
	
	@Override
	public PageData<MenuBo> getMenuListWithName(String menuName, int page, int limit) {
		PageData<MenuBo> menuBoPage = new PageData<>();
		PageData<Permission> pageData = permissionService.getPermissionListWithName(menuName, PermissionTypeEnum.MENU.getKey(), TokenUtil.getUserId(), page, limit);
		MenuBo menu = null;
		List<MenuBo> menuList = new ArrayList<>();
		for (Permission permission : pageData.getList()) {
			menu = new MenuBo();
			BeanUtil.copyProperties(permission, menu);
			menu.setMenuKey(permission.getPermissionKey());
			menu.setMenuName(permission.getPermissionName());
			menuList.add(menu);
		}
		menuBoPage.setCount(pageData.getCount());
		menuBoPage.setList(menuList);
		return menuBoPage;
	}

	@Override
	public List<MenuBo> getMenuListByKey(String menuKey) {
		List<Permission> list = permissionService.getPermissionsByKey(menuKey, PermissionTypeEnum.MENU.getKey());
		MenuBo menu = null;
		List<MenuBo> menuList = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			menu = new MenuBo();
			BeanUtil.copyProperties(list.get(i), menu);
			menu.setMenuKey(((Permission) list.get(i)).getPermissionKey());
			menu.setMenuName(((Permission) list.get(i)).getPermissionName());
			menuList.add(menu);
		}
		return menuList;
	}

	@Override
	public List<MenuBo> getMenuList() {
		List<Permission> list = permissionService.getMenuList();
		MenuBo menu = null;
		List<MenuBo> menuList = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			menu = new MenuBo();
			BeanUtil.copyProperties(list.get(i), menu);
			menu.setMenuKey(((Permission) list.get(i)).getPermissionKey());
			menu.setMenuName(((Permission) list.get(i)).getPermissionName());
			menuList.add(menu);
		}
		return menuList;
	}

	@Override
	public List<MenuBo> getMenuListByUserId(String userId) {
		List<Permission> list = permissionService.getMenuListByUserId(userId);
		MenuBo menu = null;
		List<MenuBo> menuList = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			menu = new MenuBo();
			BeanUtil.copyProperties(list.get(i), menu);
			menu.setMenuKey(((Permission) list.get(i)).getPermissionKey());
			menu.setMenuName(((Permission) list.get(i)).getPermissionName());
			menuList.add(menu);
		}
		return menuList;
	}

	@Override
	public List<MenuBo> getMenuListByUsername(String username) {
		List<Permission> list = permissionService.getMenuListByUsername(username);
		MenuBo menu = null;
		List<MenuBo> menuList = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			menu = new MenuBo();
			BeanUtil.copyProperties(list.get(i), menu);
			menu.setMenuKey(((Permission) list.get(i)).getPermissionKey());
			menu.setMenuName(((Permission) list.get(i)).getPermissionName());
			menuList.add(menu);
		}
		return menuList;
	}

	@Override
	public List<MenuBo> getMenuListByRoleId(String roleId) {
		List<Permission> list = permissionService.getMenuListByRoleId(roleId);
		MenuBo menu = null;
		List<MenuBo> menuList = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			menu = new MenuBo();
			BeanUtil.copyProperties(list.get(i), menu);
			menu.setMenuKey(((Permission) list.get(i)).getPermissionKey());
			menu.setMenuName(((Permission) list.get(i)).getPermissionName());
			menuList.add(menu);
		}
		return menuList;
	}

	@Override
	public MenuBo getMenuById(String id) {
		Permission permission = permissionService.selectObjById_common(id);
		MenuBo menu = new MenuBo();
		BeanUtil.copyProperties(permission, menu);
		menu.setMenuKey(permission.getPermissionKey());
		menu.setMenuName(permission.getPermissionName());
		return menu;
	}

	@Override
	public int deleteMenuListByParentId(String parentId) {
		if (StringUtil.empty(parentId)) {
			return 0;
		}
		Assist assist = new Assist();
		assist.setRequires(Assist.andEq("parent_id", parentId));
		return permissionService.deleteObj_common(assist);
	}
}
