package com.cimr.boot.convert.gson.annotation;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.annotations.Expose;


/**
 * 对象转json时，排除策略：
 * @Expose 注解且serialize为false 的将排除，其他不排除
 * @author yangxiang
 *
 */
public class GsonExclueSerialize implements ExclusionStrategy{

	@Override
	public boolean shouldSkipField(FieldAttributes f) {
		// 这里作判断，决定要不要排除该字段,return true为排除
        Expose expose = f.getAnnotation(Expose.class); 
        if (expose != null && expose.serialize() == false) return true; //按注解排除
        return false;	
	}

	@Override
	public boolean shouldSkipClass(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}

}
