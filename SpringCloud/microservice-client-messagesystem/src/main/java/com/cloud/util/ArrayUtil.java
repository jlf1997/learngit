package com.cloud.util;

import java.util.LinkedList;
import java.util.List;

public class ArrayUtil {
	 //求两个数组的差集   
    public static List<String> minus(String[] arr1, String[] arr2) {   
        LinkedList<String> list = new LinkedList<String>();   
        LinkedList<String> history = new LinkedList<String>();   
        String[] longerArr = arr1;   
        String[] shorterArr = arr2;   
        //找出较长的数组来减较短的数组   
        if (arr1.length > arr2.length) {   
            longerArr = arr2;   
            shorterArr = arr1;   
        }   
        for (String str : longerArr) {   
            if (!list.contains(str)) {   
                list.add(str);   
            }   
        }   
        for (String str : shorterArr) {   
            if (list.contains(str)) {   
                history.add(str);   
                list.remove(str);   
            } else {   
                if (!history.contains(str)) {   
                    list.add(str);   
                }   
            }   
        }   
      
        return list;   
    }   
   
}
