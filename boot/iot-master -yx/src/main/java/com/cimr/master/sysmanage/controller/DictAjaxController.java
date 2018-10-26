package com.cimr.master.sysmanage.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.cimr.comm.enums.SysEnums;
import com.cimr.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cimr.sysmanage.dto.HttpResult;
import com.cimr.sysmanage.dto.LayuiTableData;
import com.cimr.sysmanage.dto.SelectDto;
import com.cimr.sysmanage.model.Dict;
import com.cimr.sysmanage.service.DictService;
import com.cimr.util.Assist;
import com.cimr.util.PageData;
import com.cimr.util.StringUtil;

@Controller
@RequestMapping("/dict/ajax")
public class DictAjaxController {

	private static final Logger logger = LoggerFactory.getLogger(DictAjaxController.class);

	@Autowired
	private DictService dictService;

	/**
	 * 方法描述: 查询字典列表
	 * @param page
	 * @param limit
	 * @param allType
	 * @param descriptions
	 * @return
	 * 		LayuiTableData
	 * 作者:    admin
	 * 创建时间: 2018年4月23日 下午5:37:04
	 * 修改人:
	 * 修改时间:
	 * 修改内容:
	 * 修改次数: 0
	 */
	@RequestMapping({ "getList" })
	@ResponseBody
	public LayuiTableData list(@RequestParam(required = true, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "10") Integer limit, @RequestParam(required = false) String allType, String descriptions) {
		Assist assist = new Assist();
		assist.setRequires(Assist.andEq("type", allType));
		assist.setRequires(Assist.andLike("description", descriptions));
		PageData<Dict> pagaData = dictService.selectObj_common(assist, page, limit);
		LayuiTableData result = new LayuiTableData();
		result.setCode(0);
		result.setMsg("");
		result.setCount(pagaData.getCount());
		result.setData(pagaData.getList());
		return result;
	}

	/**
	 * 方法描述: 保存和更新字典
	 * @param dict
	 * @param session
	 * @return
	 * 		HttpResult
	 * 作者:    admin
	 * 创建时间: 2018年4月23日 下午5:37:47
	 * 修改人:
	 * 修改时间:
	 * 修改内容:
	 * 修改次数: 0
	 */
	@RequestMapping({ "save" })
	@ResponseBody
	public HttpResult save(Dict dict, HttpSession session,HttpServletRequest request) {

		HttpResult res = new HttpResult(true, "添加成功");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			if (StringUtil.empty(dict.getId())) {
				//插入新的字典
				if (dictService.isExist(dict.getType(), dict.getValue())) {
					resultMap.put("result", 1);
				} else {
					// 添加操作
					dictService.insertNonEmptyObj_common(dict);
				}
			} else {
				if (StringUtil.valid(dict.getType()) && StringUtil.valid(dict.getValue())) {
					// 更新字典
					Dict dict1 = dictService.selectObjById_common(dict.getId());
					if (!dict.getType().equals(dict1.getType()) || !dict.getValue().equals(dict1.getValue())) {
						if (dictService.isExist(dict.getType(), dict.getValue())) {
							resultMap.put("result", 1);
						}
					}
				}
				dictService.updateNonEmptyObjById_common(dict);
			}
		} catch (Exception e) {
			res.setSuccess(false);
			res.setError("操作失败，系统出现异常！");
			LogUtil.printStackTraceToLog(request,e, logger, SysEnums.HTTP_500,null);
		}
		res.setData(resultMap);
		return res;
	}

	// 删除
	@RequestMapping({ "delete" })
	@ResponseBody
	public HttpResult delete(String Id,HttpServletRequest request) {
		HttpResult res = new HttpResult(true, "删除成功");
		try {
			dictService.deleteObjById_common(Id);
		} catch (Exception e) {
			res.setSuccess(false);
			res.setError("操作失败，系统出现异常！");
			LogUtil.printStackTraceToLog(request,e, logger, SysEnums.HTTP_500,null);
		}
		return res;
	}

	// 查询单个dictInfo数据
	@RequestMapping({ "getDictInfo" })
	@ResponseBody
	public HttpResult edit(@RequestParam(required = true) String Id,HttpServletRequest request) {
		HttpResult res = new HttpResult(true, "获取成功");
		try {
			Dict dict = dictService.selectObjById_common(Id);
			res.setData(dict);
		} catch (Exception e) {
			res.setSuccess(false);
			res.setError("操作失败，系统出现异常！");
			LogUtil.printStackTraceToLog(request,e, logger, SysEnums.HTTP_500,null);
		}
		return res;
	}

	/**
	 *
	 * @param allData 是否查询所有字典
	 * @param type 查询哪个类型的字典
	 * @param value 需要回显的值
	 * @param txt 下拉框的提示语,比如"请选择xxx"
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getDictData")
	public HttpResult getSelectData(Boolean allData, String type, String value,String txt) {
		if (allData == null) {
			allData = false;
		}
		HttpResult result = new HttpResult(true, "");
		// originalData为true的时候代表需要获取原始列表数据
		if (allData == true) {
			List<Dict> dicts = dictService.selectObj_common(new Assist());
			result.setData(dicts);
		} else {
			List<SelectDto<String, String>> selectDtoList = new ArrayList<>();
			if (StringUtil.empty(txt)){
				txt = "请选择";
			}
			selectDtoList.add(new SelectDto<>(txt, ""));
			Assist assist = new Assist();
			assist.setRequires(Assist.andEq("type", type));
			List<Dict> list = dictService.selectObj_common(assist);
			for (Dict a : list) {
				SelectDto<String, String> selectDto = new SelectDto<>(a.getLabel(), a.getValue());
				if (a.getValue().equals(value)) {
					selectDto.setSelected(true);
				}
				selectDtoList.add(selectDto);
			}
			result.setData(selectDtoList);

		}
		return result;
	}
}
