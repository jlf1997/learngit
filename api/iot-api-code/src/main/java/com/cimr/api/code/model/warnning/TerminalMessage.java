package com.cimr.api.code.model.warnning;

import java.util.Map;

import com.google.gson.JsonObject;


/**
 * 终端发送的消息
 * @author Administrator
 *
 */
public class TerminalMessage {

	
	private String consumerId;
	
	private JsonObject data;
	
	private Long msgId;
	
	private Integer type;
	
	private Integer title;
	
	private String producerId;

	public String getConsumerId() {
		return consumerId;
	}

	public void setConsumerId(String consumerId) {
		this.consumerId = consumerId;
	}

	
	

	

	public JsonObject getData() {
		return data;
	}

	public void setData(JsonObject data) {
		this.data = data;
	}

	public Long getMsgId() {
		return msgId;
	}

	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getTitle() {
		return title;
	}

	public void setTitle(Integer title) {
		this.title = title;
	}

	public String getProducerId() {
		return producerId;
	}

	public void setProducerId(String producerId) {
		this.producerId = producerId;
	}
	
	
}
