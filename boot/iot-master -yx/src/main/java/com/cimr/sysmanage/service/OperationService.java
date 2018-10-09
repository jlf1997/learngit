package com.cimr.sysmanage.service;

import java.util.List;

import com.cimr.sysmanage.bo.OperationBo;
import com.cimr.util.Assist;
import com.cimr.util.PageData;

public interface OperationService {
	
	PageData<OperationBo> getOperationList(Assist assist, int page, int limit);
	  
	List<OperationBo> getOperationListByUserId(String paramString);
	  
	List<OperationBo> getOperationListByUsername(String paramString);
	  
	List<OperationBo> getOperationListByRoleId(String roleId);
	  
	OperationBo getOperationById(String paramString);
	  
	List<OperationBo> getOperationListWithName(String paramString, int page, int limit);
	  
	List<OperationBo> getOperationListByKey(String paramString);
	  
}
