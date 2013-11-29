package com.yidejia.app.mall.net.user;

import java.io.IOException;

import com.yidejia.app.mall.exception.TimeOutEx;
import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.net.HttpPostConn;

public class ChangeRead {
	public String getHttpResponse(String userId, String msgId, String token) throws IOException, TimeOutEx{
		HttpPostConn conn = new HttpPostConn(new JNICallBack().getHttp4ChangeRead(userId, msgId, token));
		return conn.getHttpResponse();
	}
}
