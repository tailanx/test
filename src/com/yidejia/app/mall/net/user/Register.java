package com.yidejia.app.mall.net.user;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.yidejia.app.mall.exception.TimeOutEx;
import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.net.HttpPostConn;

public class Register {
	public String getHttpResponse(String username, String password, String cps, String ip) throws IOException, TimeOutEx{
		HttpPostConn conn = new HttpPostConn(new JNICallBack().getHttp4Register(username, password, cps, ip));
		return conn.getHttpResponse();
	}
	
	public boolean analysisHttpResp(String resp){
		boolean isSuccess = false;
		try {
			JSONObject jsonObject = new JSONObject(resp);
			int code = jsonObject.getInt("code");
			if(code == 1003){
				isSuccess = true;
			} else{
				message = jsonObject.getString("msg");
				isSuccess = false;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isSuccess;
	}
	
	private String message = "";
	
	public String getMsg(){
		return message;
	}
}
