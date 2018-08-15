package com.cimr.remote;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.netflix.ribbon.proxy.annotation.Hystrix;

@FeignClient(name= "client")
@Hystrix
public interface ClientRemote {
	 @RequestMapping(value = "/demo/test")
	    public String helloClient();
}
