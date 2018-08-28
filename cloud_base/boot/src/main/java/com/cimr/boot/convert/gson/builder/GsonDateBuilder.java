package com.cimr.boot.convert.gson.builder;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class GsonDateBuilder {
	
	public static GsonBuilder getBuilder(GsonBuilder builder) {
		if(builder==null){
			builder = new GsonBuilder();
		}		 
		builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {

			@Override
			public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
					throws JsonParseException {
				Date date = null;
				if (json.getAsJsonPrimitive().isNumber()) {
					date = new Date(json.getAsJsonPrimitive().getAsLong());
				} else {
					String string = json.getAsJsonPrimitive().getAsString();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					try {
						date = sdf.parse(string);
					} catch (ParseException e) {
						

					}
				}

				return date;
			}
		});
		builder.registerTypeAdapter(Date.class, new JsonSerializer<Date>() {

			@Override
			public JsonElement serialize(Date src, Type typeOfSrc,
					JsonSerializationContext context) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
				return new JsonPrimitive(sdf.format(src));
			}
			
			
		});
		return builder;
	}
	
	
}
