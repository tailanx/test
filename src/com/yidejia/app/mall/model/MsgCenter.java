package com.yidejia.app.mall.model;

public class MsgCenter {
	private String msgid;//��Ϣid
	private String uid;//�û�id
	private String pid;//
	private String msg;//��Ϣ����
	private String title;//��Ϣ����
	private String isread;//�Ƿ��Ѷ�
	private String url;//����Ϣ���ݵ�url
	
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
