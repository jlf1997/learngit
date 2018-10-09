package com.cimr.demo.controller;

import com.cimr.comm.aop.FullPage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by admin on 2017/12/25.
 */
@Controller
@RequestMapping("/excel")
public class ExcelController {
    @FullPage(menu = "excel_excelTest")
    @RequestMapping({"excelTest","nav/excelTest"})
    public ModelAndView excelTest(HttpServletRequest request) {
        return new ModelAndView("/example/excelTest");
    }

    @FullPage(menu = "excel_excelOut")
    @RequestMapping({"excelOut","nav/excelOut"})
    public ModelAndView excelOut(HttpServletRequest request) {
        return new ModelAndView("/example/excelOut");
    }

}

