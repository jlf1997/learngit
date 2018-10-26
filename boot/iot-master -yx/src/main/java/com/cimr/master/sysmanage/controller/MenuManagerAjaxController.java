package com.cimr.master.sysmanage.controller;

import com.cimr.comm.constants.MenuTargetEnum;
import com.cimr.comm.constants.PermissionTypeEnum;
import com.cimr.comm.token.TokenUtil;
import com.cimr.sysmanage.bo.MenuBo;
import com.cimr.sysmanage.dto.HttpResult;
import com.cimr.sysmanage.dto.LayuiTableData;
import com.cimr.sysmanage.dto.MenuTargetDto;
import com.cimr.sysmanage.dto.MenuTree;
import com.cimr.sysmanage.model.Permission;
import com.cimr.sysmanage.model.RolePermission;
import com.cimr.sysmanage.service.MenuService;
import com.cimr.sysmanage.service.PermissionService;
import com.cimr.sysmanage.service.RolePermissionService;
import com.cimr.util.Assist;
import com.cimr.util.PageData;
import com.cimr.util.StringUtil;
import com.cimr.util.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping({ "/menuManager/ajax" })
public class MenuManagerAjaxController {

	private static final Logger logger = LoggerFactory.getLogger(MenuManagerAjaxController.class);


	@Autowired
	private MenuService menuService;

	@Autowired
	private PermissionService permissionService;

	@Autowired
	private RolePermissionService rolePermissionService;

	@RequestMapping({ "/getMenuTreeByCurrentUser" })
	@ResponseBody
	public HttpResult getMenuTreeByCurrentUser() {
		String userId = TokenUtil.getUserId();
		Set<String> permissionSet = permissionService.findPermissionByUserId(userId);

		List<MenuBo> menuList = menuService.getMenuList();
		Map<String, MenuBo> menusMap = new HashMap<>();
		List<MenuBo> menuListByUser = new ArrayList<>();
		for (int i = 0; i < menuList.size(); i++) {
			if (permissionSet.contains(((MenuBo) menuList.get(i)).getMenuKey())) {
				menuListByUser.add(menuList.get(i));
				menusMap.put(((MenuBo) menuList.get(i)).getId(), menuList.get(i));
			}
		}

		for (int j = 0; j < menuListByUser.size(); j++) {
			if (!"0".equals(((MenuBo) menuListByUser.get(j)).getParentId())) {
				MenuBo menu = (MenuBo) menusMap.get(((MenuBo) menuListByUser.get(j)).getParentId());
				if (menu != null) {
					List<MenuBo> menuChildren = menu.getChildren();
					if (menuChildren == null) {
						menuChildren = new ArrayList<>();
					}
					menuChildren.add(menuListByUser.get(j));
					menu.setChildren(menuChildren);
				}
			}
		}

		List<MenuBo> resultMenuList = new ArrayList<>();
		for (int k = 0; k < menuListByUser.size(); k++) {
			if (((MenuBo) menuListByUser.get(k)).getLevel().intValue() == 1) {
				resultMenuList.add(menuListByUser.get(k));
			}
		}

		HttpResult result = new HttpResult(true, "成功");
		result.setData(resultMenuList);
		return result;
	}

	/**
	 * 方法描述: 根据条件分页获取菜单列表
	 * @param page
	 * @param limit
	 * @param menuName
	 * @return
	 * 		LayuiTableData
	 * 作者:    admin
	 * 创建时间: 2018年4月24日 上午9:16:24
	 * 修改人:
	 * 修改时间:
	 * 修改内容:
	 * 修改次数: 0
	 */
	@RequestMapping({ "getMenuList" })
	@ResponseBody
	public LayuiTableData getMenuList(@RequestParam(required = true, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "10") Integer limit, @RequestParam(required = false) String menuName) {
		com.github.pagehelper.PageHelper.startPage(page.intValue(), limit.intValue());
		PageData<MenuBo> pageData = this.menuService.getMenuListWithName(menuName, page, limit);
		LayuiTableData result = new LayuiTableData();
		result.setCode(Integer.valueOf(0));
		result.setMsg("");
		result.setCount(pageData.getCount());
		result.setData(pageData.getList());
		return result;
	}

	@RequestMapping({ "getMenuTableTree" })
	@ResponseBody
	public HttpResult getMenuTableTree(@RequestParam(required = false) String menuName) {
		HttpResult result = new HttpResult(true, "");
		List<MenuBo> list = this.menuService.getMenuListWithName(menuName);
		List<MenuTree> levl1List = new ArrayList<>();
		List<MenuTree> levl2List = new ArrayList<>();
		for (MenuBo mb : list) {
			if (mb.getLevel().intValue() == 1) {
				MenuTree mt = new MenuTree();
				mt.setId(mb.getId());
				mt.setName(mb.getMenuName());
				mt.setMenuKey(mb.getMenuKey());
				mt.setParentId(mb.getParentId());
				mt.setOrderId(mb.getOrderId());
				mt.setLevel(mb.getLevel());
				mt.setUrl(mb.getHref());
				mt.setMenuIcon(mb.getMenuIcon());
				if (mb.getTarget().equals("_self")) {
					mt.setTarget("本页面");
				}
				if (mb.getTarget().equals("_blank")) {
					mt.setTarget("新页面");
				}
				if (mb.getComment() == null) {
					mt.setComment("");
				} else {
					mt.setComment(mb.getComment());
				}
				levl1List.add(mt);
			}
			if (mb.getLevel().intValue() == 2) {
				MenuTree mt = new MenuTree();
				mt.setId(mb.getId());
				mt.setName(mb.getMenuName());
				mt.setMenuKey(mb.getMenuKey());
				mt.setParentId(mb.getParentId());
				mt.setOrderId(mb.getOrderId());
				mt.setLevel(mb.getLevel());
				mt.setUrl(mb.getHref());
				mt.setMenuIcon(mb.getMenuIcon());
				if (mb.getTarget().equals("_self")) {
					mt.setTarget("本页面");
				}
				if (mb.getTarget().equals("_blank")) {
					mt.setTarget("新页面");
				}
				if (mb.getComment() == null) {
					mt.setComment("");
				} else {
					mt.setComment(mb.getComment());
				}
				levl2List.add(mt);
			}
		}
		for (MenuTree mb1 : levl1List) {
			List<MenuTree> mb2List = new ArrayList<>();
			for (MenuTree mb2 : levl2List) {
				if (mb1.getId().equals(mb2.getParentId())) {
					mb2List.add(mb2);
				}
			}
			mb1.setChildren(mb2List);
		}
		result.setData(levl1List);
		return result;
	}

	@RequestMapping({ "getMenuInfoById" })
	@ResponseBody
	public HttpResult getMenuInfoById(String id) {
		HttpResult result = new HttpResult(true, "");
		MenuBo menuBo = this.menuService.getMenuById(id);
		Map<String, Object> map = new HashMap<>();
		if (("0").equals(menuBo.getParentId())) {
			map.put("parentMenuName", "主菜单");
			map.put("parentMenuLevel", "0");
		} else {
			MenuBo parentMenuBo = this.menuService.getMenuById(menuBo.getParentId());
			map.put("parentMenuName", parentMenuBo.getMenuName());
			map.put("parentMenuLevel", parentMenuBo.getLevel());
		}
		map.put("menuBo", menuBo);
		List<MenuTree> mtList = getMenuTableTrees(id);
		map.put("menuTree", mtList);
		result.setData(map);
		return result;
	}

	@RequestMapping({ "addMenuInfoById" })
	@ResponseBody
	public HttpResult addMenuInfoById() {
		HttpResult result = new HttpResult(true, "");
		List<MenuBo> parentList = this.menuService.getMenuList();
		for (int i = parentList.size() - 1; i >= 0; i--) {
			if (((MenuBo) parentList.get(i)).getLevel().intValue() != 1) {
				parentList.remove(i);
			}
		}
		List<MenuTargetDto> MenuTagerList = new ArrayList<>();
		for (MenuTargetEnum menu : MenuTargetEnum.values()) {
			MenuTargetDto mtd = new MenuTargetDto();
			mtd.setKey(menu.getKey());
			mtd.setName(menu.getName());
			MenuTagerList.add(mtd);
		}
		Map<String, Object> map = new HashMap<>();
		map.put("menuTarget", MenuTagerList);
		map.put("parentList", parentList);
		result.setData(map);
		return result;
	}

	/**
	 * 方法描述: 保存和更新菜单
	 * @param id
	 * @param menuKey
	 * @param menuName
	 * @param parentId
	 * @param level
	 * @param comment
	 * @param orderId
	 * @param target
	 * @param href
	 * @param menuIcon
	 * @return
	 * 		HttpResult
	 * 作者:    admin
	 * 创建时间: 2018年4月24日 上午9:33:34
	 * 修改人:
	 * 修改时间:
	 * 修改内容:
	 * 修改次数: 0
	 */
	@RequestMapping({ "saveMenu" })
	@ResponseBody
	public HttpResult saveMenu(String id, String menuKey, String menuName, String parentId, Integer level, String comment, Float orderId, String target, String href, String menuIcon) {
		int resultStatus;
		Permission menu = new Permission();
		menu.setId(id);
		menu.setPermissionKey(menuKey);
		menu.setPermissionName(menuName);
		menu.setParentId(parentId);
		menu.setLevel(level);
		menu.setComment(comment);
		menu.setOrderId(orderId);
		menu.setTarget(target);
		menu.setHref(href);
		menu.setMenuIcon(menuIcon);
		menu.setPermissionType(PermissionTypeEnum.MENU.getKey());
		if (!StringUtil.empty(id)) {
			//更新菜单
			resultStatus = permissionService.updateNonEmptyObjById_common(menu);
		} else {
			//插入新的菜单
			resultStatus = permissionService.insertNonEmptyObj_common(menu);
			if(UserUtil.isAdmin()) {
				RolePermission rolePermission = new RolePermission();
				rolePermission.setRoleId("1");
				rolePermission.setPermissionId(menu.getId());
				rolePermissionService.insertObj_common(rolePermission);
			}
		}

		HttpResult result = new HttpResult(true, "");
		if (resultStatus == 0) {
			result.setSuccess(false);
		}

		return result;
	}

	/**
	 * 方法描述: 删除菜单
	 * @param id
	 * @param level
	 * @return
	 * 		HttpResult
	 * 作者:    admin
	 * 创建时间: 2018年4月24日 上午9:39:38
	 * 修改人:
	 * 修改时间:
	 * 修改内容:
	 * 修改次数: 0
	 */
	@RequestMapping({ "deleteMenu" })
	@ResponseBody
	public HttpResult deleteMenu(String id, Integer level) {
		HttpResult result = new HttpResult(true, "");
		Assist assist = new Assist();
		assist.setRequires(Assist.andEq("parent_id",id));
		List<Permission> list = permissionService.selectObj_common(assist);
		if(list.size()>0){
			result.setSuccess(false);
			result.setError("请先删除下级菜单！");
			return result;
		}
		int resultStatus = permissionService.deleteByPermissionId(id);
		if (resultStatus == 0) {
			result.setSuccess(false);
			result.setError("操作失败");
		}
		return result;
	}

	/**
	 * 方法描述: 根据关键字获取菜单列表
	 * @param menuKey
	 * @return
	 * 		HttpResult
	 * 作者:    admin
	 * 创建时间: 2018年5月4日 上午10:56:12
	 * 修改人:
	 * 修改时间:
	 * 修改内容:
	 * 修改次数: 0
	 */
	@RequestMapping({ "getMenuListByMenuKey" })
	@ResponseBody
	public HttpResult getMenuListByKey(String menuKey) {
		List<MenuBo> list = menuService.getMenuListByKey(menuKey);
		HttpResult result = new HttpResult(true, "");
		if (list != null && list.size() > 0) {
			result.setData(list);
		}
		return result;
	}

	@RequestMapping({ "getMenuListByMenuKeyOutCurrent" })
	@ResponseBody
	public HttpResult getMenuListByMenuKeyOutCurrent(String id, String menuKey) {
		List<MenuBo> list = this.menuService.getMenuListByKey(menuKey);
		HttpResult result = new HttpResult(true, "");
		if ((list != null) && (list.size() != 0)) {
			if (((MenuBo) list.get(0)).getId().equals(id)) {
				result.setData(null);
			} else {
				result.setData(list);
			}
		} else {
			result.setData(null);
		}
		return result;
	}

	@RequestMapping({ "getMenuTableTreeByParentId" })
	@ResponseBody
	public HttpResult getMenuTableTreeParent() {
		HttpResult result = new HttpResult(true, "");
		result.setData(getMenuTableTree());
		return result;
	}

	private List<MenuTree> getMenuTableTree() {
		List<MenuBo> list = menuService.getMenuListWithName(null);
		List<MenuTree> mList = new ArrayList<>();
		for (MenuBo mb : list) {
			MenuTree mt = new MenuTree();
			mt.setId(mb.getId());
			mt.setName(mb.getMenuName());
			mt.setMenuKey(mb.getMenuKey());
			mt.setParentId(mb.getParentId());
			mt.setOrderId(mb.getOrderId());
			mt.setLevel(mb.getLevel());
			mt.setUrl(mb.getHref());
			mt.setMenuIcon(mb.getMenuIcon());
			if (mb.getTarget().equals("_self")) {
				mt.setTarget("本页面");
			}
			if (mb.getTarget().equals("_blank")) {
				mt.setTarget("新页面");
			}
			if (mb.getComment() == null) {
				mt.setComment("");
			} else {
				mt.setComment(mb.getComment());
			}
			mList.add(mt);
		}
		List<MenuTree> convList = new ArrayList<>();
		for (MenuTree menuTree : mList) {
			if ("0".equals(menuTree.getParentId())) {
				getParentId(menuTree, mList);
				convList.add(menuTree);
			}
		}
		return convList;
	}

	/**
	 * 修改菜单时去掉修改本身的菜单
	 * @param id
	 * @return
     */
	private List<MenuTree> getMenuTableTrees(String id) {
		List<MenuBo> list = menuService.getMenuListWithName(null);
		List<MenuTree> mList = new ArrayList<>();
		for (MenuBo mb : list) {
			MenuTree mt = new MenuTree();
			mt.setId(mb.getId());
			mt.setName(mb.getMenuName());
			mt.setMenuKey(mb.getMenuKey());
			mt.setParentId(mb.getParentId());
			mt.setOrderId(mb.getOrderId());
			mt.setLevel(mb.getLevel());
			mt.setUrl(mb.getHref());
			mt.setMenuIcon(mb.getMenuIcon());
			if (mb.getTarget().equals("_self")) {
				mt.setTarget("本页面");
			}
			if (mb.getTarget().equals("_blank")) {
				mt.setTarget("新页面");
			}
			if (mb.getComment() == null) {
				mt.setComment("");
			} else {
				mt.setComment(mb.getComment());
			}
			if(!id.equals(mb.getId())){
				mList.add(mt);
			}
		}
		List<MenuTree> convList = new ArrayList<>();
		for (MenuTree menuTree : mList) {
			if ("0".equals(menuTree.getParentId())) {
				getParentId(menuTree, mList);
				convList.add(menuTree);
			}
		}
		return convList;
	}

	private void getParentId(MenuTree currMenuTree, List<MenuTree> dbMenuBoList) {
		for (MenuTree menuTree : dbMenuBoList) {
			if (currMenuTree.getId().equals(menuTree.getParentId())) {
				List<MenuTree> childrenList = currMenuTree.getChildren();
				if (childrenList == null) {
					childrenList = new ArrayList<>();
					currMenuTree.setChildren(childrenList);
				}
				currMenuTree.getChildren().add(menuTree);
				getParentId(menuTree, dbMenuBoList);
			}
		}
	}
}
