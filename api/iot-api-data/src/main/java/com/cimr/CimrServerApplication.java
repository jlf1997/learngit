package com.cimr;

import java.io.UnsupportedEncodingException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.cimr.api.code.util.MessageUtil;
import com.cimr.boot.convert.EnableHttpConvert;
import com.cimr.boot.swagger.EnableSwagger2Doc;



@SpringBootApplication
@EnableSwagger2Doc
@EnableHttpConvert
@EnableScheduling
@EnableJpaAuditing
//@EnableFeignClients
public class CimrServerApplication {
	


	public static void main(String[] args) throws UnsupportedEncodingException {
		SpringApplication.run(CimrServerApplication.class, args);
//		test();
//		ff();

	}
	
	private static void test() {
		String cmd = "0x01, 0x1C, 0x1F, 0x17, 0x20, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01";
//		String hex = Integer.toHexString(30);
//		char[] chars =hex.toCharArray();
		char[] chars = {'4','3','1','e'};
		String cou = MessageUtil.parseCommerCode(cmd,6,7,chars);
		System.out.println(cou);
	}
	
	private static void ff() throws UnsupportedEncodingException {
////	String cmd = "0x01, 0x1C, 0x1F, 0x17, 0x20, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01";
	String cmd = "0x01, 0x1C, 0x1F, 0x17, 0x20, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01";
	cmd = MessageUtil.parseCmdWithStr(cmd);
	System.out.println(cmd);
//	
////	byte[] bb = MessageUtil.parseCmdBytes(cmd);
//	byte[] bc = MessageUtil.getBytes(cmd);
//	String tt = new String(bc,"ISO8859-1");
//	System.out.println(tt);
	}
	
	
}
