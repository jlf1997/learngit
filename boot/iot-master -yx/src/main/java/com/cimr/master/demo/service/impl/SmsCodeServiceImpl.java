package com.cimr.master.demo.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cimr.demo.service.SmsCodeService;
import com.cimr.util.SmsUtil;
import com.xlybase.file.fastdfs.common.ConfigUtil;

/**
 * @author wyq
 * 短信发送辅助工具类
 * 创建时间：2017年6月2日
 */
@Service
public class SmsCodeServiceImpl implements SmsCodeService {
	
	@Value("${sms.sendSms.url: }")
	private String url ;
	@Value("${sms.sendSms.account: }")
	private String account;
	@Value("${sms.sendSms.password: }")
	private String password;

    @Override
    public String sendSms(String phone, String smsContext) {
//        String url = ConfigUtil.getStringValue("sms.sendSms.Url");
//        String account = ConfigUtil.getStringValue("sms.sendSms.account");
//        String password = ConfigUtil.getStringValue("sms.sendSms.password");
        String extno = "";
        StringBuilder param = new StringBuilder();
        param.append("&account=" + account);
        param.append("&pswd=" + password);
        param.append("&mobile=" + phone);
        try {
            param.append("&msg=" + URLEncoder.encode(smsContext, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
        param.append("&needstatus=" + false);
        param.append("&extno=" + extno);

        try {
            return SmsUtil.sendSms(url, param.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     *
     * bachSendSms
     * 根据报名列表批量发送短信
     * @param yscEnrollList 报名人列表
     * @param smsContext 短信内容
     * @param type 短信类型短信验证码类型 1 手机号注册验证码 2 手机号找回密码验证 3 手机号绑定验证码 4 手机短信提醒 5 申请成为发布者短信验证码 6
     * 后台管理员登陆验证码 6 项目相关； 7 模块相关；8 表单相关 9:询价消息  10:报价消息
     * return
     *     void
     * 2017年6月2日 下午8:34:41
     */
    /*public void bachYscEnrollSendSms(List<YscEnroll> yscEnrollList, String smsContext) {
        if (null == smsContext || "".equals(smsContext.trim())) {
            return;
        }
        for (YscEnroll yscEnroll : yscEnrollList) {
            String phone = yscEnroll.getPhone();
            sendSms(phone,smsContext);
        }
    }*/

}