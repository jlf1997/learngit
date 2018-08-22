package com.cimr.api.code.sender;

public interface SendFallBack {
	public void onSuccess();
	
	public void onFaild(String cause);
}
