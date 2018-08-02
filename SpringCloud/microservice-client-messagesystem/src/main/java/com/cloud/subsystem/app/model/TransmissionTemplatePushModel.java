package com.cloud.subsystem.app.model;

import com.cloud.subsystem.app.properties.KeyConstants;
import com.gexin.rp.sdk.template.TransmissionTemplate;

public class TransmissionTemplatePushModel extends PushModel{
		
    private TransmissionObject transmissionObject;
	@Override
	public TransmissionTemplate getTemplate() {
		// TODO Auto-generated method stub
		TransmissionTemplate template = new TransmissionTemplate();
	    template.setAppId(KeyConstants.appid);
	    template.setAppkey(KeyConstants.appKey);
	    // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
	    if(transmissionObject!=null) {
	    	template.setTransmissionType(transmissionObject.getTransmissionType());
	 	    template.setTransmissionContent(transmissionObject.getTransmissionContent());
	    }
	   
	    // 设置定时展示时间
	    // template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
	    return template;
	}
}
