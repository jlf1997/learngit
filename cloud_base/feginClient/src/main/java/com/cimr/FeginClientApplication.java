package com.cimr;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringCloudApplication
@EnableFeignClients
public class FeginClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeginClientApplication.class, args);
	}
}
