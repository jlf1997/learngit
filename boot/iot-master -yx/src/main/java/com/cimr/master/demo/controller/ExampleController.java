package com.cimr.master.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cimr.comm.aop.FullPage;

/**
 * Created by liqi on 2017/11/23.
 * liqiwork@qq.com
 */
@Controller
@RequestMapping("/example")
public class ExampleController {
    @FullPage(menu = "example_getList_layui")
    @RequestMapping({"getList_layui","nav/getList_layui"})
    public ModelAndView getList(HttpServletRequest request) {
        return new ModelAndView("/example/list_layui");
    }

}
