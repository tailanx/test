package com.yidejia.app.mall.net.voucher;

import java.io.IOException;

import com.yidejia.app.mall.exception.TimeOutEx;
import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.net.HttpPostConn;

public class Verify {
	public synchronized String getHttpResponse(String goods, String userid) throws IOException, TimeOutEx{
		HttpPostConn conn = new HttpPostConn(new JNICallBack().getHttp4GetVerify(goods, userid));
		return conn.getHttpResponse();
	}
}
