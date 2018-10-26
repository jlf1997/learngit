package com.cimr.master.sysmanage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimr.sysmanage.dao.UserRoleDao;
import com.cimr.sysmanage.model.UserRole;
import com.cimr.sysmanage.service.UserRoleService;
import com.cimr.util.Assist;
import com.cimr.util.PageData;

@Service("userRoleService")
public class UserRoleServiceImpl implements UserRoleService {

	@Autowired
	private UserRoleDao dao;

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
	public List<UserRole> selectObj_common(Assist assist) {
		if (null == assist) {
    		assist = new Assist();
    	}
		assist.setRequires(Assist.andEq("del_flag", 0));
		return dao.selectObj_common(assist);
	}

	@Override
	public PageData<UserRole> selectObj_common(Assist assist, int page, int limit) {
		if (null == assist) {
    		assist = new Assist();
    	}
		PageData<UserRole> pageData = new PageData<>();
		Long count = this.getObjRowCount_common(assist);
		int startRow = limit * (page - 1);
		assist.setStartRow(startRow);
		assist.setRowSize(limit);
		List<UserRole> list = dao.selectObj_common(assist);
		pageData.setCount(count.intValue());
		pageData.setList(list);
		return pageData;
	}

	@Override
	public UserRole selectObjByObj_common(UserRole obj) {
		return dao.selectObjByObj_common(obj);
	}

	@Override
	public UserRole selectObjById_common(String id) {
		return dao.selectObjById_common(id);
	}

	@Override
	public int insertObj_common(UserRole value) {
		value.preInsert();
		return dao.insertObj_common(value);
	}

	@Override
	public int insertNonEmptyObj_common(UserRole value) {
		value.preInsert();
		return dao.insertNonEmptyObj_common(value);
	}

	@Override
	public int insertObjByBatch_common(List<UserRole> value) {
		for (UserRole temp : value) {
			temp.preInsert();
		}
		return dao.insertObjByBatch_common(value);
	}

	@Override
	public int deleteObjById_common(String id) {
		UserRole temp = new UserRole();
		temp.setId(id);
		temp.setDelFlag(1);
		temp.preUpdate();
		return dao.updateNonEmptyObjById_common(temp);
	}

	@Override
	public int deleteObj_common(Assist assist) {
		UserRole temp = new UserRole();
		temp.setDelFlag(1);
		temp.preUpdate();
		return dao.updateNonEmptyObj_common(temp, assist);
	}

	@Override
	public int updateObjById_common(UserRole enti) {
		enti.preUpdate();
		return dao.updateObjById_common(enti);
	}

	@Override
	public int updateObj_common(UserRole value, Assist assist) {
		value.preUpdate();
		return dao.updateObj_common(value, assist);
	}

	@Override
	public int updateNonEmptyObjById_common(UserRole enti) {
		enti.preUpdate();
		return dao.updateNonEmptyObjById_common(enti);
	}

	@Override
	public int updateNonEmptyObj_common(UserRole value, Assist assist) {
		value.preUpdate();
		return dao.updateNonEmptyObj_common(value, assist);
	}

	@Override
	public int deleteObjById_common(UserRole enti) {
		return 0;
	}

	@Override
	public int deleteObj_common(UserRole enti, Assist assist) {
		return 0;
	}

	//公共方法--------结束
}
