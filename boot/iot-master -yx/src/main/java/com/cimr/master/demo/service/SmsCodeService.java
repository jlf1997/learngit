package com.cimr.master.demo.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/***
 * @author pxh
 * @date 2018/1/19 11:18
 * @TODO todo
 */
public interface SmsCodeService {
    String sendSms(String phone, String content) throws IOException;
}
