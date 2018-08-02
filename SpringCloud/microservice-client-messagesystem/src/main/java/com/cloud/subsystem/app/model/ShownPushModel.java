package com.cloud.subsystem.app.model;


/**
 * 基础推送消息对象
 * @author Administrator
 *
 */
public abstract class ShownPushModel extends PushModel{
	
	/**
	 * 推送消息标题
	 */
	private String title;
	
	/**
	 * 推送消息内容
	 */
	private String content;
	
	/**
	 * 展示开始时间
	 */
	private String durationBegin;
	
	
	/**
	 * 展示结束时间
	 */
	private String durationEnd;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDurationBegin() {
		return durationBegin;
	}

	public void setDurationBegin(String durationBegin) {
		this.durationBegin = durationBegin;
	}

	public String getDurationEnd() {
		return durationEnd;
	}

	public void setDurationEnd(String durationEnd) {
		this.durationEnd = durationEnd;
	}
	
	
	
	
}
