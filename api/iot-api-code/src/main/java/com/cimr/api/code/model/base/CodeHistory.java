package com.cimr.api.code.model.base;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.cimr.boot.model.BaseModel;

/**
 * 保存指令发送记录
 * @author Administrator
 *
 */
@Table(name="td_code_history")
@Entity
public class CodeHistory extends BaseModel{

	@Id
	private Long id;
	/**
	 * 指令id
	 */
	private String codeId;
	
	/**
	 * 最终发送的消息
	 */
	@Lob
	private String messageJson;
	/**
	 * 回调结果
	 */
	@OneToMany(mappedBy = "codeHistory")
    @org.hibernate.annotations.ForeignKey(name = "none")
	private List<CallBackLog> callBackLogs;
	
	
	/**
	 * 回调url
	 */
	private String url;
	/**
	 * 发送主题：kafka topic，rabbitmq queue
	 */
	private String topicKey;
	/**
	 * mq类型 kafka，rabbitmq
	 */
	private String mqType;
	/**
	 * mq地址
	 */
	private String mqURI;
	/**
	 * 消息发送阶段 0:发送消息 1发送到mq成功 -1 发送到mq失败 2指令执行成功-2指令执行失败
	 */
	private Integer status;
	
	
	/**
	 * 错误原因
	 */
	@Lob
	private String cause;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessageJson() {
		return messageJson;
	}

	public void setMessageJson(String messageJson) {
		this.messageJson = messageJson;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCodeId() {
		return codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTopicKey() {
		return topicKey;
	}

	public void setTopicKey(String topicKey) {
		this.topicKey = topicKey;
	}

	public String getMqType() {
		return mqType;
	}

	public void setMqType(String mqType) {
		this.mqType = mqType;
	}

	public String getMqURI() {
		return mqURI;
	}

	public void setMqURI(String mqURI) {
		this.mqURI = mqURI;
	}

	public List<CallBackLog> getCallBackLogs() {
		return callBackLogs;
	}

	public void setCallBackLogs(List<CallBackLog> callBackLogs) {
		this.callBackLogs = callBackLogs;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}



	
	
	
	
	
}
