/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.cimr.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.cimr.sysmanage.model.Dict;
import com.cimr.sysmanage.service.DictService;
import com.cimr.util.Assist;
import com.xiaoleilu.hutool.json.JSONUtil;


/**
 * 字典工具类
 * @author suhuanzhao
 * @version 2018-3-13
 */
public class DictUtils {
	
	private static DictService dictService = SpringContextUtil.getBean("sysDictService", DictService.class);

	public static final String CACHE_DICT_MAP = "dictMap";
	
	public static String getDictLabel(String value, String type, String defaultValue){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(value)){
			for (Dict dict : getDictList(type)){
				if (type.equals(dict.getType()) && value.equals(dict.getValue())){
					return dict.getLabel();
				}
			}
		}
		return defaultValue;
	}
	
	public static String getDictLabels(String values, String type, String defaultValue){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(values)){
			List<String> valueList = new ArrayList<>();
			for (String value : StringUtils.split(values, ",")){
				valueList.add(getDictLabel(value, type, defaultValue));
			}
			return StringUtils.join(valueList, ",");
		}
		return defaultValue;
	}

	public static String getDictValue(String label, String type, String defaultLabel){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(label)){
			for (Dict dict : getDictList(type)){
				if (type.equals(dict.getType()) && label.equals(dict.getLabel())){
					return dict.getValue();
				}
			}
		}
		return defaultLabel;
	}
	
	public static List<Dict> getDictList(String type){
		//TODO 需要增加对缓存的支持
		Assist assist = new Assist();
		assist.setRequires(Assist.andEq("type", type));
		List<Dict> dictList = dictService.selectObj_common(assist);
		if (dictList == null){
			dictList = new ArrayList<>();
		}
		return dictList;
	}
	
	/**
	 * 返回字典列表（JSON）
	 * @param type
	 * @return
	 */
	public static String getDictListJson(String type){
		return JSONUtil.toJsonStr(getDictList(type));
	}
	
}
