package com.yidejia.app.mall.model;

public class MsgCenter {
	private String msgid;//消息id
	private String uid;//用户id
	private String pid;//
	private String msg;//消息内容
	private String title;//消息标题
	private String isread;//是否已读
	private String url;//改消息内容的url
	
	public void setMsgid(String msgid){
		this.msgid = msgid;
	}
	public String getMsgid(){
		return msgid;
	}
	
	public void setMsg(String msg){
		this.msg = msg;
	}
	public String getMsg(){
		return msg;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	public String getTitle(){
		return title;
	}
	
	public void setUid(String uid){
		this.uid = uid;
	}
	public String getUid(){
		return uid;
	}
	
	public void setPid(String pid){
		this.pid = pid;
	}
	public String getPid(){
		return pid;
	}
	
	public void setUrl(String url){
		this.url = url;
	}
	public String getUrl(){
		return url;
	}
	
	public void setIsread(String isread){
		this.isread = isread;
	}
	public String getIsread(){
		return isread;
	}
}
