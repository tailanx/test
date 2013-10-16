package com.yidejia.app.mall.model;

public class UserCount {
	private String scores;//积分
	private String order;//订单
	private String favoliten;//收藏
	private String msg;//消息数
	
	public void setScores(String scores){
		this.scores = scores;
	}
	public String getScores(){
		return scores;
	}
	
	public void setOrder(String order){
		this.order = order;
	}
	public String getOrder(){
		return order;
	}
	
	public void setFavoliten(String favoliten){
		this.favoliten = favoliten;
	}
	public String getFavoliten(){
		return favoliten;
	}
	
	public void setMsg(String msg){
		this.msg = msg;
	}
	public String getMsg(){
		return msg;
	}
}
