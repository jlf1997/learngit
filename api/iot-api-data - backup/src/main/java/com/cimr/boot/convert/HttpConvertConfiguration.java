package com.cimr.boot.convert;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;

@Configuration
public class HttpConvertConfiguration {
	
	@Bean
    @ConditionalOnMissingBean
    public HttpConvertProperties httpConvertProperties() {
        return new HttpConvertProperties();
    }
	
	protected List<HttpMessageConverter<?>> additionalConverters;
	
	
	@SuppressWarnings("rawtypes")
	public void addConverters(HttpConvertProperties gsonProperties)  {

		additionalConverters = new ArrayList<>();
		String path = gsonProperties.getPath();
		if(path!=null) {
			String[] paths = path.split(",");
			for(String str :paths) {
				try {
					HttpMessageConverter convert =  ((CustomConvert) Class.forName(str).newInstance()).setHttpMessageConverter();
					additionalConverters.add(convert);
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
//		additionalConverters.add(gsonConfig.create());
	}
		

	
	@Bean
    public HttpMessageConverters customConverters(HttpConvertProperties gsonProperties) {		
		addConverters(gsonProperties);
        return new HttpMessageConverters(additionalConverters);
    }
	
	
}
