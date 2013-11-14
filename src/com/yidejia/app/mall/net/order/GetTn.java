package com.yidejia.app.mall.net.order;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.net.HttpPostConn;

public class GetTn {

	/**
	 * 获取流水号json
	 * @param userid
	 * @param orderCode
	 * @param token
	 * @return
	 * @throws IOException
	 */
	public String getTnHttpResp(String userid, String orderCode, String token) throws IOException{
		String url = new JNICallBack().getHttp4GetTn(userid, orderCode, token);
		HttpPostConn conn = new HttpPostConn(url);
		return conn.getHttpResponse();
	}
	
	/**
	 * 解析流水号json数据
	 * @param httpResp
	 * @return
	 */
	public boolean analysisHttpJson(String httpResp){
//		boolean isSuccess = false;
		try {
			JSONObject httpJsonObject = new JSONObject(httpResp);
			int code = httpJsonObject.getInt("code");
			if(code == 1){
				tn = httpJsonObject.getString("response");
				return true;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	
	private String tn;
	
	public String getTn(){
		return tn;
	}
}
