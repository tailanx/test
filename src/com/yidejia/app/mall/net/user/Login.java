package com.yidejia.app.mall.net.user;

import java.io.IOException;

import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.net.HttpPostConn;

public class Login {
	public String getHttpResponse(String username, String password, String ip) throws IOException{
		HttpPostConn conn = new HttpPostConn(JNICallBack.getHttp4Login(username, password, ip));
		return conn.getHttpResponse();
	}
}
