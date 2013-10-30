package com.yidejia.app.mall.exception;

/**
 * 无搜索结果异常
 */
public class NullSearchResultEx extends Exception{

	private static final long serialVersionUID = 1L;
	
	public NullSearchResultEx(){
		super();
	}
	
	public NullSearchResultEx(String message){
		super(message);
		this.message = message;
	}
	
	public NullSearchResultEx(String message, Throwable cause){
		super(message, cause);
	}
	
	public NullSearchResultEx(Throwable cause){
		super(cause);
	}
	
	private String message;
	
	public String getMessage(){
		return message;
	}
}
