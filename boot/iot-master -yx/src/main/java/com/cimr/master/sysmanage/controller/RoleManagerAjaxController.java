package com.cimr.master.sysmanage.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.cimr.comm.token.TokenUtil;
import com.cimr.sysmanage.bo.MenuBo;
import com.cimr.sysmanage.dto.HttpResult;
import com.cimr.sysmanage.dto.LayuiTableData;
import com.cimr.sysmanage.dto.MenuTree;
import com.cimr.sysmanage.dto.RoleDto;
import com.cimr.sysmanage.dto.RoleTree;
import com.cimr.sysmanage.dto.ZtreeDto;
import com.cimr.sysmanage.model.Group;
import com.cimr.sysmanage.model.Role;
import com.cimr.sysmanage.model.User;
import com.cimr.sysmanage.model.UserRole;
import com.cimr.sysmanage.service.GroupService;
import com.cimr.sysmanage.service.MenuService;
import com.cimr.sysmanage.service.RoleService;
import com.cimr.sysmanage.service.UserRoleService;
import com.cimr.sysmanage.service.UserService;
import com.cimr.util.Assist;
import com.cimr.util.Page;
import com.cimr.util.PageData;
import com.cimr.util.StringUtil;
import com.cimr.util.UserUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping({ "/roleManager/ajax" })
public class RoleManagerAjaxController {

	@Autowired
	private RoleService roleService;
	@Autowired
	private MenuService menuService;
	@Autowired
	private UserService userService;
	@Autowired
	private GroupService groupService;

	@Autowired
	private UserRoleService userRoleService;

	/**
	 * 方法描述: 根据ID获取角色
	 * 
	 * @param id
	 * @return HttpResult 作者: admin 创建时间: 2018年4月26日 下午3:03:56 修改人: 修改时间: 修改内容:
	 *         修改次数: 0
	 */
	@RequestMapping({ "getRoleById" })
	@ResponseBody
	public HttpResult getRoleById(@RequestParam(required = true) String id) {
		Role role = this.roleService.selectObjById_common(id);
		Group group=groupService.selectObjById_common(role.getGroupId());
		HttpResult result = new HttpResult(true, "获取成功");
		Map<String, Object> map = getGoupManageTrees();
		role.setGroupName(group.getGroupName());
		map.put("role",role);
		result.setData(map);
		return result;
	}

	/**
	 * 方法描述: 弹出菜单授权表单页需先查询当前角色已授权，然后组装成树形数据
	 * 
	 * @param id
	 * @return HttpResult 作者: admin 创建时间: 2018年4月26日 下午3:05:18 修改人: 修改时间: 修改内容:
	 *         修改次数: 0
	 */
	/*@RequestMapping({ "getMenuZtree" })
	@ResponseBody
	public HttpResult getMenuPer(String id) {
		HttpResult result = new HttpResult(true, "获取成功");
		List<MenuBo> allMenuList = menuService.getMenuList();
		List<MenuBo> curMenuList = menuService.getMenuListByRoleId(id);
		List<ZtreeDto> ztreeList = new ArrayList<>();

		ZtreeDto ztreeDto = null;
		for (MenuBo menuBo : allMenuList) {
			ztreeDto = new ZtreeDto();
			ztreeDto.setId(menuBo.getId());
			ztreeDto.setpId(menuBo.getParentId());

			if (menuBo.getLevel() == 1) {
				ztreeDto.setParent(true);
			} else if (menuBo.getLevel() == 2) {
				ztreeDto.setParent(false);
			}
			ztreeDto.setName(menuBo.getMenuName());
			ztreeDto.setChecked(false);
			for (MenuBo curMenuBo : curMenuList) {
				if (menuBo.getId().equals(curMenuBo.getId())) {
					ztreeDto.setChecked(true);
					break;
				}
			}
			ztreeList.add(ztreeDto);
		}
		result.setData(ztreeList);
		return result;
	}*/
	@RequestMapping({ "getMenuZtree" })
	@ResponseBody
	public HttpResult getMenuPer(String id) {
		HttpResult result = new HttpResult(true, "获取成功");
		//根据id获取可以授权的菜单
		List<MenuBo> allMenuList = menuService.getMenuList();

		List<MenuBo> curMenuList = menuService.getMenuListByRoleId(id);
		List<ZtreeDto> ztreeList = new ArrayList<>();

		ZtreeDto ztreeDto = null;
		for (MenuBo menuBo : allMenuList) {
			ztreeDto = new ZtreeDto();
			ztreeDto.setId(menuBo.getId());
			ztreeDto.setpId(menuBo.getParentId());

			if (menuBo.getLevel() == 1) {
				ztreeDto.setParent(true);
			} else if (menuBo.getLevel() == 2) {
				ztreeDto.setParent(false);
			}
			ztreeDto.setName(menuBo.getMenuName());
			ztreeDto.setChecked(false);
			for (MenuBo curMenuBo : curMenuList) {
				if (menuBo.getId().equals(curMenuBo.getId())) {
					ztreeDto.setChecked(true);
					break;
				}
			}
			ztreeList.add(ztreeDto);
		}
		result.setData(ztreeList);
		return result;
	}

	/**
	 * 方法描述: 保存菜单配置
	 * 
	 * @param id
	 * @param menuIds
	 * @return HttpResult 作者: admin 创建时间: 2018年4月26日 下午3:31:28 修改人: 修改时间: 修改内容:
	 *         修改次数: 0
	 */
	@RequestMapping({ "saveMenuPer" })
	@ResponseBody
	public HttpResult saveMenuPer(String id, String menuIds) {
		String s = menuIds.substring(1, menuIds.length() - 1);
		String[] menuIdsArray = s.split(",");
		for (int i = 0; i < menuIdsArray.length; i++) {
			menuIdsArray[i] = menuIdsArray[i].substring(1, menuIdsArray[i].length() - 1);
		}
		int resultStatus = roleService.saveRoleAndMenus(id, menuIdsArray);
		HttpResult result = new HttpResult(true, "");
		if (resultStatus == 0) {
			result.setSuccess(false);
		}
		return result;
	}

	/**
	 * 方法描述: 保存操作配置
	 * 
	 * @param id
	 * @param ids
	 * @return HttpResult 作者: admin 创建时间: 2018年4月26日 下午4:28:10 修改人: 修改时间: 修改内容:
	 *         修改次数: 0
	 */
	@RequestMapping({ "saveOpationPer" })
	@ResponseBody
	public HttpResult saveOpationPer(String id, String ids) {
		String[] operationIdsArray = ids.split(",");
		int resultStatus = roleService.saveRoleAndOperations(id, operationIdsArray);
		HttpResult result = new HttpResult(true, "");
		if (resultStatus == 0) {
			result.setSuccess(false);
		}
		return result;
	}

	/**
	 * 方法描述: 保存角色配置
	 * 
	 * @param id
	 * @param ids
	 * @return HttpResult 作者: admin 创建时间: 2018年4月26日 下午4:46:52 修改人: 修改时间: 修改内容:
	 *         修改次数: 0
	 */
	@RequestMapping({ "saveRolePer" })
	@ResponseBody
	public HttpResult saveRolePer(String id, String ids) {
		HttpResult result = new HttpResult(true, "");
		int resultStatus = 0;
		if (StringUtil.empty(ids)) {
			resultStatus = userService.saveUserAndRoles(id, null);
		} else {
			Map<String, String> idMap = JSON.parseObject(ids, HashMap.class);
			String[] roleIdsArray = idMap.keySet().toArray(new String[idMap.size()]);
			resultStatus = userService.saveUserAndRoles(id, roleIdsArray);
			if (resultStatus == 0) {
				result.setSuccess(false);
			}
		}
		return result;
	}

	/**
	 * 方法描述: 获取角色分页列表
	 * 
	 * @param page
	 * @param limit
	 * @param roleName
	 * @return LayuiTableData 作者: admin 创建时间: 2018年4月26日 下午5:06:31 修改人: 修改时间:
	 *         修改内容: 修改次数: 0
	 */
	@RequestMapping({ "/getRoleManagerList" })
	@ResponseBody
	public LayuiTableData getRoleManagerList(@RequestParam(required = true, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "10") Integer limit, @RequestParam(required = false) String roleName) {
		LayuiTableData result = new LayuiTableData();
		User currentUser = TokenUtil.getToken();

		Assist assist = null;
		PageData<Role> pageData = null;
		if (UserUtil.isAdmin()) {
			//系统级用户
			PageHelper.startPage(page, limit);
			assist = new Assist();
			assist.setRequires(Assist.andLike("role_name", roleName));
			assist.setRequires(Assist.andEq("r.del_flag", "0"));
			assist.setOrder(Assist.order("order_id", true));
			List<Role> roleList = roleService.selectObj_commons(assist);
			PageInfo<Role> pageInfo = new PageInfo(roleList);
			result.setCode(0);
			result.setMsg("");
			result.setCount((int) pageInfo.getTotal());
			result.setData(pageInfo.getList());
		} else {
			//组织级用户
			Role role = new Role();
			role.setId(currentUser.getGroupId());
			role.setRoleName(roleName);
			Assist assist1= new Assist();
			assist1.setRequires(Assist.andEq("role_name",roleName));
			assist1.setRequires(Assist.andEq("group_id",currentUser.getGroupId()));
			assist1.setRequires(Assist.andEq("r.del_flag", "0"));
			assist1.setOrder(Assist.order("order_id", true));
			//查出当前登录用户所有角色
			List<Role> roleList = roleService.selectObj_commons(assist1);

			//查询用户所在组织的所有下级组织的用户
			List<Role> list = roleService.selectRoleListByChildGroups(role);

			//查询当前登录用户
			List<Role> roles = roleService.getRoleListByUserId(currentUser.getId());
			for(int i=0;i< roleList.size();i++){
				if(roleList.get(i).getId().equals(roles.get(0).getId())){
					//删除当前登录用户
					roleList.remove(i);
				}
			}
			//添加用户所在组织的所有下级组织的用户
			roleList.addAll(list);
			Page pages = new Page(roleList,page,limit);
			result.setCode(0);
			result.setMsg("");
			result.setCount((int) pages.getCount());
			result.setData(pages.getDataList());
		}
/*
		List<Group> groupList = groupService.selectObj_common(null);
		for (Group group : groupList) {
			for (Role role : pageData.getList()) {
				if (!StringUtil.empty(role.getGroupId()) && role.getGroupId().equals(group.getId())) {
					role.setGroupName(group.getGroupName());
				}
			}
		}*/
		return result;
	}

	/**
	 * 方法描述: 根据用户ID获取角色信息
	 * 
	 * @param id
	 * @return HttpResult 作者: admin 创建时间: 2018年4月26日 下午5:07:25 修改人: 修改时间: 修改内容:
	 *         修改次数: 0
	 */
	@RequestMapping({ "getRolesByUserId" })
	@ResponseBody
	public HttpResult getRolesByUserId(@RequestParam(required = true) String id) {
		List<Role> currentList = roleService.getRoleListByUserId(id);
		HttpResult result = new HttpResult(true, "admin");
		result.setData(currentList);
		return result;
	}

	/**
	 * 方法描述: 保存和更新角色信息
	 * 
	 * @param id
	 * @param roleKey
	 * @param roleName
	 * @param comment
	 * @param orderId
	 * @return HttpResult 作者: admin 创建时间: 2018年4月26日 下午5:08:41 修改人: 修改时间: 修改内容:
	 *         修改次数: 0
	 */
	@RequestMapping({ "saveRole" })
	@ResponseBody
	public HttpResult saveRole(@RequestParam(required = false) String id, @RequestParam(required = false) String roleKey, @RequestParam(required = false) String roleName, @RequestParam(required = false) String groupId, @RequestParam(required = false) String comment, @RequestParam(required = false) Float orderId) {
		int resultStatus = 0;
		Role role = new Role();
		role.setId(id);
		role.setGroupId(groupId);
		role.setRoleKey(roleKey);
		role.setRoleName(roleName);
		role.setComment(comment);
		role.setOrderId(orderId);
		if (!StringUtil.empty(id)) {
			// 更新角色信息
			resultStatus = roleService.updateNonEmptyObjById_common(role);
		} else {
			// 插入新的角色信息
			resultStatus = roleService.insertNonEmptyObj_common(role);
		}
		HttpResult result = new HttpResult(true, "保存成功");
		if (resultStatus == 0) {
			result.setSuccess(false);
		}
		return result;
	}

	/**
	 * 方法描述: 删除角色
	 * 
	 * @param id
	 * @return HttpResult 作者: admin 创建时间: 2018年4月26日 下午5:27:25 修改人: 修改时间: 修改内容:
	 *         修改次数: 0
	 */
	@RequestMapping({ "deleteRole" })
	@ResponseBody
	public HttpResult deleteRole(@RequestParam(required = true) String id) {
		//删角色之前查询是否存在分配此角色的用户
		Assist assist=new Assist();
		assist.setRequires(Assist.andEq("role_id",id));
		List<UserRole> userRole=userRoleService.selectObj_common(assist);
		//存在则删除失败
		if(userRole.size()>0){
			HttpResult result = new HttpResult(true, "");
			result.setError("该角色已被分配，请先处理已分配的此用户！");
			result.setSuccess(false);
			return result;
		}

		int resultStatus = roleService.deleteObjById_common(id);
		HttpResult result = new HttpResult(true, "");
		if (resultStatus == 0) {
			result.setSuccess(false);
		}
		return result;
	}

	/**
	 * 方法描述: 根据角色关键字获取角色列表
	 * 
	 * @param roleKey
	 * @return HttpResult 作者: admin 创建时间: 2018年4月26日 下午5:28:10 修改人: 修改时间: 修改内容:
	 *         修改次数: 0
	 */
	@RequestMapping({ "getRoleListByKey" })
	@ResponseBody
	public HttpResult getRoleListByKey(@RequestParam(required = true) String roleKey,String id) {
		Assist assist = new Assist();
		assist.setRequires(Assist.andEq("role_key", roleKey));
		List<Role> roleList = roleService.selectObj_common(assist);
		HttpResult result = new HttpResult(true, "");
		if(StringUtil.empty(id)) {
			if (roleList != null && roleList.size() > 0) {
				result.setData(roleList);
			}
		}
		if(!StringUtil.empty(id)) {
			Role role = roleService.selectObjById_common(id);
			if(!roleKey.equals(role.getRoleKey())){
				if (roleList != null && roleList.size() > 0) {
					result.setData(roleList);
				}
			}
		}
		return result;
	}

	/**
	 * 方法描述: 根据当前用户获取角色列表
	 * @param userId
	 * @return
	 * 		HttpResult
	 * 作者:    admin
	 * 创建时间: 2018年4月27日 上午11:44:18
	 * 修改人:
	 * 修改时间:
	 * 修改内容:
	 * 修改次数: 0
	 */
	@RequestMapping({ "getRoleListByLoginUser" })
	@ResponseBody
	public HttpResult getRoleListByLoginUser(@RequestParam(required = true) String userId) {
		HttpResult result = new HttpResult(true, "获取成功");

		String userIds =UserUtil.getLoginUserId();
        User loginUser =userService.selectObjById_common(userIds);

        //根据登录用户获取所有角色
        List<Role> roleList=null;
        if("0".equals(loginUser.getGroupId())){
            //获取所有的角色
            roleList = roleService.selectList(null);
        }else {
            Role role = new Role();
            role.setId(loginUser.getGroupId());
            roleList = roleService.selectRoleListByChildGroups(role);
        }

        List<RoleDto> roleDList = getRoleDtoList(userId, roleList);
		// 新建总的RoleTree,封装为树结构
		List<RoleTree> rtList = new ArrayList<>();
		List<RoleDto> sysRoleLsit = null;
		RoleTree sysRt = null;
		/*User loginUser = TokenUtil.getToken();*/

		User user = userService.selectObjById_common(userId);
		/*Assist assist = new Assist();
		assist.setRequires(Assist.andEq("id", user.getGroupId()));
		List<Group> groupList = groupService.selectObj_common(assist);*/
		Group groups=groupService.selectObjById_common(user.getGroupId());
		List<Group> groupList = groupService.selectSon_common(user.getGroupId(),null);
		groupList.add(groups);

		/*if (StringUtil.empty(loginUser.getGroupId()) && StringUtil.empty(user.getGroupId())) {*/
		if ("0".equals(loginUser.getGroupId()) && "0".equals(user.getGroupId())) {
			//系统级用户给系统级用户分配角色,能分配任何角色
			// 先把没有组织的放进去-start
			sysRt = new RoleTree();
//			sysRt.setName("系统级角色");
			sysRoleLsit = new ArrayList<>();
			for (RoleDto rd : roleDList) {
				if (StringUtil.empty(rd.getGroupId())) {
					sysRoleLsit.add(rd);
				}
			}
			if (sysRoleLsit.size() > 0) {
				sysRt.setSubChildren(sysRoleLsit);
				rtList.add(sysRt);
			}
			// 先把没有组织的放进去-end
			//放进有组织的角色-start
			for (Group group : groupList) {
				sysRt = new RoleTree();
				sysRt.setName(group.getGroupName());
				sysRoleLsit = new ArrayList<>();
				for (RoleDto rd : roleDList) {
					if (!StringUtil.empty(rd.getGroupId()) && rd.getGroupId().equals(group.getId())) {
						sysRoleLsit.add(rd);
					}
				}
				if (sysRoleLsit.size() > 0) {
					sysRt.setSubChildren(sysRoleLsit);
					rtList.add(sysRt);
				}
			}
			//放进有组织的角色-end
		} else if (!StringUtil.empty(user.getGroupId())) {
			//给组织级用户分配角色,只能分配本组织角色
			//放进有组织的角色-start
			for (Group group : groupList) {
				sysRt = new RoleTree();
				sysRt.setName(group.getGroupName());
				sysRoleLsit = new ArrayList<>();
				for (RoleDto rd : roleDList) {
					if (!StringUtil.empty(rd.getGroupId()) && rd.getGroupId().equals(group.getId())) {
						sysRoleLsit.add(rd);
					}
				}
				if (sysRoleLsit.size() > 0) {
					sysRt.setSubChildren(sysRoleLsit);
					rtList.add(sysRt);
				}
			}
			//放进有组织的角色-end
		} else {
			result.setSuccess(false);
			result.setError("超越权限的操作");
		}
		result.setData(rtList);
		return result;
	}

	/**
	 * @auther zengfan
	 * @date 2018/3/21 13:27
	 * @params * @param null List<Role>转List<RoleDto>
	 **/
	private List<RoleDto> getRoleDtoList(String userId, List<Role> list) {
		// 根据用户id查出当前用户的角色
		List<Role> currentList = roleService.getRoleListByUserId(userId);
		// 循环List<Role> list转为List<RoleDto>
		List<RoleDto> roleDtoList = new ArrayList<>();
		RoleDto rd = null;
		for (Role r : list) {
			rd = new RoleDto();
			rd.setId(r.getId());
			rd.setRoleKey(r.getRoleKey());
			rd.setRoleName(r.getRoleName());
			rd.setGroupId(r.getGroupId());
			rd.setCheck(false);
			if (null != currentList) {
				// 判断角色是否已是当前用户的角色
				for (Role cR : currentList) {
					if (cR.getId().equals(r.getId())) {
						rd.setCheck(true);
						break;
					}
				}
			}
			roleDtoList.add(rd);
		}
		return roleDtoList;
	}
	/**
	 * 组织树
	 */

	/**
	 * 组织树
	 * @return
	 */
	private Map<String, Object> getGoupManageTrees() {
		Map<String, Object> map = new HashMap<>();
		String userId = UserUtil.getLoginUserId();
		User user = userService.selectObjById_common(userId);
		List<Group> list = groupService.selectSon_common(user.getGroupId(), null);
		Group peers = groupService.selectObjById_common(user.getGroupId());
		list.add(peers);
	/*	List<Group> list = groupService.selectObj_common(null);*/
		List<MenuTree> mList = new ArrayList<>();
		for (Group groups : list) {
			MenuTree mt = new MenuTree();
			mt.setId(groups.getId());
			mt.setName(groups.getGroupName());
			mt.setMenuKey(groups.getGroupKey());
			mt.setParentId(groups.getParentId());
			mt.setOrderId(groups.getOrderId());
			if (groups.getComment() == null) {
				mt.setComment("");
			} else {
				mt.setComment(groups.getComment());
			}
			mList.add(mt);
		}
		List<MenuTree> convList = new ArrayList<>();
		for (MenuTree menuTree : mList) {
			if (user.getGroupId().equals(menuTree.getParentId())) {
				getParentId(menuTree, mList);
				convList.add(menuTree);
			}
		}
		map.put("menuTree",convList);
		map.put("peers",peers);
		return map;
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
