package com.yidejia.app.mall.shiplog;

/**
 * 物流信息
 * @author Administrator
 *
 */
public class ShipLog {
	
	private String context;//内容
	private String time;//时间\
	
	/**
	 * 设置快递物流信息的内容
	 * @param context
	 */
	public void setContext(String context){
		this.context = context;
	}
	
	/**
	 * 
	 * @return 快递物流信息内容
	 */
	public String getContext(){
		return context;
	}
	
	/**
	 * 设置该物流信息的时间
	 * @param time
	 */
	public void setTime(String time){
		this.time = time;
	}
	
	/**
	 * 
	 * @return 该物流信息的时间点
	 */
	public String getTime(){
		return time;
	}
}
