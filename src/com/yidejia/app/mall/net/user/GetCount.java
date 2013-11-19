package com.yidejia.app.mall.net.user;

import java.io.IOException;

import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.net.HttpGetConn;
import com.yidejia.app.mall.net.HttpPostConn;

public class GetCount {
	public String getHttpResponse(String userId, String token) throws IOException{
//		HttpPostConn conn = new HttpPostConn(JNICallBack.getHttp4GetCount(userId, token));
//		return conn.getHttpResponse();
		HttpGetConn conn = new HttpGetConn(new JNICallBack().getHttp4GetCount(userId, token), true);
		return conn.getJsonResult();
	}
}
