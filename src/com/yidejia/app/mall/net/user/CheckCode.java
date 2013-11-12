package com.yidejia.app.mall.net.user;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.net.HttpPostConn;

public class CheckCode {
	
	
	public String getHttpResp(String name, String msgCode) throws IOException{
		String param = JNICallBack.getHttp4CheckCode(name, msgCode);
		HttpPostConn conn = new HttpPostConn(param);
		return conn.getHttpResponse();
	}
	
	
	public boolean analysisResp(String httpResp){
		try {
			JSONObject object = new JSONObject(httpResp);
			int respcode = object.getInt("code");
			if(respcode == 1){
				return true;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
