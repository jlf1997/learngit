package com.cimr.master.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cimr.sysmanage.bo.MenuBo;
import com.cimr.sysmanage.dto.HttpResult;
import com.cimr.sysmanage.dto.ZtreeDto;
import com.cimr.sysmanage.service.MenuService;

/***
 * @author pxh
 * @date 2018/1/3 10:27
 * @TODO todo
 */
@Controller
@RequestMapping("/ztree/ajax")
public class ZtreeAjaxController {

    @Resource
    private MenuService menuService;

    @RequestMapping("/getZtreeData")
    @ResponseBody
    public HttpResult getZtreeData() {
        List<MenuBo> menuBoList = menuService.getMenuList();
        List<ZtreeDto> ztreeDtoList = new ArrayList<>(menuBoList.size());

        ZtreeDto ztreeDto;
        for (MenuBo menuBo : menuBoList) {
            ztreeDto = new ZtreeDto();
            ztreeDto.setId(menuBo.getId());
            ztreeDto.setpId(menuBo.getParentId());
            ztreeDto.setName(menuBo.getMenuName());
//            ztreeDto.setUrl(menuBo.getHref());
            if (Integer.valueOf(1).equals(menuBo.getLevel())) {
                ztreeDto.setParent(true);
            } else {
                ztreeDto.setParent(false);
            }
            ztreeDtoList.add(ztreeDto);
        }
        return new HttpResult(true, ztreeDtoList);
    }
}
