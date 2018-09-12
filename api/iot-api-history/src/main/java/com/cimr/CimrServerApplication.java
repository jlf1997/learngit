package com.cimr;

import java.io.UnsupportedEncodingException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.cimr.boot.convert.EnableHttpConvert;
import com.cimr.boot.swagger.EnableSwagger2Doc;



@SpringBootApplication
@EnableSwagger2Doc
@EnableHttpConvert
@EnableScheduling

public class CimrServerApplication {
	
     

	public static void main(String[] args) throws UnsupportedEncodingException {
		SpringApplication.run(CimrServerApplication.class, args);

	}
	
	
	
	
}
