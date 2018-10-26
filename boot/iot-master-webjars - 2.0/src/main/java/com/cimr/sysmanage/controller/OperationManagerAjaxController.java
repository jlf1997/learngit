package com.cimr.sysmanage.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cimr.comm.constants.PermissionTypeEnum;
import com.cimr.sysmanage.bo.OperationBo;
import com.cimr.sysmanage.dto.HttpResult;
import com.cimr.sysmanage.dto.LayuiTableData;
import com.cimr.sysmanage.model.Permission;
import com.cimr.sysmanage.service.OperationService;
import com.cimr.sysmanage.service.PermissionService;
import com.cimr.util.Assist;
import com.cimr.util.PageData;
import com.cimr.util.StringUtil;

@Controller
@RequestMapping({ "/operationManager/ajax" })
public class OperationManagerAjaxController {

	private static final Logger logger = LoggerFactory.getLogger(OperationManagerAjaxController.class);

	@Autowired
	private OperationService operationService;
	
	@Autowired
	private PermissionService permissionService;

	/**
	 * 方法描述: 分页获取操作列表
	 * @param page
	 * @param limit
	 * @param operationName
	 * @return
	 * 		LayuiTableData
	 * 作者:    admin
	 * 创建时间: 2018年5月4日 上午9:10:43
	 * 修改人:
	 * 修改时间:
	 * 修改内容:
	 * 修改次数: 0
	 */
	@RequestMapping({ "getOperationList" })
	@ResponseBody
	public LayuiTableData getOperationList(@RequestParam(required = true, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "10") Integer limit, @RequestParam(required = false) String operationName) {
		LayuiTableData result = new LayuiTableData();
		Assist assist = new Assist();
		assist.setRequires(Assist.andLike("permission_name", operationName));
		PageData<OperationBo> pageData = operationService.getOperationList(assist, page, limit);
		result.setCode(Integer.valueOf(0));
		result.setMsg("");
		result.setCount(pageData.getCount());
		result.setData(pageData.getList());
		return result;
	}

	/**
	 * 方法描述: 根据角色ID获取操作列表
	 * @param id
	 * @return
	 * 		HttpResult
	 * 作者:    admin
	 * 创建时间: 2018年5月4日 上午9:28:26
	 * 修改人:
	 * 修改时间:
	 * 修改内容:
	 * 修改次数: 0
	 */
	@RequestMapping({ "getOperationByRoleId" })
	@ResponseBody
	public HttpResult getOperationByRoleId(@RequestParam(required = true) String id) {
		List<OperationBo> currentList = operationService.getOperationListByRoleId(id);
		HttpResult result = new HttpResult(true, "");
		result.setData(currentList);
		return result;
	}

	/**
	 * 方法描述: 根据操作ID获取操作记录
	 * @param id
	 * @return
	 * 		HttpResult
	 * 作者:    admin
	 * 创建时间: 2018年5月4日 上午9:28:45
	 * 修改人:
	 * 修改时间:
	 * 修改内容:
	 * 修改次数: 0
	 */
	@RequestMapping({ "getOperationById" })
	@ResponseBody
	public HttpResult getOperationById(@RequestParam(required = true) String id) {
		OperationBo operationBo = operationService.getOperationById(id);
		HttpResult result = new HttpResult(true, "");
		result.setData(operationBo);
		return result;
	}

	/**
	 * 方法描述: 保存和更新操作
	 * @param id
	 * @param operationKey
	 * @param operationName
	 * @param comment
	 * @param orderId
	 * @return
	 * 		HttpResult
	 * 作者:    admin
	 * 创建时间: 2018年5月4日 上午9:29:01
	 * 修改人:
	 * 修改时间:
	 * 修改内容:
	 * 修改次数: 0
	 */
	@RequiresPermissions("operationManager:saveOperation")
	@RequestMapping({ "saveOperation" })
	@ResponseBody
	public HttpResult saveOperation(String id, String operationKey, String operationName, String comment, Float orderId) {
		int resultStatus = 0;
		Permission permission = new Permission();
		permission.setId(id);
		permission.setPermissionKey(operationKey);
		permission.setPermissionName(operationName);
		permission.setComment(comment);
		permission.setOrderId(orderId);
		permission.setPermissionType(PermissionTypeEnum.OPERATION.getKey());
		if (!StringUtil.empty(id)) {
			resultStatus = permissionService.updateNonEmptyObjById_common(permission);
		} else {
			resultStatus = permissionService.insertNonEmptyObj_common(permission);
		}

		HttpResult result = new HttpResult(true, "保存成功");
		if (resultStatus == 0) {
			result.setSuccess(false);
			result.setError("系统异常");
		}
		return result;
	}

	/**
	 * 方法描述: 删除操作
	 * @param id
	 * @return
	 * 		HttpResult
	 * 作者:    admin
	 * 创建时间: 2018年5月4日 上午9:10:25
	 * 修改人:
	 * 修改时间:
	 * 修改内容:
	 * 修改次数: 0
	 */
	@RequestMapping({ "deleteOperation" })
	@ResponseBody
	public HttpResult deleteOperation(String id) {
		int resultStatus = permissionService.deleteByPermissionId(id);
		HttpResult result = new HttpResult(true, "");
		if (resultStatus == 0) {
			result.setSuccess(false);
			result.setError("系统错误");
		}
		return result;
	}

	/**
	 * 方法描述: 根据关键字获取操作列表
	 * @param operationKey
	 * @return
	 * 		HttpResult
	 * 作者:    admin
	 * 创建时间: 2018年5月4日 上午9:42:29
	 * 修改人:
	 * 修改时间:
	 * 修改内容:
	 * 修改次数: 0
	 */
	@RequestMapping({ "getOperationListByKey" })
	@ResponseBody
	public HttpResult getOperationListByKey(String operationKey) {
		HttpResult result = new HttpResult(true, "");
		List<OperationBo> list = operationService.getOperationListByKey(operationKey);
		if ((list != null) && (list.size() == 0)) {
			result.setData(null);
		} else {
			result.setData(list);
		}
		return result;
	}

	@RequestMapping({ "getOperationListByKeyOutCurrent" })
	@ResponseBody
	public HttpResult getOperationListByKeyOutCurrent(String id, String operationKey) {
		HttpResult result = new HttpResult(true, "");
		List<OperationBo> list = operationService.getOperationListByKey(operationKey);
		if ((list != null) && (list.size() != 0)) {
			if (((OperationBo) list.get(0)).getId().equals(id)) {
				result.setData(null);
			} else {
				result.setData(list);
			}
		} else {
			result.setData(null);
		}
		return result;
	}
}
