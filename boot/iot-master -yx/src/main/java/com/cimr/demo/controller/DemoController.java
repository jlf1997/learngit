package com.cimr.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cimr.comm.aop.FullPage;

/**
 * Created by liqi on 2017/12/26.
 * liqiwork@qq.com
 */
@Controller
@RequestMapping("/demo")
public class DemoController {

    @FullPage(menu = "demo_1")
    @RequestMapping({"nav/1"})
    public ModelAndView demo1 (HttpServletRequest request) {
        return new ModelAndView("demo/按钮");
    }

    @FullPage(menu = "demo_2")
    @RequestMapping({"nav/2"})
    public ModelAndView demo2 (HttpServletRequest request) {
        return new ModelAndView("demo/表单");
    }

    @FullPage(menu = "demo_3")
    @RequestMapping({"nav/3"})
    public ModelAndView demo3 (HttpServletRequest request) {
        return new ModelAndView("demo/导航／面包屑");
    }

    @FullPage(menu = "demo_4")
    @RequestMapping({"nav/4"})
    public ModelAndView demo4 (HttpServletRequest request) {
        return new ModelAndView("demo/动画");
    }

    @FullPage(menu = "demo_5")
    @RequestMapping({"nav/5"})
    public ModelAndView demo5 (HttpServletRequest request) {
        return new ModelAndView("demo/分页");
    }

    @FullPage(menu = "demo_6")
    @RequestMapping({"nav/6"})
    public ModelAndView demo6 (HttpServletRequest request) {
        return new ModelAndView("demo/辅助元素");
    }

    @FullPage(menu = "demo_7")
    @RequestMapping({"nav/7"})
    public ModelAndView demo7 (HttpServletRequest request) {
        return new ModelAndView("demo/复杂表头");
    }

    @FullPage(menu = "demo_8")
    @RequestMapping({"nav/8"})
    public ModelAndView demo8 (HttpServletRequest request) {
        return new ModelAndView("demo/赋值已知数据");
    }

    @FullPage(menu = "demo_9")
    @RequestMapping({"nav/9"})
    public ModelAndView demo9 (HttpServletRequest request) {
        return new ModelAndView("demo/高度最大化适应");
    }

    @FullPage(menu = "demo_10")
    @RequestMapping({"nav/10"})
    public ModelAndView demo10 (HttpServletRequest request) {
        return new ModelAndView("demo/工具集");
    }

    @FullPage(menu = "demo_11")
    @RequestMapping({"nav/11"})
    public ModelAndView demo11 (HttpServletRequest request) {
        return new ModelAndView("demo/固定列");
    }

    @FullPage(menu = "demo_12")
    @RequestMapping({"nav/12"})
    public ModelAndView demo12 (HttpServletRequest request) {
        return new ModelAndView("demo/后台布局");
    }

    @FullPage(menu = "demo_13")
    @RequestMapping({"nav/13"})
    public ModelAndView demo13 (HttpServletRequest request) {
        return new ModelAndView("demo/徽章");
    }

    @FullPage(menu = "demo_14")
    @RequestMapping({"nav/14"})
    public ModelAndView demo14 (HttpServletRequest request) {
        return new ModelAndView("demo/加入表单元素");
    }

    @FullPage(menu = "demo_15")
    @RequestMapping({"nav/15"})
    public ModelAndView demo15 (HttpServletRequest request) {
        return new ModelAndView("demo/监听单元格事件");
    }

    @FullPage(menu = "demo_16")
    @RequestMapping({"nav/16"})
    public ModelAndView demo16 (HttpServletRequest request) {
        return new ModelAndView("demo/简单数据表格");
    }

    @FullPage(menu = "demo_17")
    @RequestMapping({"nav/17"})
    public ModelAndView demo17 (HttpServletRequest request) {
        return new ModelAndView("demo/进度条");
    }

    @FullPage(menu = "demo_18")
    @RequestMapping({"nav/18"})
    public ModelAndView demo18 (HttpServletRequest request) {
        return new ModelAndView("demo/静态表格");
    }

    @FullPage(menu = "demo_19")
    @RequestMapping({"nav/19"})
    public ModelAndView demo19 (HttpServletRequest request) {
        return new ModelAndView("demo/开启单元格编辑");
    }

    @FullPage(menu = "demo_20")
    @RequestMapping({"nav/20"})
    public ModelAndView demo20 (HttpServletRequest request) {
        return new ModelAndView("demo/开启分页");
    }

    @FullPage(menu = "demo_21")
    @RequestMapping({"nav/21"})
    public ModelAndView demo21 (HttpServletRequest request) {
        return new ModelAndView("demo/开启复选框");
    }

    @FullPage(menu = "demo_22")
    @RequestMapping({"nav/22"})
    public ModelAndView demo22 (HttpServletRequest request) {
        return new ModelAndView("demo/列宽自动分配");
    }

    @FullPage(menu = "demo_23")
    @RequestMapping({"nav/23"})
    public ModelAndView demo23 (HttpServletRequest request) {
        return new ModelAndView("demo/流加载");
    }

    @FullPage(menu = "demo_24")
    @RequestMapping({"nav/24"})
    public ModelAndView demo24 (HttpServletRequest request) {
        return new ModelAndView("demo/轮播");
    }

    @FullPage(menu = "demo_25")
    @RequestMapping({"nav/25"})
    public ModelAndView demo25 (HttpServletRequest request) {
        return new ModelAndView("demo/面板");
    }

    @FullPage(menu = "demo_26")
    @RequestMapping({"nav/26"})
    public ModelAndView demo26 (HttpServletRequest request) {
        return new ModelAndView("demo/日期与时间选择");
    }

    @FullPage(menu = "demo_27")
    @RequestMapping({"nav/27"})
    public ModelAndView demo27 (HttpServletRequest request) {
        return new ModelAndView("demo/设置初始排序");
    }

    @FullPage(menu = "demo_28")
    @RequestMapping({"nav/28"})
    public ModelAndView demo28 (HttpServletRequest request) {
        return new ModelAndView("demo/设置单元格样式");
    }

    @FullPage(menu = "demo_29")
    @RequestMapping({"nav/29"})
    public ModelAndView demo29 (HttpServletRequest request) {
        return new ModelAndView("demo/时间线");
    }

    @FullPage(menu = "demo_30")
    @RequestMapping({"nav/30"})
    public ModelAndView demo30 (HttpServletRequest request) {
        return new ModelAndView("demo/数据表格的重载");
    }

    @FullPage(menu = "demo_31")
    @RequestMapping({"nav/31"})
    public ModelAndView demo31 (HttpServletRequest request) {
        return new ModelAndView("demo/数据操作");
    }

    @FullPage(menu = "demo_32")
    @RequestMapping({"nav/32"})
    public ModelAndView demo32 (HttpServletRequest request) {
        return new ModelAndView("demo/文件上传");
    }

    @FullPage(menu = "demo_33")
    @RequestMapping({"nav/33"})
    public ModelAndView demo33 (HttpServletRequest request) {
        return new ModelAndView("demo/选项卡");
    }

    @FullPage(menu = "demo_34")
    @RequestMapping({"nav/34"})
    public ModelAndView demo34 (HttpServletRequest request) {
        return new ModelAndView("demo/栅格");
    }

    @FullPage(menu = "demo_35")
    @RequestMapping({"nav/35"})
    public ModelAndView demo35 (HttpServletRequest request) {
        return new ModelAndView("demo/转化静态表格");
    }

    @FullPage(menu = "demo_36")
    @RequestMapping({"nav/36"})
    public ModelAndView demo36 (HttpServletRequest request) {
        return new ModelAndView("demo/自定义分页");
    }


    @FullPage(menu = "demo_37")
    @RequestMapping({"nav/37"})
    public ModelAndView demo37 (HttpServletRequest request) {
        return new ModelAndView("demo/饼状图");
    }

    @FullPage(menu = "demo_38")
    @RequestMapping({"nav/38"})
    public ModelAndView demo38 (HttpServletRequest request) {
        return new ModelAndView("demo/弹出层");
    }

    @FullPage(menu = "demo_39")
    @RequestMapping({"nav/39"})
    public ModelAndView demo39 (HttpServletRequest request) {
        return new ModelAndView("demo/树状图");
    }

    @FullPage(menu = "demo_40")
    @RequestMapping({"nav/40"})
    public ModelAndView demo40 (HttpServletRequest request) {
        return new ModelAndView("demo/无权限");
    }

    @FullPage(menu = "demo_41")
    @RequestMapping({"nav/41"})
    public ModelAndView demo41 (HttpServletRequest request) {
        return new ModelAndView("demo/综合信息服务管理平台");
    }

    @FullPage(menu = "demo_42")
    @RequestMapping({"nav/42"})
    public ModelAndView demo42 (HttpServletRequest request) {
        return new ModelAndView("demo/柱状图");
    }
}
