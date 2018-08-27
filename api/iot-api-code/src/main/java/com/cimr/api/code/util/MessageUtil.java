package com.cimr.api.code.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.cimr.api.code.model.mgr.Message;
import com.cimr.api.comm.model.TerimalModel;

import io.jsonwebtoken.lang.Arrays;



@Configuration
public class MessageUtil {
	
	static {
		//防止fastjson将小数转为BigDecimal
		int features = 0;
		features |= Feature.AutoCloseSource.getMask();
		features |= Feature.InternFieldNames.getMask();
//				features |= Feature.UseBigDecimal.getMask();
		features |= Feature.AllowUnQuotedFieldNames.getMask();
		features |= Feature.AllowSingleQuotes.getMask();
		features |= Feature.AllowArbitraryCommas.getMask();
		features |= Feature.SortFeidFastMatch.getMask();
		features |= Feature.IgnoreNotMatch.getMask();
		JSON.DEFAULT_PARSER_FEATURE = features;
		//防止fastjson将字段首字母小写
		System.setProperty("fastjson.compatibleWithJavaBean", "true");
	}
	/**
	 * 构造消息对象
	 * @param cmdType 指令类型
	 * @param cmdTitle 指令标题
	 * @param cmdContents 指令内容
	 * @param telIds 终端编号
	 * @return 消息指令对象
	 * @throws UnsupportedEncodingException
	 */
	public static Message getMessage(int type,int title,Integer cmdType,Integer cmdTitle,String cmdContents,List<String> telIds) throws UnsupportedEncodingException {
	   return MessageUtil.getMessage(1, type, title, cmdType, cmdTitle, cmdContents, telIds);
	}
	
	
	/**
	 * 构造消息对象
	 * @param cmdType 指令类型
	 * @param cmdTitle 指令标题
	 * @param cmdContents 指令内容
	 * @param telIds 终端编号
	 * @return 消息指令对象
	 * @throws UnsupportedEncodingException
	 */
	public static Message getMessage(int version,int type,int title,Integer cmdType,Integer cmdTitle,String cmdContents,List<String> telIds) throws UnsupportedEncodingException {
		Message message = new Message();
		message.setProducerId("app");
		message.setConsumerId("iot");
		Random rm = new Random();
		message.setMsgId(rm.nextInt(65534)+1);
		message.setMsgTime(new Date());
		message.setVersion((byte) version);
		message.setType((byte) type);
		message.setTitle((byte) title);
		Map<String,Object> data = new HashMap<>();
		if(cmdType!=null) {
			data.put("cmdType", cmdType);
		}
		if(cmdTitle!=null) {
			data.put("cmdTitle", cmdTitle);
		}
		if(cmdContents!=null) {
			cmdContents = parseCmdWithStr(cmdContents);
			data.put("cmdContent", cmdContents);	
		}
			
		if(telIds!=null) {
		
			telIds.stream().filter(predicate->{
				if("".equals(predicate)) {
					return false;
				}
				return true;
			});
			String telIdsJson = JSON.toJSONString(telIds);
			data.put("telIds", telIdsJson);
		}
		message.setData(data);
		return message;
	}
	
	
	/**
	 * 终端对象列表转换为终端编号列表
	 * @param terList
	 * @return
	 */
	public static List<String> convertTerminalModelListToStringList(List<TerimalModel> terList){
		List<String> list = new ArrayList<>();
		Assert.notNull(terList,"terList is not null");
		terList.forEach(terminal->{
			Assert.notNull(terminal,"terminal is not null");
			list.add(terminal.getTerId());
		});
		return list;
	}
	
	
	/**
	 * 通用传输命令
	 * @param version
	 * @param type
	 * @param title
	 * @param data
	 * @param telIds
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static Message getSysMessage(Integer version,Integer type,Integer title,HttpServletRequest request,List<String> telIds) throws UnsupportedEncodingException {
		Message message = new Message();
		message.setProducerId("system");
		message.setConsumerId("iot");
		Random rm = new Random();
		message.setMsgId(rm.nextInt(65534)+1);
		message.setMsgTime(new Date());
		
		message.setVersion(Byte.valueOf(version+""));
		
		
		message.setType(Byte.valueOf(type+""));
		
		message.setTitle(Byte.valueOf(title+""));
		Map<String,Object> data = new HashMap<>();
		if(request!=null) {
			Enumeration<String> en = request.getParameterNames();
			while(en.hasMoreElements()) {
				String ele = en.nextElement();
				if(!"title".equals(ele)
					&& !"type".equals(ele)
					&& !"version".equals(ele)) {
					data.put(ele, request.getParameter(ele));
				}
			
			}
		}
		
		if(telIds!=null && telIds.size()>0) {
		
			telIds.stream().filter(predicate->{
				if("".equals(predicate)) {
					return false;
				}
				return true;
			});
			String telIdsJson = JSON.toJSONString(telIds);
			data.put("telIds", telIdsJson);
		}
		message.setData(data);
		return message;
	}
	
	public static String parseCmdWithStr(String content) {
		content = StringUtils.deleteWhitespace(content);
		String[] str = content.split(",");
		char[] out = new char[str.length*3];
		char[] e;
		for(int i=0;i<str.length;i++) {
			 e = str[i].toCharArray();
			 
			 out[i*3] = (e[2]);
			 out[i*3+1] = (e[3]);
			 out[i*3+2] = ',';
		}
		content = new String(out);
		content = content.substring(0, content.length()-1);
		return content;
	}
	
	
	/**
	 * 解析指令格式，变为iso编码 byte数组
	 * @param cmd
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String parseCmd(String cmd) throws UnsupportedEncodingException {
		return parseCmd(cmd,"ISO8859-1");
	}
	
	/**
	 * 解析指令格式，变为指定编码的 byte数组
	 * @param cmd
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String parseCmd(String cmd,String charsetName) throws UnsupportedEncodingException {
		cmd = cmdFomat(cmd);
		byte[] bytes =getBytes(cmd);
		return new String(bytes,charsetName);
	}
	
	
	/**
	 * 解析指令内容为二进制
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String cmdFomat(String content) {
		content = StringUtils.deleteWhitespace(content);
		String[] str = content.split(",");
		char[] out = new char[str.length*2];
		char[] e;
		for(int i=0;i<str.length;i++) {
			 e = str[i].toCharArray();
			 
			 out[i*2] = (e[2]);
			 out[i*2+1] = (e[3]);
		}
		return new String(out);
	}
	
	/**
	 * 字符串长度是否是奇数
	 * @param str
	 * @return
	 */
	public static boolean isOdd(String str) {
		int length = str.length();
		int isOdd = length % 2;
		if (isOdd == 0)
			return false;
		else
			return true;
	}
	
	/**
	 * 字符串转字节数组
	 * @param str
	 * @return
	 */
	public static byte[] getBytes(String str) {
		boolean isOdd = isOdd(str);
		int size = str.length();
		if (isOdd) {
			byte[] byteOdd = new byte[size / 2 + 1];
			for (int j = 0, i = 0; i < str.length() - 1; i++) {
				if (i % 2 == 0) {
					byte a = getByteFromChar(str.charAt(i));
					byte b = getByteFromChar(str.charAt(++i));
					byteOdd[j++] = (byte) (a * 16 + b);
				}
 
			}
			byteOdd[size / 2] = (byte) str.charAt(str.length() - 1);
			return byteOdd;
		} else {
			byte[] byteEven = new byte[size / 2];
			for (int j = 0, i = 0; i < str.length(); i++) {
				if (i % 2 == 0) {
					byte a = getByteFromChar(str.charAt(i));
					byte b = getByteFromChar(str.charAt(++i));
					byteEven[j++] = (byte) (a * 16 + b);
				}
 
			}
			return byteEven;
		}
 
	}
 
	/**
	 * char转字节
	 * @param c
	 * @return
	 */
	public static byte getByteFromChar(char c) {
		if (c == '0') {
			return 0;
		} else if (c == '1') {
			return 1;
		} else if (c == '2') {
			return 2;
		} else if (c == '3') {
			return 3;
		} else if (c == '4') {
			return 4;
		} else if (c == '5') {
			return 5;
		} else if (c == '6') {
			return 6;
		} else if (c == '7') {
			return 7;
		} else if (c == '8') {
			return 8;
		} else if (c == '9') {
			return 9;
		} else if (c == 'a'||c=='A') {
			return 10;
		} else if (c == 'b'||c=='B') {
			return 11;
		} else if (c == 'c'||c=='C') {
			return 12;
		} else if (c == 'd'||c=='D') {
			return 13;
		} else if (c == 'e'||c=='E') {
			return 14;
		} else if (c == 'f'||c=='F') {
			return 15;
		}
		return -1;
	}
	
	
	/**
	 * 对指令进行替换
	 * @param code 原始指令
	 * @param begin 开始
	 * @param end 结束
	 * @param value 替换的值
	 * @return
	 */
	public static String parseCommerCode(String codes,int begin,int end ,char[] chars) {
		String[] code = codes.split(",");
		List<String> newValue = getStringFromCharBy2(chars);
		
		for(int i=0;i<end-begin+1;i++) {
			if(i<newValue.size()) {
				code[end-i-1] = "0x"+newValue.get(i);
			}
			
		}
		return StringUtils.join(code, ",");
	}
	
	
//	/**
//	 * 通配符替换指令中的值，其中P1...PN,表示有n组需要替换的值
//	 * @param codes
//	 * @param charsList
//	 * @return
//	 */
//	public static String parseCommerCode(String codes ,List<char[]> charsList) {
//		String[] code = codes.split(",");
//		List<String> newValue = getStringFromCharBy2(chars);
//		
//		for(int i=0;i<end-begin+1;i++) {
//			if(i<newValue.size()) {
//				code[end-i-1] = "0x"+newValue.get(i);
//			}
//			
//		}
//		return StringUtils.join(code, ",");
//	}
	

	
	/**
	 * 获取指令占位符位置
	 * @param cmd
	 * @return
	 */
	private static int[] getPlaceholder(String[] code) {
		
		return null;
	}
	
	
	/**
	 * 获取2进制char
	 * @param chars
	 * @return
	 */
	private static List<String> getStringFromCharBy2(char[] chars) {
		List<String> strs = new ArrayList<>();
		char[] ctwo = new char[2];
		String s ;
		//偶数
		if(chars.length%2==0) {
			//java 与c 编码方式不同 ，需要转换
			for(int i=0;i<chars.length;i=i+2) {
				ctwo[0] = chars[i];
				ctwo[1] = chars[i+1];
				s = String.valueOf(ctwo);
				strs.add(s);
			}
		}else {
			ctwo[0] = '0';
			ctwo[1] = chars[0];
			s = String.valueOf(ctwo);
			strs.add(s);
			for(int i=1;i<chars.length;i=i+2) {
				ctwo[0] = chars[i];
				ctwo[1] = chars[i+1];
				s = String.valueOf(ctwo);
				strs.add(s);
			}
		}
		
		return  strs;
	
	}
 
}
