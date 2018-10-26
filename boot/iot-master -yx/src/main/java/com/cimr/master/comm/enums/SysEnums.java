package com.cimr.master.comm.enums;

/**
 * 系统基础代码类
 * Created by suhuanzhao on 2018/8/9.
 */
public enum SysEnums {
    /**
     * 0,"分页列表请求正常"
     */
    NORMAL(0,"分页列表请求正常"),
    /**
     * 200,"正常访问"
     */
    HTTP_200(200,"正常访问"),
    /**
     * 400,"链接参数有误"
     */
    HTTP_400(404,"链接参数有误"),
    /**
     * 404,"请求页面不存在"
     */
    HTTP_404(404,"请求页面不存在"),
    /**
     * 500,"系统出现异常"
     */
    HTTP_500(500,"系统出现异常"),
    /**
     * 100,"没有权限"
     */
    ERROR_NO_PERMISSION(100,"没有权限"),
    /**
     * 101,"表单校验失败"
     */
    ERROR_FORM_VALIDATE(101,"表单校验失败"),
    /**
     * 1500,"API连接返回异常"
     */
    API_HTTP_500(1500,"API连接返回异常"),
    /**
     * 1100,"API连接正常,但是返回false"
     */
    API_ERROR(1100,"API连接正常,但是返回false"),
    /**
     * 1200,"API连接失败"
     */
    API_LINK_FAIL(1005,"API连接失败"),
    ;
    /**
     * 中文描述
     */
    private String desc;
    /**
     * 故障码
     */
    private Integer code;
    private SysEnums(Integer code, String desc){
        this.code = code;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getCode() {
        return code;
    }

}
