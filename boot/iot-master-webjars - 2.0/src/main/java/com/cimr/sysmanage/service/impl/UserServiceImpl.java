package com.cimr.sysmanage.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimr.sysmanage.dao.UserDao;
import com.cimr.sysmanage.dto.UserDto;
import com.cimr.sysmanage.model.User;
import com.cimr.sysmanage.model.UserRole;
import com.cimr.sysmanage.service.GroupService;
import com.cimr.sysmanage.service.UserRoleService;
import com.cimr.sysmanage.service.UserService;
import com.cimr.util.Assist;
import com.cimr.util.PageData;
import com.cimr.util.StringUtil;

@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao dao;

	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private GroupService groupService;

	@Override
	public User login(String username, String pswd) {
		User temp = new User();
		temp.setUsername(username);
		temp.setPswd(pswd);
		User user = this.selectObjByObj_common(temp);
		return user;
	}

	@Override
	public User getByUsername(String username) {
		User temp = new User();
		temp.setUsername(username);
		User user = this.selectObjByObj_common(temp);
		return user;
	}

	@Override
	public int saveUserAndRoles(String userId, String[] roleIdsArray) {
		int resultCount = 0;
		if (StringUtil.empty(userId)) {
			return resultCount;
		}
		Assist assist = new Assist();
		//删除原来的角色设置
		assist.setRequires(Assist.andEq("user_id", userId));
	    userRoleService.deleteObj_common(assist);
	    
	    if (roleIdsArray == null || roleIdsArray.length == 0) {
	    	return resultCount;
	    }
	    //添加新的角色设置
	    List<UserRole> userRolesForAdd = new ArrayList<>();
	    UserRole userRole = null;
	    for (String roleId : roleIdsArray) {
	    	userRole = new UserRole();
	    	userRole.setUserId(userId);
	    	userRole.setRoleId(roleId);
	    	userRolesForAdd.add(userRole);
	    }
	    if (userRolesForAdd.size() > 0) {
	    	resultCount = userRoleService.insertObjByBatch_common(userRolesForAdd);
	    }
	    return resultCount;
	}

	// 公共方法-------开始

	@Override
	public long getObjRowCount_common(Assist assist) {
		if (null == assist) {
    		assist = new Assist();
    	}
		assist.setRequires(Assist.andEq("del_flag", 0));
		return dao.getObjRowCount_common(assist);
	}

	@Override
	public List<User> selectObj_common(Assist assist) {
		if (null == assist) {
    		assist = new Assist();
    	}
		assist.setRequires(Assist.andEq("del_flag", 0));
		assist.setOrder(Assist.order("order_id, id", true));
		return dao.selectObj_common(assist);
	}

	@Override
	public PageData<User> selectObj_common(Assist assist, int page, int limit) {
		if (null == assist) {
    		assist = new Assist();
    	}
		PageData<User> pageData = new PageData<>();
		Long count = this.getObjRowCount_common(assist);
		int startRow = limit * (page - 1);
		assist.setStartRow(startRow);
		assist.setRowSize(limit);
		assist.setOrder(Assist.order("order_id, id", true));
		List<User> list = dao.selectObj_common(assist);
		pageData.setCount(count.intValue());
		pageData.setList(list);
		return pageData;
	}

	@Override
	public User selectObjByObj_common(User obj) {
		return dao.selectObjByObj_common(obj);
	}

	@Override
	public User selectObjById_common(String id) {
		return dao.selectObjById_common(id);
	}

	@Override
	public int insertObj_common(User value) {
		value.preInsert();
		return dao.insertObj_common(value);
	}

	@Override
	public int insertNonEmptyObj_common(User value) {
		value.preInsert();
		return dao.insertNonEmptyObj_common(value);
	}

	@Override
	public int insertObjByBatch_common(List<User> value) {
		for (User temp : value) {
			temp.preInsert();
		}
		return dao.insertObjByBatch_common(value);
	}

	@Override
	public int deleteObjById_common(String id) {
		User temp = new User();
		temp.setId(id);
		temp.setDelFlag(1);
		temp.preUpdate();
		return dao.updateNonEmptyObjById_common(temp);
	}

	@Override
	public int deleteObj_common(Assist assist) {
		User temp = new User();
		temp.setDelFlag(1);
		temp.preUpdate();
		return dao.updateNonEmptyObj_common(temp, assist);
	}

	@Override
	public int updateObjById_common(User enti) {
		enti.preUpdate();
		return dao.updateObjById_common(enti);
	}

	@Override
	public int updateObj_common(User value, Assist assist) {
		value.preUpdate();
		return dao.updateObj_common(value, assist);
	}

	@Override
	public int updateNonEmptyObjById_common(User enti) {
		enti.preUpdate();
		return dao.updateNonEmptyObjById_common(enti);
	}

	@Override
	public int updateNonEmptyObj_common(User value, Assist assist) {
		value.preUpdate();
		return dao.updateNonEmptyObj_common(value, assist);
	}

	@Override
	public int deleteObjById_common(User enti) {
		return 0;
	}

	@Override
	public int deleteObj_common(User enti, Assist assist) {
		return 0;
	}

	// 公共方法-------结束


	@Override
	public List<User> selectUserListByChildGroups(UserDto user) {
		//判断是否有userId
		String id = user.getId();
		if (null == id || "".equals(id)){
			return null;
		}

		//判断是否有组织ID
		String groupId = user.getCurrentUserGroupId();
		if (null == groupId || "".equals(groupId)){
			User temp = this.selectObjById_common(id);
			if(null != temp){
				user.setCurrentUserGroupId(temp.getGroupId());
			}
		}
		return dao.selectUserListByChildGroups(user);
	}

	@Override
	public List<User> selectUserListByParentGroups(UserDto user) {
		//判断是否有userId
		String id = user.getId();
		if (null == id || "".equals(id)){
			return null;
		}
		//判断是否有组织ID
		String groupId = user.getGroupId();
		if (null == groupId || "".equals(groupId)){
			User temp = this.selectObjById_common(id);
			if(null != temp){
				user.setCurrentUserGroupId(temp.getGroupId());
			}
		}
		return dao.selectUserListByParentGroups(user);
	}
}
