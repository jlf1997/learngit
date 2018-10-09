package com.cimr.demo.controller;


import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.cimr.demo.model.ExcelModel;
import com.cimr.demo.service.ExampleService;
import com.cimr.demo.service.ExeclExportService;
import com.xiaoleilu.hutool.poi.excel.ExcelReader;
import com.xiaoleilu.hutool.poi.excel.ExcelUtil;

@Controller
@RequestMapping("/excel/ajax")
public class ExcelAjaxController {
    @Autowired
    private ExampleService exampleService;
    @Autowired
    private ExeclExportService execlExportService;
    /**
    *@auther zengfan
    *@date 2017/12/25 15:21
    *@params String name 表格名字
    *@params List list 需要导出的数据以list格式传入
    *@params Class clasz 导出的数据类型，需要在class实体类加注解
     * https://gitee.com/jeecg/easypoi  使用的EasyPoi
    **/
    @RequestMapping({"execlExport"})
    //@ResponseBody
    public void execlExport(HttpServletResponse response)throws UnsupportedEncodingException{
        //HttpResult result = new HttpResult(true, "");
        List<ExcelModel> list = new ArrayList<ExcelModel>();
        ExcelModel em1 =new ExcelModel();
        em1.setOrder("1");
        em1.setName("准");
        em1.setJob("AD");
        ExcelModel em2 =new ExcelModel();
        em2.setOrder("2");
        em2.setName("钊");
        em2.setJob("SUP");
        ExcelModel em3 =new ExcelModel();
        em3.setOrder("3");
        em3.setName("辉");
        em3.setJob("AP");
        list.add(em1);
        list.add(em2);
        list.add(em3);
        execlExportService.execlExport(response, "表格", list, ExcelModel.class);
       // return result;
    }
    @RequestMapping({"excelIn"})
    @ResponseBody
    public List excelInput( @RequestParam("file")MultipartFile file){
        List<Map<String,Object>> readAll = null;
        try{
        CommonsMultipartFile cf= (CommonsMultipartFile)file;
        DiskFileItem fi = (DiskFileItem)cf.getFileItem();

        File f = fi.getStoreLocation();
        ExcelReader reader = ExcelUtil.getReader(f);
        readAll = reader.readAll();

        }catch (Exception e){
            e.printStackTrace();
        }
        return readAll;
    }
}
