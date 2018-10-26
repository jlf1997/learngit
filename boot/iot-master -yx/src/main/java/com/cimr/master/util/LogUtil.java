package com.cimr.master.util;

import com.cimr.comm.enums.SysEnums;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

/**
 * 日志工具类
 */
public class LogUtil {

    /**
     * 输出异常日志信息
     * @param request
     * @param t 异常类
     * @param logger logger对象,必须要传
     * @param sysEnums 异常代码枚举
     * @param extralParams  额外参数
     */
    public static void printStackTraceToLog(HttpServletRequest request, Throwable t, Logger logger, SysEnums sysEnums, Map<String,Object> extralParams) {
        String url = "";
        String detailMsg = "";
        if(request == null && extralParams !=null && extralParams.containsKey("url")){
            url = (String) extralParams.get("url");
        }else if(request != null){
            url = request.getRequestURL().toString();
        }else {

        }
        StringWriter sw = new StringWriter();
        if(t != null){
            //重定向输出流
            t.printStackTrace(new PrintWriter(sw, true));
            detailMsg = sw.getBuffer().toString();
        }else if(t==null && extralParams !=null && extralParams.containsKey("msg")){
            detailMsg = (String) extralParams.get("msg");
        }
        //日志输出
        logger.error("访问链接:{},响应状态码:{},代码含义:{},详细信息:\r\n{}",url,sysEnums.getCode(),sysEnums.getDesc(),detailMsg);
    }

}
