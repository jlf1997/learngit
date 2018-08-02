package com.cloud.message.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
/**
 * 实例化RestTemplate，通过@LoadBalanced注解开启均衡负载能力.
 * @author admin
 *
 */
@Configuration
public class RestTemplateConfig {
	  @Bean
	  @LoadBalanced
	  public RestTemplate restTemplate() {
		System.out.println("服务注册成功！");
	    return new RestTemplate();
	  }
}
