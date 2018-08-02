package com.cloud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 使用Eureka做服务发现.
 * @author 涂平
 */
@SpringBootApplication
@EnableEurekaServer
public class ServiceCloudApplication {
  private static final Logger logger = LoggerFactory.getLogger(ServiceCloudApplication.class);   	
  public static void main(String[] args) {
    SpringApplication.run(ServiceCloudApplication.class, args);
    System.out.println("-----服务注册中心启动成功------");
  }
}
