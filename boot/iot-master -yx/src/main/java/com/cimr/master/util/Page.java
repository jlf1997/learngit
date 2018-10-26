package com.cimr.master.util;

import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * Created by admin on 2018/6/26.
 */
public class Page {
    private Integer currentPage;//当前页
    private int pageSize;//每页显示记录条数
    private int totalPage;//总页数
    private List<?> dataList;//每页显示的数据
    private int star;//开始数据
    private int count;//总条数

    public Integer getCurrentPage() {
        return currentPage;
    }
    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public int getTotalPage() {
        return totalPage;
    }
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public List<?> getDataList() {
        return dataList;
    }
    public void setDataList(List<?> dataList) {
        this.dataList = dataList;
    }
    public int getStar() {
        return star;
    }
    public void setStar(int star) {
        this.star = star;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Page(){}
    public Page(List<?> list,int currentPage,int pageSize){
        //刚开始的页面为第一页
        this.setCurrentPage(currentPage);
        //设置每页数据为十条
        this.setPageSize(pageSize);
        //每页的开始数
        this.setStar((this.getCurrentPage() - 1) * this.getPageSize());
        //list的大小
        int count = list.size();
        this.setCount(count);
        //设置总页数
        this.setTotalPage(count % 10 == 0 ? count / 10 : count / 10 + 1);
        //对list进行截取
        this.setDataList(list.subList(this.getStar(),count-this.getStar()>this.getPageSize()?this.getStar()+this.getPageSize():count));
    }
}
