package com.cimr.api.code.model.mgr;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import com.alibaba.fastjson.JSON;



/**
 * 类描述:封装消息类
 * 作者:admin
 * 创建时间:2017年12月29日 下午4:23:42
 */
public class Message implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 消息标题
	 */
	private Byte title;
	
	/**
	 * 消息类型
	 */
	private Byte type;
	
	/**
	 * 消息版本
	 */
	private Byte version;
	
	/**
	 * 消息ID
	 */
	private Integer msgId;
	
	/**
	 * 消息最初生成时间
	 */
	private Date msgTime;
	
	/**
	 * 消息消费者ID
	 */
	private String consumerId;
	
	/**
	 * 生产者ID
	 */
	private String producerId;
	
	/**
	 * 消息内容主体
	 */
	private Map<String, Object> data;
	
	//默认构造函数
	public Message() {
		super();
	}

	/**
	 * 构造函数(初始构造)，产生消息时使用
	 * @param title
	 * @param type
	 * @param serial
	 * @param msgId
	 * @param data
	 */
	public Message(byte title, byte type, byte version, String producerId, String consumerId, Map<String, Object> data) {
		this.title = title;
		this.type = type;
		this.version = version;
		this.producerId = producerId;
		this.consumerId = consumerId;
		this.data = data;
		this.msgId = (int) (Math.random() * 65535);
		this.msgTime = new Date();
	}
	
	/**
	 * 构造函数(中间构造)，接收消息时使用
	 * @param title
	 * @param type
	 * @param serial
	 * @param data
	 */
	public Message(byte title, byte type, byte version, int msgId, String producerId, String consumerId, Date msgTime, Map<String, Object> data) {
		this.title = title;
		this.type = type;
		this.version = version;
		this.msgId = msgId;
		this.producerId = producerId;
		this.consumerId = consumerId;
		this.data = data;
		this.msgTime = msgTime;
	}

	public Byte getTitle() {
		return title;
	}

	public void setTitle(Byte title) {
		this.title = title;
	}

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	public Byte getVersion() {
		return version;
	}

	public void setVersion(Byte version) {
		this.version = version;
	}

	public Integer getMsgId() {
		return msgId;
	}

	public void setMsgId(Integer msgId) {
		this.msgId = msgId;
	}

	public Date getMsgTime() {
		return msgTime;
	}

	public void setMsgTime(Date msgTime) {
		this.msgTime = msgTime;
	}

	public String getConsumerId() {
		return consumerId;
	}

	public void setConsumerId(String consumerId) {
		this.consumerId = consumerId;
	}

	public String getProducerId() {
		return producerId;
	}

	public void setProducerId(String producerId) {
		this.producerId = producerId;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	
	public String toJson() {
		return JSON.toJSONString(this);
	}

}
