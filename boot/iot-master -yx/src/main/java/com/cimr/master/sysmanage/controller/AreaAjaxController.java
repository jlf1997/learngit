package com.cimr.master.sysmanage.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.cimr.sysmanage.model.Area;
import com.cimr.sysmanage.service.AreaService;
import com.cimr.util.Assist;
import com.cimr.util.PageData;
import com.cimr.util.StringUtil;

/**
 * Created by suhuanzhao on 2018/1/3.
 */
@Controller
@RequestMapping("/area/ajax")
public class AreaAjaxController {

	private static final Logger logger = LoggerFactory.getLogger(AreaAjaxController.class);

	@Autowired
	private AreaService areaService;

	/**
	 * 列表查询
	 *
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping({ "getList" })
	@ResponseBody
	public LayuiTableData list(@RequestParam(required = true, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "10") Integer limit, @RequestParam(required = false) String title) {
		Assist assist = new Assist();
		assist.setRequires(Assist.andLike("title", title));
		PageData<Area> pagaData = areaService.selectObj_common(assist, page, limit);
		LayuiTableData result = new LayuiTableData();
		result.setCode(0);
		result.setMsg("");
		result.setCount(pagaData.getCount());
		result.setData(pagaData.getList());
		return result;
	}

	/**
	 * 保存和更新
	 *
	 * @param session
	 * @return
	 */
	@RequestMapping({ "save" })
	@ResponseBody
	public HttpResult save(Area area, HttpSession session, HttpServletRequest request) {

		HttpResult res = new HttpResult(true, "添加成功");
		try {
			if (StringUtil.empty(area.getId())) {
				// 添加操作
				areaService.addNewArea(area);
			} else {
				// 更新操作
				areaService.updateArea(area);
			}
		} catch (Exception e) {
			res.setSuccess(false);
			res.setError("操作失败，系统出现异常！");
			LogUtil.printStackTraceToLog(request,e, logger, SysEnums.HTTP_500,null);
		}
		return res;
	}

	// 删除
	@RequestMapping({ "delete" })
	@ResponseBody
	public HttpResult delete(String areaId,HttpServletRequest request) {
		HttpResult res = new HttpResult(true, "删除成功");
		try {
			areaService.deleteAreaById(areaId);
		} catch (Exception e) {
			res.setSuccess(false);
			res.setError("操作失败，系统出现异常！");
			LogUtil.printStackTraceToLog(request,e, logger, SysEnums.HTTP_500,null);
		}
		return res;
	}

	// 查询单个userInfo数据
	@RequestMapping({ "getAreaInfo" })
	@ResponseBody
	public HttpResult edit(@RequestParam(required = true) String areaId,HttpServletRequest request) {
		HttpResult res = new HttpResult(true, "获取成功");
		try {
			Area area = areaService.selectObjById_common(areaId);
			res.setData(area);
		} catch (Exception e) {
			res.setSuccess(false);
			res.setError("操作失败，系统出现异常！");
			LogUtil.printStackTraceToLog(request,e, logger, SysEnums.HTTP_500,null);
		}
		return res;
	}

	@ResponseBody
	@RequestMapping("/getAreaData")
	public HttpResult getSelectData(@RequestParam("pid") String pid, String provinceId, String cityId, String areaId,HttpServletRequest request) {
		List<SelectDto<String, String>> selectDtoList = new ArrayList<>();
		Assist assist = new Assist();
		assist.setRequires(Assist.andEq("pid", pid));
		List<Area> list = areaService.selectObj_common(assist);
		for (Area a : list) {
			SelectDto selectDto = new SelectDto<String, String>(a.getTitle(), a.getAreaId());
			if (a.getAreaId().equals(provinceId) || a.getAreaId().equals(cityId) || a.getAreaId().equals(areaId)) {
				selectDto.setSelected(true);
			}
			selectDtoList.add(selectDto);
		}
		return new HttpResult(true, selectDtoList);
	}

}
