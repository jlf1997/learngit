package com.cimr.api.statistics.exception;

public class TimeTooLongException extends Exception{

	private String message;
	
	public TimeTooLongException(String message) {
		super(message);
		this.message = message;
		
	}
	public String getMessage() {
		return message;
	}

}
