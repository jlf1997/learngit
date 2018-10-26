package com.cimr.sysmanage.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.cimr.comm.enums.SysEnums;
import com.cimr.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cimr.comm.config.AppFileProperties;
import com.cimr.sysmanage.dto.HttpResult;
import com.cimr.sysmanage.dto.UeditorDto;
import com.cimr.sysmanage.service.UnitFileService;
import com.xlybase.file.service.FastDFSService;


/**
 * 文件上传（缓存到本地）
 */
@Controller
@RequestMapping(value = "/files")
public class UnitFileUploadAjaxController {

    private static final Logger logger = LoggerFactory.getLogger(UnitFileUploadAjaxController.class);


    @Autowired
    private UnitFileService unitFileService;

    /**
     * 文件上传
     *
     * @param files
     * @param session
     * @return com.cimr.comm.dto.HttpResult
     * @author:suhuanzhao
     * @date: 2017/12/25 15:43
     **/
    @RequestMapping(value = "/upload")
    @ResponseBody
    public HttpResult fileUpload2(@RequestParam("file") MultipartFile[] files, HttpSession session, HttpServletRequest request) {
        HttpResult res = new HttpResult(false, "上传失败");
        String realPath = session.getServletContext().getRealPath("/");
        System.out.println("realPath:" + realPath);
        List fileDtos = new ArrayList();
        try {
            System.out.println("sss");
            //上传到服务器本地缓存
            fileDtos = unitFileService.saveFileTemp(files, realPath);
        } catch (Exception e) {
            LogUtil.printStackTraceToLog(request,e, logger, SysEnums.HTTP_500,null);
        }
        res.setSuccess(true);
        res.setData(fileDtos);
        res.setError("上传成功");
        return res;
    }


    /**
     * 富文本编辑器图片上传
     *
     * @param file
     * @return
     */
    @ResponseBody
    @RequestMapping("ueditorUpload")
    public UeditorDto ueditorUpload(@RequestParam("upfile") MultipartFile file,HttpServletRequest request) {

        UeditorDto ueditorDto = new UeditorDto();
        String fname = file.getOriginalFilename();
        ueditorDto.setOriginalName(fname);
        String ext;
        if (fname.lastIndexOf(".") > 0) {
            ext = fname.substring(fname.lastIndexOf(".") + 1, fname.length());
        } else {
            ext = "";
        }
        ueditorDto.setType(ext);
        try {
            String path = FastDFSService.uploadFile(file.getInputStream(), ext);
            if (path == null) {
                ueditorDto.setState("ERROR");
            } else {
                ueditorDto.setFileName(path);
                ueditorDto.setUrl(AppFileProperties.getFdfsServer() + "/" + path);
                ueditorDto.setState("SUCCESS");
            }
        } catch (IOException e) {
            ueditorDto.setState("文件上传失败");
            LogUtil.printStackTraceToLog(request,e, logger, SysEnums.HTTP_500,null);
        }
        return ueditorDto;
    }

}
