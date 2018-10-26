package com.cimr.comm.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;

@Configuration
public class AppConfig {

//	@Bean
//	@ConditionalOnMissingBean
//	public GlobalExceptionResolver getGlobalExceptionResolver() {
//		GlobalExceptionResolver globalExceptionResolver =  new GlobalExceptionResolver();
//		Properties mappings = new Properties();
//		mappings.put(new UnauthorizedException(), "comm/error/unauthorized");
//		mappings.put(new InvalidSessionException(), "comm/error/invalidSession");
//		mappings.put(new NotLoginException(""), "comm/exception/login");
//		globalExceptionResolver.setExceptionMappings(mappings);
//		globalExceptionResolver.setDefaultErrorView("/comm/error/error");
//		globalExceptionResolver.setExceptionAttribute("ex");
//		return globalExceptionResolver;
//	}
	
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.druid")
    public DataSource primaryDatasource() {
		
		return DruidDataSourceBuilder.create().build();
    }
	
//	@Bean
//	@ConditionalOnMissingBean
//	public MappingJackson2HttpMessageConverter mappingJacksonHttpMessageConverter() {
//		MappingJackson2HttpMessageConverter mappingJacksonHttpMessageConverter = new mappingJacksonHttpMessageConverter();
//		
//		return mappingJacksonHttpMessageConverter;
//	}
//	
//	@Bean
//	@ConditionalOnMissingBean
//	public AnnotationMethodHandlerAdapter getAnnotationMethodHandlerAdapter() {
//		AnnotationMethodHandlerAdapter annotationMethodHandlerAdapter = new AnnotationMethodHandlerAdapter();
//		annotationMethodHandlerAdapter.set
//		return annotationMethodHandlerAdapter;
//	}
}
