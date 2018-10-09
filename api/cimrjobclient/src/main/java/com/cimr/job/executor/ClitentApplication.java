package com.cimr.job.executor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ClitentApplication {

	public static void main(String[] args) {
        SpringApplication.run(ClitentApplication.class, args);
	}

}