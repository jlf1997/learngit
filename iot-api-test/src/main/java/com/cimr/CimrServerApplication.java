package com.cimr;

import java.io.UnsupportedEncodingException;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.cimr.boot.convert.EnableHttpConvert;
import com.cimr.boot.swagger.EnableSwagger2Doc;
import com.spring4all.mongodb.EnableMongoPlus;



@SpringCloudApplication
@EnableFeignClients
@EnableSwagger2Doc
@EnableHttpConvert
@EnableScheduling
@EnableJpaAuditing
@EnableMongoPlus
//@EnableFeignClients
public class CimrServerApplication {
	


	public static void main(String[] args) throws UnsupportedEncodingException {
		SpringApplication.run(CimrServerApplication.class, args);

	}
	
	
	
}
