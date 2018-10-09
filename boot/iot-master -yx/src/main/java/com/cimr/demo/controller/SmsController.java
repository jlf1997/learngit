package com.cimr.demo.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cimr.comm.aop.FullPage;
import com.cimr.demo.service.SmsCodeService;
import com.cimr.sysmanage.dto.HttpResult;

/***
 * @author pxh
 * @date 2018/1/19 10:43
 * @TODO todo
 */
@Controller
@RequestMapping("/sms")
public class SmsController {

    @Resource
    private SmsCodeService smsCodeService;

    @FullPage(menu = "sms_sendSmsIndex")
    @RequestMapping({"/sendSmsIndex", "/nav/sendSmsIndex"})
    public ModelAndView sendSmsIndex(HttpServletRequest request) {
        ModelAndView view = new ModelAndView();
        view.setViewName("example/sendSmsIndex");
        return view;
    }


    @RequestMapping("/sendSms")
    @ResponseBody
    public HttpResult sendSms(String phone) throws IOException {
        String req = smsCodeService.sendSms(phone, "测试短信");
        if (req == null) {
            return new HttpResult(false, "短信发送失败");
        } else {
            return new HttpResult(true, "短信发送成功");
        }
    }
}
