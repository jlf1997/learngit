package com.cimr.boot.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanMap;

public class BootBeanUtils {

	public static Map convertBean(Object bean) throws Exception {
        Class type=bean.getClass();
        Map returnMap = new HashMap();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor descriptor:propertyDescriptors) {
            String propertyName = descriptor.getName();
            if (!propertyName.equals("class")){
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean);
                if (result!=null){
                    returnMap.put(propertyName,result);
                }else {
                    returnMap.put(propertyName,"");
                }
            }
        }
        return returnMap;
    }
	
	
	  public static <T> T convertMap(Class<T> type, Map map) throws Exception {
	        BeanInfo beanInfo = Introspector.getBeanInfo(type);
	        T obj=type.newInstance();
	        PropertyDescriptor[] propertyDescriptors=beanInfo.getPropertyDescriptors();
	        for (PropertyDescriptor descriptor:propertyDescriptors) {
	            String propertyName = descriptor.getName();
	            if (map.containsKey(propertyName)){
	                Object value = map.get(propertyName);
	                descriptor.getWriteMethod().invoke(obj,value);
	            }
	        }
	        return obj;
	    }
	  
	  /**
	   * 使用json进行深克隆
	   * @param obj
	   * @param cls
	   * @return
	   */
	  public static Object deepClone(Object obj,Class cls) {
		  String jsonstr = GsonUtil.objToJson(obj);
	      return GsonUtil.jsonToObj(jsonstr, cls);
	  }
}
