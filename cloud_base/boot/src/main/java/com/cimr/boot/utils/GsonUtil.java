package com.cimr.boot.utils;

import java.math.BigDecimal;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;



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
	
	public static JsonElement getJsonElement(JsonObject jsonObject,String memberName){
		if(jsonObject.has(memberName)) {
			return jsonObject.get(memberName);
		}
		return null;
	}
	
	/**
	 * 获取string 类型
	 * @param jsonObject
	 * @param memberName
	 * @return
	 */
	public static String getString(JsonObject jsonObject,String memberName) {
		JsonElement jsonElement =  getJsonElement(jsonObject, memberName);
		if(jsonElement==null) {
			return null;
		}
		return jsonElement.getAsString();
	}
	
	public static Long getLong(JsonObject jsonObject,String memberName) {
		JsonElement jsonElement =  getJsonElement(jsonObject, memberName);
		if(jsonElement==null) {
			return null;
		}
		return jsonElement.getAsLong();
	}
	
	public static Integer getInteger(JsonObject jsonObject,String memberName) {
		JsonElement jsonElement =  getJsonElement(jsonObject, memberName);
		if(jsonElement==null) {
			return null;
		}
		return jsonElement.getAsInt();
	}
	
	public static BigDecimal getBigDecimal(JsonObject jsonObject,String memberName) {
		JsonElement jsonElement =  getJsonElement(jsonObject, memberName);
		if(jsonElement==null) {
			return null;
		}
		return jsonElement.getAsBigDecimal();
	}
	
	public static Boolean getBoolean(JsonObject jsonObject,String memberName) {
		JsonElement jsonElement =  getJsonElement(jsonObject, memberName);
		if(jsonElement==null) {
			return null;
		}
		return jsonElement.getAsBoolean();
	}
	
	public static Byte getByte(JsonObject jsonObject,String memberName) {
		JsonElement jsonElement =  getJsonElement(jsonObject, memberName);
		if(jsonElement==null) {
			return null;
		}
		return jsonElement.getAsByte();
	}
	
	public static Double getDouble(JsonObject jsonObject,String memberName) {
		JsonElement jsonElement =  getJsonElement(jsonObject, memberName);
		if(jsonElement==null) {
			return null;
		}
		return jsonElement.getAsDouble();
	}
	
	public static Float getFloat(JsonObject jsonObject,String memberName) {
		JsonElement jsonElement =  getJsonElement(jsonObject, memberName);
		if(jsonElement==null) {
			return null;
		}
		return jsonElement.getAsFloat();
	}
	
}
