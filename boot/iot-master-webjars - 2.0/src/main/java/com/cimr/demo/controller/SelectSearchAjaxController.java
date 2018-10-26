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
 * @date 2018/1/3 12:50
 * @TODO todo
 */
@Controller
@RequestMapping("/selectSeacrch/ajax")
public class SelectSearchAjaxController {

    @ResponseBody
    @RequestMapping("/getSelectData")
    public HttpResult getSelectData(@RequestParam(value = "param",required = false) String key) {
        List<SelectDto<String, String>> selectDtoList = new ArrayList<>(3);
        selectDtoList.add(new SelectDto(key, key));
        selectDtoList.add(new SelectDto(key + key, key + key));
        selectDtoList.add(new SelectDto(key + key + key, key + key + key));
        return new HttpResult(true, selectDtoList);
    }
}
