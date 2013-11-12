package com.yidejia.app.mall.net.order;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.net.HttpGetConn;
import com.yidejia.app.mall.net.HttpPostConn;

public class SaveReturnOrder {
	
	public String getHttpResp(String user_id, String order_code,
			String the_date, String contact, String phone, String cause,
			String desc, String token) throws IOException {
		String url = JNICallBack.getHttp4GetReturn(user_id, order_code,
				the_date, contact, phone, cause, desc, token);
		HttpPostConn conn = new HttpPostConn(url);
		return conn.getHttpResponse();
	}
	
	/**
	 * 解析返回数据
	 * @param httpResp
	 * @return
	 * @throws JSONException 
	 */
	public boolean analysisReturn(String httpResp) throws JSONException{
		JSONObject jsonObject = new JSONObject(httpResp);
		int code = jsonObject.getInt("code");
		if(code == 1){
			String msg = jsonObject.getString("msg");
			if("成功".equals(msg)) return true;
		}
		return false;
	}
}
