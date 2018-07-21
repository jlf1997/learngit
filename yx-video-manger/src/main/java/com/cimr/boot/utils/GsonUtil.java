package com.cimr.boot.utils;

import java.util.List;

import com.google.gson.JsonElement;



public class GsonUtil {
	
	private static FastGson fastGson = new FastGson();
	/**
	 * json字符串转换未对应对象数组
	 * @param jsonstr
	 * @param cls
	 * @return
	 */
	public static <T> List<T> jsonToList(String jsonstr,Class<T> cls) {
		
		return fastGson.jsonToList(jsonstr, cls);
		
	}
	/**
	 * 
	 * @param jsonstr
	 * @param cls
	 * @return
	 */
	public static <T> T jsonToObj(String jsonstr,Class<T> cls) {
		return fastGson.jsonToObj(jsonstr, cls);		
	}
	
	public static <T> String objToJson(T t) {
		return fastGson.objToJson(t);
	}
	
	public static boolean isJson(String json) {
		return fastGson.isJson(json);
	}
	
	
	public static <T> JsonElement strToJsonElement(String jsonstr) {
		return fastGson.strToJsonElement(jsonstr);
	}
	
}
