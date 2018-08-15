package com.cimr.boot.utils;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;



public class RefUtil {

	
	public static Method getWriteMethod(PropertyDescriptor property) {
		Method writeMethod = property.getWriteMethod();
		if (writeMethod != null) {					
			if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
				writeMethod.setAccessible(true);
			}								
		}
		return writeMethod;
	}
	
	public static Method getReadMethod(PropertyDescriptor property) {
		Method readMethod = property.getReadMethod();
		if (readMethod != null) {					
			if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
				readMethod.setAccessible(true);
			}								
		}
		return readMethod;
	} 
	
	public static <T> T getNewInstance() {
		return null;
		
	}
	
	public  static <A extends Annotation> A  getAnnotation(Field field,Class<A> annotationClass) {
		boolean fieldHasAnno = field.isAnnotationPresent(annotationClass);  
		 if(fieldHasAnno){  
			 A fieldAnno = field.getAnnotation(annotationClass);
			 return fieldAnno;
		 }
		 return null;	
	}
	
	public  static <A extends Annotation,T> A  getAnnotation(T t,Class<A> annotationClass) throws NoSuchFieldException, SecurityException {
		Field field = annotationClass.getDeclaredField("");
		return getAnnotation(field,annotationClass);
	}
	
	
	public static <T> List<Object> getValues(Method readMethod,T[] ts) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
			List<Object> list = new ArrayList<>();
			Object obj = null;
			for(T t:ts) {
				obj = readMethod.invoke(t);
				if(obj!=null) {
					list.add(obj);
				}
			}			
			return list;
		
	}
	
	
	
	
}
