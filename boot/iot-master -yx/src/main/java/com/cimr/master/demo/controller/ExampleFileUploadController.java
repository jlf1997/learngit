package com.cimr.master.demo.controller;

import com.cimr.comm.aop.FullPage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/***
 * @author pxh
 * @date 2018/1/2 15:43
 * @TODO 文件上传示例
 */
@Controller
@RequestMapping("/fileUpload")
public class ExampleFileUploadController {


    /***
     * 跳转到文件上传页面
     * @return
     */
    @FullPage(menu = "fileUpload_newindex")
    @RequestMapping({"/newindex","/nav/newindex"})
    public ModelAndView fileUploadPage(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("example/fileUploadIndex");
        return modelAndView;
    }


    @FullPage(menu = "fileUpload_multiFilesIndex")
    @RequestMapping({"/multiFilesIndex","/nav/multiFilesIndex"})
    public ModelAndView multiFilesIndex(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("example/multiFileUploadIndex");
        return modelAndView;
    }
}
