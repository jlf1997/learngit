package com.cimr.sysmanage.controller;

import com.cimr.comm.token.TokenUtil;
import com.cimr.sysmanage.dto.HttpResult;
import com.cimr.sysmanage.dto.LayuiTableData;
import com.cimr.sysmanage.dto.UnitFileDto;
import com.cimr.sysmanage.model.UnitFile;
import com.cimr.sysmanage.service.UnitFileService;
import com.cimr.util.Assist;
import com.cimr.util.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件管理Ajax
 * Created by liqi on 2017/11/23.
 * liqiwork@qq.com
 */
@Controller
@RequestMapping("/unitFile/ajax")
public class UnitFileAjaxController {

    @Autowired
    private UnitFileService unitFileService;

    /**
     * 文件列表查询
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping({"getList"})
    @ResponseBody
    public LayuiTableData getList_layui(@RequestParam(required=true,defaultValue="1") Integer page,
                                        @RequestParam(required=false,defaultValue="10") Integer limit,
                                        @RequestParam(required = false) String title) {
        Assist assist = new Assist();
        assist.setRequires(Assist.andLike("title", title));
        PageData<UnitFile> pageData = unitFileService.selectObj_common(assist, page, limit);

        LayuiTableData result = new LayuiTableData();
        result.setCode(0);
        result.setMsg("");
        result.setCount(pageData.getCount());
        List<UnitFileDto> unitFileDtoList = new ArrayList<>();
        UnitFileDto unitFileDto = null;
        for (UnitFile unitFile1: pageData.getList()) {
            unitFileDto = new UnitFileDto(unitFile1);
            unitFileDtoList.add(unitFileDto);
        }
        result.setData(unitFileDtoList);
        return result;
    }

    /**
     * 文件保存（测试）
     * @param fileJson FileDTO的json字符串
     * @param session
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public HttpResult saveFile( @RequestParam(required = false) String fileJson, HttpSession session) {
        HttpResult result = new HttpResult(true, "添加成功");
        //获取真实路径
        String realPath = session.getServletContext().getRealPath("/");

        //上传文件到文件服务器
        unitFileService.uploadFiles(fileJson,realPath,null,null,null, TokenUtil.getUserId());
        return result;
    }

    /**
     * 文件删除
     * @param sysFileIds 需要删除的id，多个之间用逗号隔开
     * @param session
     * @return
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public HttpResult deleteFile( @RequestParam(required = false) String sysFileIds, HttpSession session) {
        HttpResult result = new HttpResult(true, "删除成功");
        String []ids = sysFileIds.split(",");
        boolean b = unitFileService.deleteFiles(ids, "");
        result.setSuccess(b);
        if(!b){
            result.setError("删除失败");
        }
        return result;
    }

}


