package com.cimr.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cimr.sysmanage.dto.HttpResult;
import com.cimr.sysmanage.dto.SelectDto;

/***
 * @author pxh
 * @date 2017/12/20 11:50
 * @TODO todo
 */
@RequestMapping("/exampleSelectSearch")
@Controller
public class ExampleSelectSearchAjaxController {

    private static final List<SelectDto<Integer, Integer>> SELECTDOTLIST = new ArrayList();

    @RequestMapping("/demo")
    @ResponseBody
    public HttpResult demo(@RequestParam("param") Integer param) {
        List selectDotList = new ArrayList();
        for (SelectDto<Integer, Integer> selectDto : SELECTDOTLIST) {
            if (selectDto.getKey().equals(param)) {
                selectDotList.add(selectDto);
            }
        }
        HttpResult httpResult = new HttpResult(true, selectDotList);
        return httpResult;
    }


    static {
        SELECTDOTLIST.add(new SelectDto<Integer, Integer>(1, 1));
        SELECTDOTLIST.add(new SelectDto<Integer, Integer>(2, 2));
        SELECTDOTLIST.add(new SelectDto<Integer, Integer>(3, 3));
        SELECTDOTLIST.add(new SelectDto<Integer, Integer>(4, 4));
        SELECTDOTLIST.add(new SelectDto<Integer, Integer>(5, 5));
        SELECTDOTLIST.add(new SelectDto<Integer, Integer>(6, 6));
    }
}
