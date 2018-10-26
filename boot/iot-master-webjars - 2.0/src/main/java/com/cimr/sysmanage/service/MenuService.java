package com.cimr.sysmanage.service;

import java.util.List;

import com.cimr.sysmanage.bo.MenuBo;
import com.cimr.util.PageData;

public interface MenuService {
	List<MenuBo> getMenuList();

	List<MenuBo> getMenuListByUserId(String paramString);

	List<MenuBo> getMenuListByUsername(String paramString);

	List<MenuBo> getMenuListByRoleId(String paramString);

	List<MenuBo> getMenuListByKey(String paramString);

	MenuBo getMenuById(String paramString);

	List<MenuBo> getMenuListWithName(String menuName);
	
	PageData<MenuBo> getMenuListWithName(String menuName, int page, int limit);

	int deleteMenuListByParentId(String paramString);
}
