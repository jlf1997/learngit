package com.cimr.sysmanage.controller;

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

import com.cimr.comm.config.AppFileProperties;
import com.cimr.comm.token.TokenUtil;
import com.cimr.sysmanage.dto.HttpResult;
import com.cimr.sysmanage.dto.LayuiTableData;
import com.cimr.sysmanage.dto.MenuTree;
import com.cimr.sysmanage.dto.PermissionTree;
import com.cimr.sysmanage.dto.SelectDto;
import com.cimr.sysmanage.dto.UserDto;
import com.cimr.sysmanage.model.Group;
import com.cimr.sysmanage.model.Permission;
import com.cimr.sysmanage.model.UnitFile;
import com.cimr.sysmanage.model.User;
import com.cimr.sysmanage.model.UserDevice;
import com.cimr.sysmanage.service.GroupService;
import com.cimr.sysmanage.service.PermissionService;
import com.cimr.sysmanage.service.UnitFileService;
import com.cimr.sysmanage.service.UserDeviceService;
import com.cimr.sysmanage.service.UserRoleService;
import com.cimr.sysmanage.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 类描述:目前使用的用户异步访问控制层 作者:admin 创建时间:2018年4月23日 下午2:09:01
 */
@Controller
@RequestMapping({ "/userManager/ajax" })
public class  UserManagerAjaxController {

	private static final Logger logger = LoggerFactory.getLogger(UserManagerAjaxController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private PermissionService permissionService;

	@Autowired
	private UnitFileService unitFileService;

	@Autowired
	private GroupService groupService;

	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private UserDeviceService userDeviceService;

	/**
	 * 方法描述: 保存皮肤设置
	 * 
	 * @param theme
	 * @return HttpResult 作者: admin 创建时间: 2018年4月23日 下午3:16:41 修改人: 修改时间: 修改内容:
	 *         修改次数: 0
	 */
	@RequestMapping({ "saveSkin" })
	@ResponseBody
	public HttpResult saveSkin(String theme) {
		String userId = TokenUtil.getUserId();
		User temp = new User();
		temp.setId(userId);
		temp.setTheme(theme);
		int resultStatus = userService.updateNonEmptyObjById_common(temp);
		HttpResult result = new HttpResult(true, "");
		if (resultStatus == 0) {
			result.setSuccess(false);
		}
		return result;
	}

	/**
	 * 方法描述: 获取用户列表
	 * 
	 * @param page
	 * @param limit
	 * @return LayuiTableData 作者: admin 创建时间: 2018年4月23日 下午2:09:48 修改人: 修改时间:
	 *         修改内容: 修改次数: 0
	 */
	@RequestMapping({ "getUserManagerList" })
	@ResponseBody
	public LayuiTableData getUserManagerList(@RequestParam(required = true, defaultValue = "1")
														 Integer page, @RequestParam(required = false, defaultValue = "10")
			Integer limit, UserDto user) {

		LayuiTableData result = new LayuiTableData();
		String userId = UserUtil.getLoginUserId();
		User users = userService.selectObjById_common(userId);

		if (UserUtil.isAdmin()){
			Assist assist = new Assist();
			assist.setRequires(Assist.andLike("fullname", user.getFullname()));
			PageData<User> pageData = userService.selectObj_common(assist, page, limit);
			List<UserDto> userDtoList = new ArrayList<>();
			for(User userTemp:pageData.getList()){
				UserDto userDto = new UserDto(userTemp,null);
				userDto.setUserTypeStr(DictUtils.getDictLabel(userTemp.getUserType(),"user_type",""));
				userDtoList.add(userDto);
			}
			result.setCount(pageData.getCount());
			result.setData(userDtoList);
			result.setCode(Integer.valueOf(0));
			result.setMsg("");
		}else {
			PageHelper.startPage(page, limit);
			/*user.setId(currentUser.getId());*/
			user.setId(users.getId());
			List<User> list = userService.selectUserListByChildGroups(user);
			PageInfo<User> pageInfo = new PageInfo(list);
			result.setCode(0);
			result.setMsg("");
			result.setCount((int) pageInfo.getTotal());
			result.setData(list);
		}
		return result;
	}

	/**
	 * 方法描述: 根据ID获取用户
	 * 
	 * @param id
	 * @return HttpResult 作者: admin 创建时间: 2018年4月23日 下午2:10:10 修改人: 修改时间: 修改内容:
	 *         修改次数: 0
	 */
	@RequestMapping({ "getUserById" })
	@ResponseBody
	public HttpResult getUserById(@RequestParam(required = true) String id, HttpServletRequest request) {
		User user = userService.selectObjById_common(id);
		Group group=groupService.selectObjById_common(user.getGroupId());
		UnitFile uf = unitFileService.selectObjById_common(user.getAvatar());

		String upath =  AppFileProperties.getServerPath();
		//组织树
		Map<String, Object> map = getGoupManageTrees();
		if(uf!=null) {
			String fileName = uf.getSourceUrl();
			map.put("fileName", fileName);
		}
		String url = request.getScheme()+"://"+ request.getServerName()+":"+request.getServerPort()+"/"+upath+"/";
		map.put("unitFile", uf);
		map.put("url",url);
		map.put("user", user);
		map.put("group",group);
		HttpResult result = new HttpResult(true, "");
		result.setData(map);
		return result;
	}

	/**
	 * 方法描述: 保存和更新用户
	 * 
	 * @param session
	 * @param fileJson
	 * @param id
	 * @param pswd
	 * @param username
	 * @param fullname
	 * @param phone
	 * @param email
	 * @param comment
	 * @param orderId
	 * @param theme
	 * @return HttpResult 作者: admin 创建时间: 2018年4月23日 下午2:14:49 修改人: 修改时间: 修改内容:
	 *         修改次数: 0
	 */
	@RequestMapping({ "saveUser" })
	@ResponseBody
	public HttpResult saveUser(HttpServletRequest request,HttpSession session, String fileJson, String id, String pswd, String username, String groupId, String fullname, String phone, String email, String comment, Float orderId, String theme,String userType) {
		HttpResult result = new HttpResult(true, "保存成功");
		try{

			String realPath = session.getServletContext().getRealPath("/");
			int resultStatus;
			User newUser = new User();
			newUser.setId(id);
			if (!StringUtil.empty(pswd)) {
				newUser.setPswd(PasswordUtil.encrypt(username, pswd));
			}
			newUser.setUsername(username);
			newUser.setGroupId(groupId);
			newUser.setFullname(fullname);
			newUser.setPhone(phone);
			newUser.setEmail(email);
			newUser.setComment(comment);
			newUser.setOrderId(orderId);
			newUser.setTheme(theme);
			newUser.setUserType(userType);
			List<UnitFile> list = unitFileService.uploadFiles(fileJson, realPath, null, null, null, TokenUtil.getUserId());
			if (list != null && list.size() == 1) {
				newUser.setAvatar(list.get(0).getId());

			}
			if (!StringUtil.empty(id)) {
				// 更新用户
				User user = userService.selectObjById_common(id);
				//当选着组织为自己时，提示不成功
				String userId = UserUtil.getLoginUserId();
				User users = userService.selectObjById_common(userId);
				if (!UserUtil.isAdmin()) {
					if (groupId.equals(users.getGroupId())) {
//						HttpResult result = new HttpResult(true, "");
						result.setSuccess(false);
						result.setError("请选择您的下级组织！");
						return result;
					}
				}
				if (list != null && list.size() == 1) {
					// 有头像更新，删除原来的头像
					String a = TokenUtil.getUserId();
					unitFileService.deleteFile(user.getAvatar(), TokenUtil.getUserId());
				}
				resultStatus = userService.updateNonEmptyObjById_common(newUser);
			} else {
				//当选着组织为自己时，提示不成功
				String userId = UserUtil.getLoginUserId();
				User user = userService.selectObjById_common(userId);
				if (!UserUtil.isAdmin()) {
					if (groupId.equals(user.getGroupId())) {
//						HttpResult result = new HttpResult(true, "");
						result.setSuccess(false);
						result.setError("请选择您的下级组织！");
						return result;
					}
				}
				// 添加新的用户
				resultStatus = userService.insertNonEmptyObj_common(newUser);
			}
			if (resultStatus == 0) {
				result.setSuccess(false);
			}

			return result;
		}catch (Exception e){
			LogUtil.printStackTraceToLog(request,e, logger, SysEnums.HTTP_500,null);
		}
		return result;
	}

	/**
	 * 方法描述: 删除用户
	 * 
	 * @param id
	 * @return HttpResult 作者: admin 创建时间: 2018年4月23日 下午2:34:36 修改人: 修改时间: 修改内容:
	 *         修改次数: 0
	 */
	@RequestMapping({ "deleteUser" })
	@ResponseBody
	public HttpResult deleteUser(@RequestParam(required = true) String id) {

		int resultStatus = userService.deleteObjById_common(id);

		//删除用户之后把用户对应关系表里面的数据删除
		Assist assist=new Assist();
		assist.setRequires(Assist.andEq("user_id",id));
		userRoleService.deleteObj_common(assist);

		HttpResult result = new HttpResult(true, "");
		if (resultStatus == 0) {
			result.setSuccess(false);
		}
		return result;
	}

	/**
	 * 方法描述: 根据用户名获取用户
	 * 
	 * @param userName
	 * @return HttpResult 作者: admin 创建时间: 2018年4月23日 下午2:35:11 修改人: 修改时间: 修改内容:
	 *         修改次数: 0
	 */
	@RequestMapping({ "getUserByUserName" })
	@ResponseBody
	public HttpResult getUserByUserName(String userName) {
		User temp = new User();
		temp.setUsername(userName);
		User user = userService.selectObjByObj_common(temp);
		HttpResult result = new HttpResult(true, "");
		result.setData(user);
		return result;
	}

	/**
	 * 方法描述: 通过用户名获取非当前登录用户信息
	 * 
	 * @param id
	 * @param userName
	 * @return HttpResult 作者: admin 创建时间: 2018年4月23日 下午2:40:43 修改人: 修改时间: 修改内容:
	 *         修改次数: 0
	 */
	@RequestMapping({ "getUserByUserNameOutCurrent" })
	@ResponseBody
	public HttpResult getUserByUserNameOutCurrent(String id, String userName) {
		User user = userService.getByUsername(userName);
		HttpResult result = new HttpResult(true, "");
		if (user != null) {
			if (user.getId().equals(id)) {
				result.setData(null);
			} else {
				result.setData(user);
			}
		} else {
			result.setData(null);
		}
		return result;
	}

	/**
	 * 方法描述: 通过用户ID获取菜单、权限列表
	 * 
	 * @param id
	 * @return HttpResult 作者: admin 创建时间: 2018年4月23日 下午2:42:13 修改人: 修改时间: 修改内容:
	 *         修改次数: 0
	 */
	@RequestMapping({ "getPermissionListByUserId" })
	@ResponseBody
	public HttpResult getPermissionListByUserId(String id) {
		List<Permission> list = permissionService.getPermissionListByUserId(id);
		List<Permission> menuList = new ArrayList<>();
		List<Permission> operationList = new ArrayList<>();

		for (Permission permission : list) {
			if (permission.getPermissionType().equals("menu")) {
				menuList.add(permission);
			}
			if (permission.getPermissionType().equals("operation")) {
				operationList.add(permission);
			}
		}

		List<Permission> menuLevel_1_List = new ArrayList<>();
		List<Permission> menuLevel_2_List = new ArrayList<>();
		for (Permission menu : menuList) {
			if (menu.getLevel().intValue() == 1) {
				menuLevel_1_List.add(menu);
			}
			if (menu.getLevel().intValue() == 2) {
				menuLevel_2_List.add(menu);
			}
		}

		List<PermissionTree> menuPtList = new ArrayList<>();
		for (Permission menuLevel_1 : menuLevel_1_List) {
			PermissionTree pt_level_1 = new PermissionTree();
			pt_level_1.setName(menuLevel_1.getPermissionName());
			pt_level_1.setValue(menuLevel_1.getPermissionKey());
			List<PermissionTree> childList = new ArrayList<>();
			for (Permission menuLevel_2 : menuLevel_2_List) {
				if (menuLevel_2.getParentId().equals(menuLevel_1.getId())) {
					PermissionTree childPt = new PermissionTree();
					childPt.setName(menuLevel_2.getPermissionName());
					childPt.setValue(menuLevel_2.getPermissionKey());
					childList.add(childPt);
				}
			}
			pt_level_1.setChildren(childList);
			menuPtList.add(pt_level_1);
		}

		List<PermissionTree> perPtList = new ArrayList<>();
		for (Permission operation : operationList) {
			PermissionTree perPt = new PermissionTree();
			perPt.setName(operation.getPermissionName());
			perPt.setValue(operation.getPermissionKey());
			perPtList.add(perPt);
		}
		Map<String, Object> map = new HashMap<>();
		map.put("menu", menuPtList);
		map.put("permission", perPtList);
		HttpResult result = new HttpResult(true, "");
		result.setData(map);
		return result;
	}

	/**
	 * 方法描述: 获取组织选择列表数据
	 *
	 * @return HttpResult 作者: admin 创建时间: 2018年4月28日 下午2:37:14 修改人: 修改时间: 修改内容:
	 *         修改次数: 0
	 */
	@RequestMapping({ "/getGroupIdData" })
	@ResponseBody
	public HttpResult getGroupIdData(String groupId) {
		List<SelectDto<String, String>> selectDtoList = new ArrayList<>();
		selectDtoList.add(new SelectDto<String, String>("系统级", ""));
		User user = TokenUtil.getToken();
		List<Group> list = groupService.getGroupListByUserId(user.getId(), user.getGroupId());
		for (Group group : list) {
			SelectDto<String, String> s = new SelectDto<String, String>(group.getGroupName(), group.getId());
			if (groupId != null) {
				if (group.getId().equals(groupId)) {
					s.setSelected(true);
				}
			}
			selectDtoList.add(s);
		}
		return new HttpResult(true, selectDtoList);
	}

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


	/**
	 * 方法描述: 获取组织选择列表数据
	 *
	 * @return HttpResult 作者: admin 创建时间: 2018年4月28日 下午2:37:14 修改人: 修改时间: 修改内容:
	 *         修改次数: 0
	 */
	@RequestMapping({ "/getRoleUser" })
	@ResponseBody
	public HttpResult getRoleUser(String deviceId) {
		HttpResult result =new HttpResult(true, "");

		Map<String, Object> map = new HashMap<>();

		String userId = UserUtil.getLoginUserId();
		//获取当前登录用户信息
		User user = userService.selectObjById_common(userId);
		//获取当前登录用户以下组织
		List<Group> groupList = groupService.selectSon_common(user.getGroupId(),null);
		//获取当前用户组织
		Group group = groupService.selectObjById_common(user.getGroupId());
		//获取当前登录用户组织及以下组织
		groupList.add(group);

		//查询当前登录用户组织及以下组织的用户
		UserDto userDto = new UserDto();
		userDto.setId(userId);
		List<User> userList = userService.selectUserListByChildGroups(userDto);

		List<Group> deleteGroup= new ArrayList<>();
		for(int i=0;i<groupList.size();i++){
             for(int j=0;j<userList.size();j++){
				 if(userList.get(j).getGroupId().equals(groupList.get(i).getId())){
					 break;
				 }
				 if(j==(userList.size()-1)){
					 deleteGroup.add(groupList.get(i));
				 }
			 }
		}
		groupList.removeAll(deleteGroup);
		//根据传过来的设备id查询相关用户并设值
		Assist assist = new Assist();
		assist.setRequires(Assist.andEq("device_id",deviceId));
		List<UserDevice> userDeviceList = userDeviceService.selectObj_common(assist);
		//设置复选框是否选中
		List<UserDto> userDtoList = new ArrayList<>();
		for(int i=0;i<userList.size();i++){
			if(userDeviceList.size()==0){
				UserDto userDto1 = new UserDto(userList.get(i),null);
				userDtoList.add(userDto1);
			}
			for(int j=0;j<userDeviceList.size();j++){
				if(userList.get(i).getId().equals(userDeviceList.get(j).getUserId())){
					UserDto userDto1 = new UserDto(userList.get(i),null);
					userDto1.setCheckbox(true);
					userDtoList.add(userDto1);
					break;
				}
				if(j==(userDeviceList.size()-1)){
					UserDto userDto1 = new UserDto(userList.get(i),null);
					userDtoList.add(userDto1);
				}
			}

		}
		map.put("groupList",groupList);
		map.put("userList",userDtoList);
		result.setData(map);
		return result;
	}

	/**
	 * 方法描述: 修改密码
	 *
	 * @return HttpResult 作者: admin 创建时间: 2018年4月28日 下午2:37:14 修改人: 修改时间: 修改内容:
	 *         修改次数: 0
	 */
	@RequestMapping({ "/pwdEdit" })
	@ResponseBody
	public HttpResult pwdEdit(String username,String pswd) {
		User user = userService.getByUsername(username);
		if (!StringUtil.empty(pswd)) {
			user.setPswd(PasswordUtil.encrypt(username, pswd));
		}
		userService.updateNonEmptyObjById_common(user);
		return new HttpResult(true, "");
	}

	/**
	 * 方法描述: 重置密码
	 *
	 * @return HttpResult 作者: admin 创建时间: 2018年4月28日 下午2:37:14 修改人: 修改时间: 修改内容:
	 *         修改次数: 0
	 */
	@RequestMapping({ "/pwdReset" })
	@ResponseBody
	public HttpResult resetEdit(String username) {
		String pwd = "123456";
		User user = userService.getByUsername(username);
		user.setPswd(PasswordUtil.encrypt(username, pwd));
		userService.updateNonEmptyObjById_common(user);
		return new HttpResult(true, "");
	}
}
