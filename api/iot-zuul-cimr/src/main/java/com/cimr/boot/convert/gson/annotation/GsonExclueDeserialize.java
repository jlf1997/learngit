package com.cimr.boot.convert.gson.annotation;



import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.annotations.Expose;

/**
 * json字符串转对象时，排除策略：
 * @Expose 注解且deserialize为false 的将排除，其他不排除
 * @Expose默认全为true
 * @author Administrator
 *
 */
public class GsonExclueDeserialize implements ExclusionStrategy{

	@Override
	public boolean shouldSkipField(FieldAttributes f) {
		
		// 这里作判断，决定要不要排除该字段,return true为排除
        Expose expose = f.getAnnotation(Expose.class); 
        if (expose != null && expose.deserialize() == false) return true; //按注解排除
        return false;	
	}

	@Override
	public boolean shouldSkipClass(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}

}
