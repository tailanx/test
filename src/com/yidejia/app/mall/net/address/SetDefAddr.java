package com.yidejia.app.mall.net.address;

import java.io.IOException;

import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.net.HttpPostConn;

public class SetDefAddr {
	public String getHttpResponse(String cid, String aid, String token) throws IOException{
		HttpPostConn conn = new HttpPostConn(JNICallBack.getHttp4SetDefAddr(cid, aid, token));
		return conn.getHttpResponse();
	}
}
