package com.cimr.sysmanage.controller;

import com.cimr.comm.aop.FullPage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by suhuanzhao 2018/1/3.
 */
@Controller
@RequestMapping("/area")
public class AreaController {
    @FullPage(menu = "area_management")
    @RequestMapping({"management","nav/management"})
    public ModelAndView management(HttpServletRequest request) {
        return new ModelAndView("sysmanage/area");
    }
}
