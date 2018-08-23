package com.cimr.boot.utils;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

public class FastGson {
	private GsonBuilder builder;
	
	private Gson gs;
	
	public FastGson() {
		builder = new GsonBuilder();
		gs = builder.create();
	}
	
	public FastGson(GsonBuilder builder) {
		this.builder = builder;
		gs = builder.create();
	}
	
	public void setBuilder(GsonBuilder builder) {
		this.builder = builder;
		gs = builder.create();
	}
	
	/**
	 * json字符串转换未对应对象数组
	 * @param jsonstr
	 * @param cls
	 * @return
	 */
	public <T> List<T> jsonToList(String jsonstr,Class<T> cls) {
		List<T> list = new ArrayList<>();
		if(isJson(jsonstr)) {
			JsonElement returnData = new JsonParser().parse(jsonstr);			
			if(returnData.isJsonArray()) {
				JsonArray array = returnData.getAsJsonArray();
				for(JsonElement json : array) {					
					T t = gs.fromJson(json, cls);
					list.add(t);
				}
			}else {	
				if(returnData.isJsonObject()) {
					T t = gs.fromJson(returnData, cls);
					list.add(t);
				}
				
			}			
		}else {
			list =null;
		}
		return list;
		
	}
	/**
	 * 
	 * @param jsonstr
	 * @param cls
	 * @return
	 */
	public  <T> T jsonToObj(String jsonstr,Class<T> cls) {		
		if(jsonstr==null || isJson(jsonstr))
			
			return gs.fromJson(jsonstr, cls);
		return null;
	}
	
	public  <T> String objToJson(T t) {		
		return gs.toJson(t);
	}
	
	public  boolean isJson(String json) {
		 try {  
	            new JsonParser().parse(json);  
	            return true;  
	        } catch (JsonParseException e) { 
	          
	            return false;  
	        }  
	}
	
	
	public  <T> JsonElement strToJsonElement(String jsonstr) {		
		return new JsonParser().parse(jsonstr);
	}
}
