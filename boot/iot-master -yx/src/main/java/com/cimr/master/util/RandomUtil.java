package com.cimr.master.util;

import org.apache.commons.lang.RandomStringUtils;

public class RandomUtil
{
  public static String random(int len) {
    String str = "0123456789";
    return RandomStringUtils.random(len, str);
  }
  
  public static String random() {
    String str = "0123456789";
    return random(4);
  }
}
