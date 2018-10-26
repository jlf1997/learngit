package com.cimr.master.sysmanage.controller;

import com.cimr.comm.aop.model.AccessLogEntity;
import com.cimr.sysmanage.dto.LayuiTableData;
import com.cimr.sysmanage.dto.SysLogDto;
import com.cimr.sysmanage.model.User;
import com.cimr.sysmanage.service.SysLogService;
import com.cimr.sysmanage.service.UserService;
import com.cimr.util.Assist;
import com.cimr.util.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by suhuanzhao 2018/1/3.
 */
@Controller
@RequestMapping("/sysLog")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

    @Autowired
    private UserService userService;
    @RequestMapping({"management","nav/sysLogManagement"})
    public ModelAndView management(HttpServletRequest request) {
        return new ModelAndView("sysmanage/sysLog");
    }

    /**
     * 方法描述: 查询日志列表
     *
     * @param page
     * @param limit
     * @return LayuiTableData
     * 作者:    admin
     * 创建时间: 2018年4月23日 下午5:37:04
     * 修改人:
     * 修改时间:
     * 修改内容:
     * 修改次数: 0
     */
    @RequestMapping({ "ajax/getList" })
    @ResponseBody
    public LayuiTableData list(@RequestParam(required = true, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "10") Integer limit,String accessTime) {

        Assist assist = new Assist();
        if(accessTime!=null&&!"".equals(accessTime)) {
            String bTime = accessTime.substring(0,19);
            String eTime = accessTime.substring(22);
            assist.setRequires(Assist.andGt("access_time",bTime));
            assist.setRequires(Assist.andLt("access_time", eTime));
        }
        PageData<AccessLogEntity> pagaData = sysLogService.selectObj_common(assist, page, limit);
        List<SysLogDto> sysLogDtoList =new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(AccessLogEntity accessLogEntityTemp:pagaData.getList()){
            User user = userService.selectObjById_common(accessLogEntityTemp.getUserId());
            SysLogDto sysLogDto = new SysLogDto(accessLogEntityTemp);
            sysLogDto.setAccessTimeStr(sdf.format(accessLogEntityTemp.getAccessTime()));
            if(user!=null) {
                sysLogDto.setUserName(user.getFullname());
            }
            sysLogDtoList.add(sysLogDto);
        }
        LayuiTableData result = new LayuiTableData();
        result.setCode(0);
        result.setMsg("");
        result.setCount(pagaData.getCount());
        result.setData(sysLogDtoList);
        return result;
    }
}
