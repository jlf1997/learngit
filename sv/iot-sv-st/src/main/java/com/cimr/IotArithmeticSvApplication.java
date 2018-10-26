package com.cimr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.cimr.boot.convert.EnableHttpConvert;
import com.cimr.boot.swagger.EnableSwagger2Doc;

@SpringCloudApplication
@EnableSwagger2Doc
@EnableHttpConvert
@EnableScheduling
public class IotArithmeticSvApplication {

	public static void main(String[] args) {
		SpringApplication.run(IotArithmeticSvApplication.class, args);
//		Date faultEndDate = TimeUtil.getTheLastDayOfYear(new Date());
//		System.out.println(faultEndDate);
	}
}
