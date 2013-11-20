package com.yidejia.app.mall.net.user;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.net.HttpPostConn;

public class ResetPsw {
	
	public String getHttpResponse(String username, String password,String code) throws IOException{
		HttpPostConn conn = new HttpPostConn(new JNICallBack().getHttp4ResetPsw(username, password, code));
		return conn.getHttpResponse();
	}
	
	public boolean analysisHttp(String httpresp){
		boolean issuccess = false;
		try {
			JSONObject jsonObject = new JSONObject(httpresp);
			int code = jsonObject.getInt("code");
			if(code == 1){
				issuccess = true;
			} else {
				issuccess = false;
				msg = jsonObject.getString("msg");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return issuccess;
	}
	
	private String msg = "";
	public String getMsg(){
		return msg;
	}
}
