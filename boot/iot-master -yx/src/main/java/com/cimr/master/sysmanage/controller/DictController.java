package com.cimr.master.sysmanage.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cimr.comm.aop.FullPage;
import com.cimr.sysmanage.service.DictService;

/**
 * @author lailichun
 */
@Controller
@RequestMapping("/dict")
public class DictController {

    @Autowired
    private DictService dictService;

    //@RequiresPermissions("dict:manage")
    @FullPage(menu = "dict_management")
    @RequestMapping({"management","nav/management"})
    public ModelAndView management(HttpServletRequest request) {
        return new ModelAndView("sysmanage/dict");
    }
    
}
