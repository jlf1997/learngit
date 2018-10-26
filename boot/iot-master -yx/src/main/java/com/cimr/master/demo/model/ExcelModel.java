package com.cimr.master.demo.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;

/**
 * Created by admin on 2018/1/19.
 */
@ExcelTarget("courseEntity")
public class ExcelModel {
    @Excel(name = "序号", orderNum = "1")
    private String order;
    @Excel(name = "姓名")
    private String name;
    @Excel(name = "职务")
    private String job;

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
