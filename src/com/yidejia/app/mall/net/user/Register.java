package com.yidejia.app.mall.net.user;

import java.io.IOException;

import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.net.HttpPostConn;

public class Register {
	public String getHttpResponse(String username, String password, String cps, String ip) throws IOException{
		HttpPostConn conn = new HttpPostConn(JNICallBack.getHttp4Register(username, password, cps, ip));
		return conn.getHttpResponse();
	}
}
