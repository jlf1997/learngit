package com.cimr.comm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.cimr.comm.config.AppFileProperties;



@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {
	
	
	@Autowired
	private AppFileProperties appFileProperties;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	registry.addResourceHandler("/static/**").addResourceLocations("classpath:META-INF/resources/webjars/master/static/");
    	registry.addResourceHandler("/img/**").addResourceLocations("file:"+appFileProperties.getBasePath());
        registry.addResourceHandler("/resource/**").addResourceLocations("file:"+appFileProperties.getBasePath());
        /*String classpath = this.getClass().getResource("/").getPath().replaceFirst("/", "");
        String webappRoot = classpath.replaceAll("target/classes/", "");
        String filePath=webappRoot+"src/main/webapp/fileTemp/";
        System.out.println(filePath);
        registry.addResourceHandler("/img/**").addResourceLocations("file:///"+filePath);*/
        super.addResourceHandlers(registry);
    }
}
