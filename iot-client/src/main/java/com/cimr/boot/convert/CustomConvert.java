package com.cimr.boot.convert;

import org.springframework.http.converter.HttpMessageConverter;


public interface CustomConvert {

	public HttpMessageConverter<?> setHttpMessageConverter();
}
