package com.cimr.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;








public class MapUtil
{
  public static StringBuffer buildToString(Map map, String block1, String block2)
  {
    StringBuffer sb = new StringBuffer();
    Iterator names = map.keySet().iterator();
    while (names.hasNext()) {
      String name = (String)names.next();
      if (name != null) {
        if (!"".equals(sb.toString())) {
          sb.append(block2);
        }
        sb.append(name);
        sb.append(block1);
        Object value = map.get(name);
        if (value == null) {
          sb.append("<NULL>");
        } else if ((value instanceof String)) {
          sb.append((String)value);
        } else if (!(value instanceof String[])) {
          sb.append(value.toString());
        } else {
          String[] delim = (String[])value;
          sb.append(delim[0]);
        }
      }
    }
    







    return sb;
  }
  
  public static Map<String, Object> toMap(Map<String, String[]> map) {
    Map<String, Object> resultMap = new HashMap(map.size());
    
    for (Map.Entry<String, String[]> entry : map.entrySet()) {
      int len = ((String[])entry.getValue()).length;
      if (len == 1) {
        resultMap.put(entry.getKey(), ((String[])entry.getValue())[0]);
      } else if (len > 1) {
        resultMap.put(entry.getKey(), entry.getValue());
      }
    }
    return resultMap;
  }
}
