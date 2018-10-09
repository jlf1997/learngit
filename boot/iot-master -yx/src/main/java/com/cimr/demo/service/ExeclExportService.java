package com.cimr.demo.service;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by admin on 2018/1/19.
 */
public interface ExeclExportService{
    public void execlExport(HttpServletResponse response, String name, List list, Class clasz)throws UnsupportedEncodingException;
}
