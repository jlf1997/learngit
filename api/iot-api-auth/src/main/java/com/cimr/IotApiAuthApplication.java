package com.cimr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.cimr.boot.convert.EnableHttpConvert;
import com.cimr.boot.swagger.EnableSwagger2Doc;

@SpringBootApplication
@EnableSwagger2Doc
@EnableHttpConvert
@EnableJpaAuditing
public class IotApiAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(IotApiAuthApplication.class, args);
	}
}
