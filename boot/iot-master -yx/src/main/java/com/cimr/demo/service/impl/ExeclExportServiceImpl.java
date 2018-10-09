package com.cimr.demo.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import com.cimr.demo.service.ExeclExportService;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;

/**
 * Created by admin on 2018/1/19.
 */
@Service
public class ExeclExportServiceImpl implements ExeclExportService {
    public  void execlExport(HttpServletResponse response,String name,List list,Class clasz)throws UnsupportedEncodingException{
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(name,""),
                clasz, list);
        String fileName = name+".xls";
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("gbk"), "ISO-8859-1"));
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.flush();
                out.close();
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
