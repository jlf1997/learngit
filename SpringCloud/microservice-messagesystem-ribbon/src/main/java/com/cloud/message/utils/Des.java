package com.cloud.message.utils;

import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import com.alibaba.fastjson.JSONArray;

public class Des {

	public static String KEY = "JfYv4bVZ";

	public static String decrypt(String message, String key) throws Exception {
		byte[] bytesrc = convertHexString(message);
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
		cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
		byte[] retByte = cipher.doFinal(bytesrc);
		return new String(retByte);
	}
	

	public static byte[] encrypt(String message, String key) throws Exception {
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
		return cipher.doFinal(message.getBytes("UTF-8"));
	}

	public static byte[] convertHexString(String ss) {
		byte digest[] = new byte[ss.length() / 2];
		for (int i = 0; i < digest.length; i++) {
			String byteString = ss.substring(2 * i, 2 * i + 2);
			int byteValue = Integer.parseInt(byteString, 16);
			digest[i] = (byte) byteValue;
		}
		return digest;
	}

	

	public static String toHexString(byte b[]) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			String plainText = Integer.toHexString(0xff & b[i]);
			if (plainText.length() < 2)
				plainText = "0" + plainText;
			hexString.append(plainText);
		}
		return hexString.toString();
	}
	
	
	/*public static void main(String[] args) throws Exception {
		String key = KEY;
		String value = "startTime=2018-02-01&endTime=2018-02-01&token=www.51tys.com&stationID=[\"0B5C0BF9-3709-4264-BB55-9599E135FF33\"]&terminalID=[\"13300658534\"]";
		String jiami = java.net.URLEncoder.encode(value, "utf-8").toLowerCase();
		String a = toHexString(encrypt(value, key)).toUpperCase();
		System.out.println(a);
		String b = java.net.URLDecoder.decode(Des.decrypt("5636F214D4C5A721246220093E92A3DCC3E55B8315CD3BBC2855C11834714CBD1B75B89C0D240889FF43DF1782599C1BD4368318113E3AC2D9AB1C28F42FD4E75B1A35E2FD87CD16C1DBA8F31D1E8136242EBEB2C24CC865", Des.KEY), "utf-8");
		System.out.println(b);
		Map<String,Object> map = new HashMap<String,Object>();
		for (String string : b.split("&")) {
			String[] str = string.split("=");
			map.put(str[0], str[1]);
		}
		System.out.println(map.get("stationID").toString());
		// String[] str = JSONArray.parseObject(map.get("stationID").toString(), String[].class);
		
		
	}*/
}
