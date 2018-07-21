package com.cimr.boot.redis.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;

public class MyBeanUtils extends BeanUtils{
	private MyBeanUtils(){}

	public static void populate(final Object bean, final Map<String, ? extends Object> properties){

        try {
        	regist();
            BeanUtils.populate(bean, properties);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
	
	 public static Map<String, String> describe(final Object bean)
	            throws IllegalAccessException, InvocationTargetException,
	            NoSuchMethodException {
				 //处理时间格式
		 		regist();
		         //封装数据
	        return BeanUtils.describe(bean);
	    }
	 
	 private static void regist() {
		 DateConverter dateConverter = new DateConverter();
         //设置日期格式
         dateConverter.setPatterns(new String[]{"yyyy-MM-dd","yyyy-MM-dd HH:mm:ss"});
         //注册格式
         dateConverter.setUseLocaleFormat(true);
         ConvertUtils.register(dateConverter, Date.class);
	 }
	
}
