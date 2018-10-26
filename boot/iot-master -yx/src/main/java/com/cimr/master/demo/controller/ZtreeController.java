package com.cimr.master.demo.controller;

import com.cimr.comm.aop.FullPage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/***
 * @author pxh
 * @date 2018/1/3 10:23
 * @TODO todo
 */
@Controller
@RequestMapping("/ztree")
public class ZtreeController {

    @FullPage(menu = "ztree_ztreeIndex")
    @RequestMapping({"/ztreeIndex", "/nav/ztreeIndex"})
    public ModelAndView ztreeIndex(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("example/ztreeIndex");
        return modelAndView;
    }
}
