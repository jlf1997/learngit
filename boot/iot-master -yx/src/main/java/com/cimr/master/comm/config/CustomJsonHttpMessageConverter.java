package com.cimr.master.comm.config;

import java.util.List;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

/**
 * Created by suhuanzhao on 2018/8/13.
 */
@Component
public class CustomJsonHttpMessageConverter extends WebMvcConfigurerAdapter{
	
	@Override
	  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
	    super.configureMessageConverters(converters);
	 
	    // 初始化转换器
	    FastJsonHttpMessageConverter fastConvert = new FastJsonHttpMessageConverter();
	    // 初始化一个转换器配置
	    FastJsonConfig fastJsonConfig = new FastJsonConfig();
	    fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
	    // 将配置设置给转换器并添加到HttpMessageConverter转换器列表中
	    fastConvert.setFastJsonConfig(fastJsonConfig);
	 
	    converters.add(fastConvert);
	  }




}
