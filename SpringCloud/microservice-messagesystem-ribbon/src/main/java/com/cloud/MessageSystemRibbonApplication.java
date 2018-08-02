package com.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
/**
 * 消息服务器服务消费者Ribbon 基于HTTP和TCP负载均衡器
 * @author tuping
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
@EnableHystrixDashboard
public class MessageSystemRibbonApplication {

  public static void main(String[] args) {
    SpringApplication.run(MessageSystemRibbonApplication.class, args);
    System.out.println("----------消息服务器消费者Ribbon启动成功------------");
  }
}
