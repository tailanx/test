package com.yidejia.app.mall.net.user;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.yidejia.app.mall.exception.TimeOutEx;
import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.net.HttpPostConn;

public class SendMsg {
	
	
	public String getHttpResp(String name, String code) throws IOException, TimeOutEx{
		String param = new JNICallBack().getHttp4SendMsg(name, code);
		HttpPostConn conn = new HttpPostConn(param);
		return conn.getHttpResponse();
	}
	
	public boolean analysisHttpResp(String httpResp){
		boolean isSuccess = false;
		try {
			JSONObject object = new JSONObject(httpResp);
			int code = object.getInt("code");
			if(code == 1){
				isSuccess = true;
				response = object.getString("response");
				return isSuccess;
			}else{
				response = object.getString("response");
				JSONObject oJsonObject = new JSONObject(response);
				
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isSuccess;
	}
	
	private String response;
	
	public String getResponse(){
		return response;
	}
}
