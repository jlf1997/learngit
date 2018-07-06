package com.cimr.push.util;

import com.gexin.rp.sdk.base.ITemplate;
import com.gexin.rp.sdk.base.impl.ListMessage;

/**
 * 个推工具类
 * @author Administrator
 *
 */
public class GeiTuiUtil {

	
	
	/**
	 * 获取默认listMessage
	 * @param template 数据模板
	 * @return
	 */
	public static ListMessage getDefaultListMessage(ITemplate template) {
		 ListMessage message = new ListMessage();
		 //消息是否离线有效
	     message.setOffline(true);
	     // 离线有效时间，单位为毫秒，可选
	     message.setOfflineExpireTime(24 * 3600 * 1000);
	     message.setData(template);
	     message.setPushNetWorkType(0); // 可选，判断是否客户端是否wifi环境下推送，1为在WIFI环境下，0为不限制网络环境。。
	     return message;
	}
	
	
	
	
}
