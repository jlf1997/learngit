package com.cimr.master.sysmanage.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.cimr.sysmanage.dao.UserDao;
import com.cimr.sysmanage.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimr.sysmanage.dao.GroupDao;
import com.cimr.sysmanage.model.Group;
import com.cimr.sysmanage.service.GroupService;
import com.cimr.util.Assist;
import com.cimr.util.PageData;
import com.cimr.util.StringUtil;

@Service("groupService")
public class GroupServiceImpl implements GroupService {
	@Autowired
	private GroupDao dao;

	@Override
	public List<Group> getGroupAndSonsByGroupId(String groupId) {
		Assist assist = new Assist();
		assist.setRequires(Assist.andEq("id", groupId));
		assist.setRequires(Assist.orEq("parent_id", groupId));
		return dao.selectObj_common(assist);
	}

	@Override
	public List<Group> getGroupListByUserId(String userId, String groupId) {
		if (StringUtil.empty(userId)) {
			return null;
		}
		List<Group> groupList = null;
		// 获取用户绑定的租户ID
		if (!StringUtil.empty(groupId)) {
			// 获取绑定的组织
			Group group = this.selectObjById_common(groupId);
			groupList = new ArrayList<>();
			groupList.add(group);
		} else {
			// 获取所有组织
			groupList = this.selectObj_common(null);
		}
		return groupList;
	}

	@Override
	public List<Group> selectSon_common(String groupId,String groupName) {
		Group group= new Group();
		group.setId(groupId);
		group.setGroupName(groupName);
		return dao.selectSon_common(group);
	}

	@Override
	public List<Group> selectFather_common(String groupId) {
		Group group= new Group();
		group.setId(groupId);
		return dao.selectFather_common(group);
	}

	@Override
	public List<Group> selectUpdate_common(String groupId, String updateId) {
		Group group= new Group();
		group.setId(groupId);
		group.setUpdateId(updateId);
		return dao.selectUpdate_common(group);
	}

	@Override
	public boolean isGroupName(String groupName) {
		Assist assist=new Assist();
		assist.setRequires(Assist.andEq("group_name",groupName));
		List<Group> list =this.selectObj_common(assist);
		if(list==null||list.size()==0){
           return false;
		}else {
			return true;
		}
	}


	// 公共方法-----开始

	@Override
	public long getObjRowCount_common(Assist assist) {
		if (null == assist) {
    		assist = new Assist();
    	}
		assist.setRequires(Assist.andEq("del_flag", 0));
		return dao.getObjRowCount_common(assist);
	}

	@Override
	public List<Group> selectObj_common(Assist assist) {
		if (null == assist) {
    		assist = new Assist();
    	}
		assist.setRequires(Assist.andEq("del_flag", 0));
		assist.setOrder(Assist.order("order_id, id", true));
		return dao.selectObj_common(assist);
	}

	@Override
	public PageData<Group> selectObj_common(Assist assist, int page, int limit) {
		if (null == assist) {
    		assist = new Assist();
    	}
		PageData<Group> pageData = new PageData<>();
		Long count = this.getObjRowCount_common(assist);
		int startRow = limit * (page - 1);
		assist.setStartRow(startRow);
		assist.setRowSize(limit);
		assist.setOrder(Assist.order("order_id, id", true));
		List<Group> list = dao.selectObj_common(assist);
		pageData.setCount(count.intValue());
		pageData.setList(list);
		return pageData;
	}

	@Override
	public Group selectObjByObj_common(Group obj) {
		return dao.selectObjByObj_common(obj);
	}

	@Override
	public Group selectObjById_common(String id) {
		return dao.selectObjById_common(id);
	}

	@Override
	public int insertObj_common(Group value) {
		value.preInsert();
		return dao.insertObj_common(value);
	}

	@Override
	public int insertNonEmptyObj_common(Group value) {
		value.preInsert();
		return dao.insertNonEmptyObj_common(value);
	}

	@Override
	public int insertObjByBatch_common(List<Group> value) {
		for (Group temp : value) {
			temp.preInsert();
		}
		return dao.insertObjByBatch_common(value);
	}

	@Override
	public int deleteObjById_common(String id) {
		Group temp = new Group();
		temp.setId(id);
		temp.setDelFlag(1);
		temp.preUpdate();
		return dao.updateNonEmptyObjById_common(temp);
	}

	@Override
	public int deleteObj_common(Assist assist) {
		Group temp = new Group();
		temp.setDelFlag(1);
		temp.preUpdate();
		return dao.updateNonEmptyObj_common(temp, assist);
	}

	@Override
	public int updateObjById_common(Group enti) {
		enti.preUpdate();
		return dao.updateObjById_common(enti);
	}

	@Override
	public int updateObj_common(Group value, Assist assist) {
		value.preUpdate();
		return dao.updateObj_common(value, assist);
	}

	@Override
	public int updateNonEmptyObjById_common(Group enti) {
		enti.preUpdate();
		return dao.updateNonEmptyObjById_common(enti);
	}

	@Override
	public int updateNonEmptyObj_common(Group value, Assist assist) {
		value.preUpdate();
		return dao.updateNonEmptyObj_common(value, assist);
	}

	@Override
	public int deleteObjById_common(Group enti) {
		return 0;
	}

	@Override
	public int deleteObj_common(Group enti, Assist assist) {
		return 0;
	}

	// 公共方法-----结束
}
