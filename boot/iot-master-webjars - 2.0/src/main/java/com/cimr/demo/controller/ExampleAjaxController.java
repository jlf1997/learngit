package com.cimr.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.cimr.demo.model.Example;
import com.cimr.demo.service.ExampleService;
import com.cimr.sysmanage.dto.HttpResult;
import com.cimr.sysmanage.dto.LayuiTableData;

/**
 * Created by liqi on 2017/11/23.
 * liqiwork@qq.com
 */
@Controller
@RequestMapping("/example/ajax")
public class ExampleAjaxController {
    @RequestMapping({"getList_layui"})
    @ResponseBody
    public LayuiTableData getList_layui(@RequestParam(required=true,defaultValue="1") Integer page,
                                        @RequestParam(required=false,defaultValue="10") Integer pageSize,
                                        @RequestParam(required = false) String title) {
        PageHelper.startPage(page, pageSize);

//        Example inputParam = new Example();
//        inputParam.setTitle(title);
        List<Example> list = exampleService.selectList(title);
        PageInfo<Example> pageInfo = new PageInfo(list);
        LayuiTableData result = new LayuiTableData();
        result.setCode(0);
        result.setMsg("");
        result.setCount((int)pageInfo.getTotal());
        result.setData(pageInfo.getList());
        return result;
    }

    @RequestMapping({"saveExample"})
    @ResponseBody
    public HttpResult saveExample(@RequestParam(required = false) String title) {
        Example example = new Example();
//        exampleService.insertObject_common(example);

        HttpResult result = new HttpResult(true, "");
        return result;
    }

    @Autowired
    private ExampleService exampleService;
}
