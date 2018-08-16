package com.cimr.boot.websocket;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.socket.PingMessage;
import org.springframework.web.socket.WebSocketSession;

public class WebSocketUtil {
	 @Autowired
	private WebSocketSession webSocketSession;
	
	public  void heartBeat() throws IOException {
		byte[] bs = new byte[1];
		bs[0] = 'i';
		ByteBuffer byteBuffer = ByteBuffer.wrap(bs);
		PingMessage pingMessage = new PingMessage(byteBuffer);
		
		webSocketSession.sendMessage(pingMessage);
		System.out.println("已发送一个ping包：【" + pingMessage.toString() + "】");
	}
}
