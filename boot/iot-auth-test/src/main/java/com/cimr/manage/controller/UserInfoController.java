package com.cimr.manage.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cimr.comm.aop.FullPage;

/**
 * Created by suhuanzhao 2018/1/3.
 */
@Controller
@RequestMapping("/userInfo")
public class UserInfoController {
    @FullPage(menu = "userInfo_management")
    @RequestMapping({"management","nav/management"})
    public ModelAndView management(HttpServletRequest request) {
        return new ModelAndView("manager/userInfo");
    }
}
