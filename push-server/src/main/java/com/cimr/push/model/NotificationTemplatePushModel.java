package com.cimr.push.model;

import com.cimr.push.properties.KeyConstants;
import com.gexin.rp.sdk.base.ITemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;

public class NotificationTemplatePushModel  extends ShownPushModel{
	
	private TransmissionObject transmissionObject;
	

	public TransmissionObject getTransmissionObject() {
		return transmissionObject;
	}

	public void setTransmissionObject(TransmissionObject transmissionObject) {
		this.transmissionObject = transmissionObject;
	}


	@Override
	public ITemplate getTemplate() {
		// TODO Auto-generated method stub
		
		 NotificationTemplate template = new NotificationTemplate();
		    // 设置APPID与APPKEY
		    template.setAppId(KeyConstants.appid);
		    template.setAppkey(KeyConstants.appKey);
		    // 设置通知栏标题与内容
		    template.setTitle(this.getTitle());
		    template.setText(this.getContent());
		    // 配置通知栏图标
		    template.setLogo(KeyConstants.logo);
		    // 配置通知栏网络图标
		    template.setLogoUrl(KeyConstants.logoUrl);
		    // 设置通知是否响铃，震动，或者可清除
		    template.setIsRing(true);
		    template.setIsVibrate(true);
		    template.setIsClearable(true);
		    // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
		    if(this.transmissionObject!=null) {
		    	template.setTransmissionType(transmissionObject.getTransmissionType());
				template.setTransmissionContent(transmissionObject.getTransmissionContent());
		    }
		  
		    // 设置定时展示时间
		    // template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
		    return template;
	}
	
	
	
}
