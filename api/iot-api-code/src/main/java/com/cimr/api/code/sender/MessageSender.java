package com.cimr.api.code.sender;

public interface  MessageSender {
	public void send(String key,Object message,SendFallBack fallBack);
	

	

}
