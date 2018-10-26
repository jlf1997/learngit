package com.cimr.sysmanage.controller;

import com.cimr.comm.aop.FullPage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 文件管理
 * Created by suhuanzhao on 2017/11/23.
 * liqiwork@qq.com
 */
@Controller
@RequestMapping("/unitFile")
public class UnitFileController {
    @FullPage(menu = "unitFile_management")
    @RequestMapping({"page","nav/page"})
    public ModelAndView getList(HttpServletRequest request) {
//        FastDFSService.
        return new ModelAndView("sysmanage/list_file");
    }
}
