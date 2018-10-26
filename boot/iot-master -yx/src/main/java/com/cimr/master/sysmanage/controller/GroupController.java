package com.cimr.master.sysmanage.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.cimr.comm.enums.SysEnums;
import com.cimr.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cimr.comm.token.TokenUtil;
import com.cimr.sysmanage.dto.HttpResult;
import com.cimr.sysmanage.dto.LayuiTableData;
import com.cimr.sysmanage.dto.MenuTree;
import com.cimr.sysmanage.model.Group;
import com.cimr.sysmanage.model.User;
import com.cimr.sysmanage.service.GroupService;
import com.cimr.sysmanage.service.RoleService;
import com.cimr.sysmanage.service.UserService;


/**
 * 类描述:组织管理 作者:admin 创建时间:2018年4月26日 上午9:37:34
 */
@Controller
@RequestMapping("/group/ajax")
public class GroupController {

	private static final Logger logger = LoggerFactory.getLogger(GroupController.class);

	@Autowired
	private GroupService groupService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;
	/**
	 * 分页查询
	 *
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping({ "/getList" })
	@ResponseBody
	public LayuiTableData list(@RequestParam(required = true, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "10") Integer limit, @RequestParam(required = false) String groupName) {
		Assist assist = new Assist();
		assist.setRequires(Assist.andLike("group_name", groupName));
		PageData<Group> pageData = groupService.selectObj_common(assist, page, limit);
		LayuiTableData result = new LayuiTableData();
		result.setCode(0);
		result.setMsg("");
		result.setCount(pageData.getCount());
		result.setData(pageData.getList());
		return result;
	}

	/**
	 * 方法描述: 保存和更新组织信息
	 * @param group
	 * @param session
	 * @return
	 * 		HttpResult
	 * 作者:    admin
	 * 创建时间: 2018年4月26日 上午9:48:36
	 * 修改人:
	 * 修改时间:
	 * 修改内容:
	 * 修改次数: 0
	 */
	@RequestMapping({ "/save" })
	@ResponseBody
	public HttpResult save(Group group, HttpSession session, HttpServletRequest request) {
		int resultStatus = 0;
		try {
			if (StringUtil.empty(group.getId())) {
				// 添加操作
				resultStatus = groupService.insertNonEmptyObj_common(group);
			} else {
				// 更新操作
				resultStatus = groupService.updateNonEmptyObjById_common(group);
			}
		} catch (Exception e) {
			LogUtil.printStackTraceToLog(request,e, logger, SysEnums.HTTP_500,null);
		}
		HttpResult result = new HttpResult(true, "保存成功");
        if(resultStatus == 0) {
            result.setSuccess(false);
            result.setError("保存失败");
        }
		return result;
	}

	/**
	 * 方法描述: 删除组织
	 * @param id
	 * @return
	 * 		HttpResult
	 * 作者:    admin
	 * 创建时间: 2018年4月26日 上午10:09:15
	 * 修改人:
	 * 修改时间:
	 * 修改内容:
	 * 修改次数: 0
	 */
	@RequestMapping({ "/delete" })
	@ResponseBody
	public HttpResult delete(String id) {
		HttpResult result = new HttpResult(true, "删除成功");
		Assist assist = new Assist();
		assist.setRequires(Assist.andEq("group_id", id));
		long userCount = userService.getObjRowCount_common(assist);

		long roleCount = roleService.getObjRowCount_common(assist);
		if (userCount > 0) {
			result.setSuccess(false);
			result.setError("组织下存在用户,请先删除用户!");
		}else if(roleCount>0){
			result.setSuccess(false);
			result.setError("组织下存在角色,请先删除角色!");
		}else {
			groupService.deleteObjById_common(id);
		}
		return result;
	}

	/**
	 * 方法描述: 根据ID获取单个信息
	 * @param id
	 * @return
	 * 		HttpResult
	 * 作者:    admin
	 * 创建时间: 2018年4月26日 上午10:11:36
	 * 修改人:
	 * 修改时间:
	 * 修改内容:
	 * 修改次数: 0
	 */
	@RequestMapping({ "/getById" })
	@ResponseBody
	public HttpResult getById(@RequestParam(required = false) String id,HttpServletRequest request) {
		HttpResult result = new HttpResult(true, "获取成功");
		try {
			if(id!=null&&!"".equals(id)) {
				Group group = groupService.selectObjById_common(id);
				if (null != group) {
					Map<String, Object> map = getGroupManageTree(id);
					map.put("group", group);
					result.setData(map);
				}
			}else{
				Map<String, Object> map = getGoupManageTrees(null);
				result.setData(map);
			}
		} catch (Exception e) {
			LogUtil.printStackTraceToLog(request,e, logger, SysEnums.HTTP_500,null);
		}

		return result;
	}

	/**
	 * 方法描述: 根据当前用户的级别获取本级别及以下的组织信息
	 * @return
	 * 		HttpResult
	 * 作者:    admin
	 * 创建时间: 2018年4月26日 上午10:16:09
	 * 修改人:
	 * 修改时间:
	 * 修改内容:
	 * 修改次数: 0
	 */
	@RequestMapping({ "/getAll" })
	@ResponseBody
	public HttpResult getAll() {
		HttpResult result = new HttpResult(true, "查询成功");
		User user = TokenUtil.getToken();
		Group temp = new Group();
		temp.setId(user.getGroupId());
		Group myGroup = groupService.selectObjByObj_common(temp);
		if (null == myGroup) {
			result.setSuccess(false);
			result.setError("获取失败");
		} else {
			List<Group> groupList = new ArrayList<>();
			groupList.add(myGroup);
			getListByParentId(myGroup.getId(), groupList);
			result.setData(groupList);
		}
		return result;
	}
	
	private void getListByParentId(String parentId, List<Group> list) {
		Assist assist = new Assist();
		assist.setRequires(Assist.andEq("parent_id", parentId));
		List<Group> resultList = groupService.selectObj_common(assist);
		if (resultList != null && resultList.size() > 0) {
			list.addAll(resultList);
			for (Group group : resultList) {
				getListByParentId(group.getId(), list);
			}
		}
	}
	@RequestMapping({ "getGroupTreeParent" })
	@ResponseBody
	public HttpResult getGroupTreeParent(String groupName) {
		HttpResult result = new HttpResult(true, "");
		result.setData(getGoupManageTree(groupName));
		return result;
	}
	private List<MenuTree> getGoupManageTree(String groupName) {
		String userId = UserUtil.getLoginUserId();
		User user = userService.selectObjById_common(userId);
		List<Group> list = groupService.selectSon_common(user.getGroupId(), groupName);
		Group group = groupService.selectObjById_common(user.getGroupId());
		list.add(group);
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
				if (user.getGroupId().equals(menuTree.getId())) {
					getParentId(menuTree, mList);
					menuTree.setUrl("1");
					convList.add(menuTree);
				}
		}
		return convList;
	}

	/**
	 * 组织树
	 * @param groupName
	 * @return
     */
	private Map<String, Object> getGoupManageTrees(String groupName) {
		Map<String, Object> map = new HashMap<>();
		String userId = UserUtil.getLoginUserId();
		User user = userService.selectObjById_common(userId);
		List<Group> list = groupService.selectSon_common(user.getGroupId(), groupName);
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
	/**
	 * 组织修改时组织树
	 * @return
	 */
	private Map<String, Object> getGroupManageTree(String groupId) {
		Map<String, Object> map = new HashMap<>();
		String userId = UserUtil.getLoginUserId();
		User user = userService.selectObjById_common(userId);
		List<Group> list = groupService.selectUpdate_common(user.getGroupId(),groupId);
		Group peers = groupService.selectObjById_common(user.getGroupId());
	/*	list.add(peers);*/
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
			if(!groupId.equals(groups.getId())){
				mList.add(mt);
			}
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

	/**
	 * 组织名称重复判断
	 * @return
     */
	@RequestMapping({ "isGroupName" })
	@ResponseBody
	public HttpResult isGroupName(@RequestParam(required = true) String groupName,String id) {
		String flag="0";
		HttpResult result = new HttpResult(true, "");
        if(StringUtil.empty(id)) {
			if (groupService.isGroupName(groupName)) {
				flag = "1";
				result.setError(flag);
				return result;
			}
		}
		if(!StringUtil.empty(id)){
			Group group = groupService.selectObjById_common(id);
			if(!groupName.equals(group.getGroupName())){
				if(!groupService.isGroupName(group.getGroupName())) {
					flag="1";
					result.setError(flag);
					return result;
				}
			}
		}
			result.setError(flag);
			return result;
	}
}
