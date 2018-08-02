package com.cloud.subsystem.app.model;

import com.gexin.rp.sdk.base.ITemplate;

/**
 * 不在下拉栏中展示的消息，纯后台消息
 * @author Administrator
 *
 */
public  class TransmissionObject {
		// 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
		private Integer transmissionType;
		
		
		// 透传消息内容
		private String transmissionContent;

		public Integer getTransmissionType() {
			return transmissionType;
		}

		public void setTransmissionType(Integer transmissionType) {
			this.transmissionType = transmissionType;
		}

		public String getTransmissionContent() {
			return transmissionContent;
		}

		public void setTransmissionContent(String transmissionContent) {
			this.transmissionContent = transmissionContent;
		}

		

		
}
