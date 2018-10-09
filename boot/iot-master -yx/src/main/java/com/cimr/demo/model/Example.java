package com.cimr.demo.model;


import cn.afterturn.easypoi.excel.annotation.Excel;
import com.cimr.comm.base.BaseEntity;

/**
 * Created by liqi on 2017/11/24.
 * liqiwork@qq.com
 */
public class Example extends BaseEntity {
    @Excel(name = "排序", height = 20, width = 30, isImportField = "true_st")
    private Long order;
    @Excel(name = "标题", height = 20, width = 30, isImportField = "true_st")
    private String title;
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }
}
