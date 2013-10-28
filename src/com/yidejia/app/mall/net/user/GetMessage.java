package com.yidejia.app.mall.net.user;

import java.io.IOException;

import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.net.HttpGetConn;
import com.yidejia.app.mall.net.HttpPostConn;

public class GetMessage {
	public String getHttpResponse(String userId, String offset, String limit, String token) throws IOException{
//		HttpPostConn conn = new HttpPostConn(JNICallBack.getHttp4GetMessage(userId, token, offset, limit));
//		return conn.getHttpResponse();
		HttpGetConn conn = new HttpGetConn(JNICallBack.getHttp4GetMessage(userId, token, offset, limit), true);
		return conn.getJsonResult();
	}
}
