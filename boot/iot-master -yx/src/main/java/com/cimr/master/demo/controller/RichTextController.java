package com.cimr.master.demo.controller;

import com.cimr.comm.aop.FullPage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/***
 * @author pxh
 * @date 2018/1/3 13:39
 * @TODO todo
 */
@Controller
@RequestMapping("/richtext")
public class RichTextController {

    @FullPage(menu = "richtext_ueditorIndex")
    @RequestMapping({"/ueditorIndex", "/nav/ueditorIndex"})
    public ModelAndView ueditorIndex(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("example/ueditorIndex");
        return modelAndView;
    }
}
