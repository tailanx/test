package com.yidejia.app.mall.net.user;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.net.HttpPostConn;

public class GetCode {

	private int code;
	
	public String getHttpResp(String name) throws IOException{
		String param = new JNICallBack().getHttp4GetCode(name);
		HttpPostConn conn = new HttpPostConn(param);
		return conn.getHttpResponse();
	}
	
	/**
	 * 解析json数据
	 * @param httpResp
	 * @return
	 */
	public boolean analysisGetCode(String httpResp){
		boolean isSuccess = false;
		
		try {
			JSONObject object = new JSONObject(httpResp);
			int respCode = object.getInt("code");
			if(respCode == 1){
				try {
					code = object.getInt("response");
					return true;
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return isSuccess;
	}
	
	/**
	 * 
	 * @return 图片验证码
	 */
	public int getCode(){
		return code;
	}
}
