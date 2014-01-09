package com.yidejia.app.mall.exception;

public class TimeOutEx extends Exception {
	private static final long serialVersionUID = 1L;
	
	public TimeOutEx(){
		super();
	}
	
	public TimeOutEx(String message) {
		super();
		this.message = message;
	}
	
	public TimeOutEx(String message, Throwable cause){
		super(message, cause);
	}
	
	public TimeOutEx(Throwable cause){
		super(cause);
	}

	private String message;

	public String getMessage() {
		return message;
	}

}
