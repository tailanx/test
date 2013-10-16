package com.yidejia.app.mall.net.voucher;

import java.io.IOException;

import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.net.HttpPostConn;

public class Verify {
	public String getHttpResponse(String goods, String userid) throws IOException{
		HttpPostConn conn = new HttpPostConn(JNICallBack.getHttp4GetVerify(goods, userid));
		return conn.getHttpResponse();
	}
}
