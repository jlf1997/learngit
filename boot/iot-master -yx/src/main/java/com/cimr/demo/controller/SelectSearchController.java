package com.cimr.demo.controller;

import com.cimr.comm.aop.FullPage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/***
 * @author pxh
 * @date 2018/1/3 12:42
 * @TODO todo
 */
@Controller
@RequestMapping("/selectSearch")
public class SelectSearchController {

    @FullPage(menu = "selectSearch_searchIndex")
    @RequestMapping({"/searchIndex","/nav/searchIndex"})
    public ModelAndView searchIndex(HttpServletRequest request){
        ModelAndView modelAndView =new ModelAndView();
        modelAndView.setViewName("example/searchIndex");
        return modelAndView;
    }
}
