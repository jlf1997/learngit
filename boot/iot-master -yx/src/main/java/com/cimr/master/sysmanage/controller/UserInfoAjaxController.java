package com.cimr.master.sysmanage.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cimr.sysmanage.dto.HttpResult;
import com.cimr.sysmanage.dto.LayuiTableData;
import com.cimr.sysmanage.dto.UserInfoDto;
import com.cimr.sysmanage.model.User;
import com.cimr.sysmanage.model.UserInfo;
import com.cimr.sysmanage.service.UnitFileService;
import com.cimr.sysmanage.service.UserInfoService;
import com.cimr.sysmanage.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * Created by suhuanzhao on 2018/1/3.
 */
@Controller
@RequestMapping("/userInfo/ajax")
public class UserInfoAjaxController {

    private static final Logger logger = LoggerFactory.getLogger(AreaAjaxController.class);

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UnitFileService unitFileService;

    @Autowired
    private UserService userService;
    /**
     * 列表查询
     * @param page
     * @param limit
     * @param fullname
     * @return
     */
    @RequestMapping({"getList"})
    @ResponseBody
    public LayuiTableData list(@RequestParam(required=true,defaultValue="1") Integer page,
                               @RequestParam(required=false,defaultValue="10") Integer limit,
                               @RequestParam(required = false) String username,String fullname) {
        PageHelper.startPage(page, limit);
        UserInfo usr = new UserInfo();
        usr.setUsername(username);
        usr.setFullname(fullname);
        List<UserInfo> list = null;
//        List<UserInfo> list = userInfoService.getList(usr);

        PageInfo<UserInfo> pageInfo = new PageInfo(list);
        List<UserInfoDto> userInfoDtoList = new ArrayList<>();
        UserInfoDto userInfoDto = null;
        for (UserInfo ui : pageInfo.getList()){
            userInfoDto = new UserInfoDto(ui);
            userInfoDtoList.add(userInfoDto);
        }
        LayuiTableData result = new LayuiTableData();
        result.setCode(0);
        result.setMsg("");
        result.setCount((int)pageInfo.getTotal());
        result.setData(userInfoDtoList);
        return result;
    }

    /**
     * 保存和更新
     * @param userInfoDto
     * @param session
     * @return
     */
    @RequestMapping({"save"})
    @ResponseBody
    public HttpResult save(UserInfoDto userInfoDto, HttpSession session) {
        String realPath = session.getServletContext().getRealPath("/");
        userInfoDto.setRealPath(realPath);
        String msg = "";
        HttpResult res = new HttpResult(true,"添加成功");
        if(userInfoDto.getId() == null || "".equals(userInfoDto.getId())){
            //添加操作
            msg = userInfoService.insertByObj(userInfoDto);
        }else{
            //更新操作
            msg = userInfoService.updateByObj(userInfoDto);

        }
        res.setError(msg);
        return res;
    }

    //删除
    @RequestMapping({"delete"})
    @ResponseBody
    public HttpResult delete(String userIds) {
        HttpResult res = new HttpResult(true,"删除成功");
        userInfoService.delete(userIds);
        return res;
    }


    //查询单个userInfo数据
    @RequestMapping({"getUserInfo"})
    @ResponseBody
    public HttpResult edit(@RequestParam(required = true) String  userId) {
        HttpResult res = new HttpResult(true,"获取成功");
        Map  map = new HashMap();
        //获取该用户的用户信息数据（包括user数据）
        UserInfo userInfo = userInfoService.getByUserId(userId);
        map.put("userInfo",userInfo);
        //返回该用户的头像
        User u = userService.selectObjById_common(userId);
        map.put("unitFile",unitFileService.selectObjById_common(u.getAvatar()));
        res.setData(map);
        return res;
    }

}
