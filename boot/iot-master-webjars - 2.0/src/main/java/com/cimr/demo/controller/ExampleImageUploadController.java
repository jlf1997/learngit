package com.cimr.demo.controller;

import com.cimr.comm.aop.FullPage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by suhuanzhao on 2017/11/23.
 * liqiwork@qq.com
 */
@Controller
@RequestMapping("/example")
public class ExampleImageUploadController {
    @FullPage(menu = "example_imgUpload")
    @RequestMapping({"imagePage","nav/imagePage"})
    public ModelAndView getList(HttpServletRequest request) {
//        FastDFSService.
        return new ModelAndView("/example/imageUploadIndex");
    }
}
