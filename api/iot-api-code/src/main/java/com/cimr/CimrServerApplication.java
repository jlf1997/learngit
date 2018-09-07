package com.cimr;

import java.io.UnsupportedEncodingException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.netflix.feign.EnableFeignClients;
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
	
	private static void parseMessage() {
	String	message = "{\r\n" + 
				"    \"consumerId\": \"APP\",\r\n" + 
				"    \"data\": {\r\n" + 
				"        \"T_CW\": 50,\r\n" + 
				"        \"T_CW_STATUS\": 0,\r\n" + 
				"        \"YHT_HYD_OIL\": true,\r\n" + 
				"        \"YHT_HYD_OIL_STATUS\": 1,\r\n" + 
				"        \"Z_HOLE\": 35,\r\n" + 
				"        \"Z_HOLE_STATUS\": 2,\r\n" + 
				"        \"gatherMsgTime\": 1534746537288,\r\n" + 
				"        \"projectNo\": \"P0001\",\r\n" + 
				"        \"terminalNo\": \"TEL0000001\"\r\n" + 
				"    },\r\n" + 
				"    \"msgId\": 46698,\r\n" + 
				"    \"msgTime\": 1534746537303,\r\n" + 
				"    \"producerId\": \"SVR0002\",\r\n" + 
				"    \"title\": 2,\r\n" + 
				"    \"type\": 10,\r\n" + 
				"    \"version\": 1\r\n" + 
				"}";
//		WarningService.parseMessage(message);
	}
	
	private static void sss(StringBuilder sb) {
		sb.append("hhhhh");
	}
	
	
}
