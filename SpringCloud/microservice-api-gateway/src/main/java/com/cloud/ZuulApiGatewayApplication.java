package com.cloud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * 1.统一系统的入口，屏蔽系统的实现细节
 * 2.实现自动化的服务实例维护和负载均衡的路由转发
 * 3.实现接口权限校验
 * 使用@EnableZuulProxy注解激活zuul。
 * 跟进该注解可以看到该注解整合了@EnableCircuitBreaker、@EnableDiscoveryClient，是个组合注解，目的是简化配置。
 * @author tuping
 */
@SpringBootApplication
@EnableZuulProxy
public class ZuulApiGatewayApplication {
  private static final Logger logger = LoggerFactory.getLogger(ZuulApiGatewayApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(ZuulApiGatewayApplication.class, args);
    logger.info("路由服务器启动...");
  }
}
