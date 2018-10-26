package com.cimr.master.sysmanage.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimr.comm.constants.PermissionTypeEnum;
import com.cimr.comm.token.TokenUtil;
import com.cimr.sysmanage.bo.OperationBo;
import com.cimr.sysmanage.model.Permission;
import com.cimr.sysmanage.service.OperationService;
import com.cimr.sysmanage.service.PermissionService;
import com.cimr.util.Assist;
import com.cimr.util.PageData;
import com.xiaoleilu.hutool.bean.BeanUtil;

@Service("operationService")
public class OperationServiceImpl implements OperationService {
	
	@Autowired
	private PermissionService permissionService;

	@Override
	public PageData<OperationBo> getOperationList(Assist assist, int page, int limit) {
		assist.setRequires(Assist.andEq("permission_type", "operation"));
		PageData<Permission> permissionPage = permissionService.selectObj_common(assist, page, limit);
		OperationBo operation = null;
		List<OperationBo> list = new ArrayList<>();
		List<Permission> permissionList = permissionPage.getList();
		for (int i = 0; i < permissionList.size(); i++) {
			operation = new OperationBo();
			BeanUtil.copyProperties(permissionList.get(i), operation);
			operation.setOperationKey((permissionList.get(i)).getPermissionKey());
			operation.setOperationName((permissionList.get(i)).getPermissionName());
			list.add(operation);
		}
		PageData<OperationBo> pageData = new PageData<>();
		pageData.setCount(permissionPage.getCount());
		pageData.setList(list);
		return pageData;
	}

	@Override
	public List<OperationBo> getOperationListByUserId(String userId) {
		List<Permission> list = permissionService.getOperationListByUserId(userId);
		OperationBo operation = null;
		List<OperationBo> operationList = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			operation = new OperationBo();
			BeanUtil.copyProperties(list.get(i), operation);
			operation.setOperationKey(((Permission) list.get(i)).getPermissionKey());
			operation.setOperationName(((Permission) list.get(i)).getPermissionName());
			operationList.add(operation);
		}
		return operationList;
	}

	@Override
	public List<OperationBo> getOperationListByUsername(String username) {
		List<Permission> list = permissionService.getOperationListByUsername(username);
		OperationBo operation = null;
		List<OperationBo> operationList = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			operation = new OperationBo();
			BeanUtil.copyProperties(list.get(i), operation);
			operation.setOperationKey(((Permission) list.get(i)).getPermissionKey());
			operation.setOperationName(((Permission) list.get(i)).getPermissionName());
			operationList.add(operation);
		}
		return operationList;
	}

	@Override
	public List<OperationBo> getOperationListByRoleId(String roleId) {
		List<Permission> list = permissionService.getOperationListByRoleId(roleId);
		OperationBo operation = null;
		List<OperationBo> operationList = new ArrayList<>();
		for (Permission permission : list) {
			operation = new OperationBo();
			BeanUtil.copyProperties(permission, operation);
			operation.setOperationKey(permission.getPermissionKey());
			operation.setOperationName(permission.getPermissionName());
			operationList.add(operation);
		}
		return operationList;
	}

	@Override
	public OperationBo getOperationById(String id) {
		Permission permission = permissionService.selectObjById_common(id);
		OperationBo operation = new OperationBo();
		BeanUtil.copyProperties(permission, operation);
		operation.setOperationKey(permission.getPermissionKey());
		operation.setOperationName(permission.getPermissionName());
		return operation;
	}

	@Override
	public List<OperationBo> getOperationListWithName(String operationName, int page, int limit) {
		if (operationName != null) {
			operationName = operationName.trim();
		}
		List<Permission> list = permissionService.getPermissionListWithName(operationName, PermissionTypeEnum.OPERATION.getKey(), TokenUtil.getUserId());
		OperationBo operation = null;
		List<OperationBo> operationList = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			operation = new OperationBo();
			BeanUtil.copyProperties(list.get(i), operation);
			operation.setOperationKey(((Permission) list.get(i)).getPermissionKey());
			operation.setOperationName(((Permission) list.get(i)).getPermissionName());
			operationList.add(operation);
		}
		return operationList;
	}

	@Override
	public List<OperationBo> getOperationListByKey(String operationKey) {
		List<Permission> list = permissionService.getPermissionsByKey(operationKey, PermissionTypeEnum.OPERATION.getKey());
		OperationBo operation = null;
		List<OperationBo> operationList = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			operation = new OperationBo();
			BeanUtil.copyProperties(list.get(i), operation);
			operation.setOperationKey(((Permission) list.get(i)).getPermissionKey());
			operation.setOperationName(((Permission) list.get(i)).getPermissionName());
			operationList.add(operation);
		}
		return operationList;
	}

}
