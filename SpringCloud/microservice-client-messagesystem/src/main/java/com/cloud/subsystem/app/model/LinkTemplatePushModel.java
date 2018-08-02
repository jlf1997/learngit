package com.cloud.subsystem.app.model;

import com.cloud.subsystem.app.properties.KeyConstants;
import com.gexin.rp.sdk.base.ITemplate;
import com.gexin.rp.sdk.template.LinkTemplate;

public class LinkTemplatePushModel extends ShownPushModel{

	/**
	 * 浏览器打开地址
	 */
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public ITemplate getTemplate() {
		// TODO Auto-generated method stub
		LinkTemplate template = new LinkTemplate();
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
	    // 设置打开的网址地址
	    template.setUrl(this.getUrl());
	    // 设置定时展示时间
	    // template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
	    return template;
	}
	
	
}
