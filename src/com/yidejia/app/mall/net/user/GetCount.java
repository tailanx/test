package com.yidejia.app.mall.net.user;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.yidejia.app.mall.exception.TimeOutEx;
import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.net.HttpGetConn;
import com.yidejia.app.mall.net.HttpPostConn;

public class GetCount {
	
	private String scores;
	private String order;
	private String favoliten;
	private String msg;
	
	public String getHttpResponse(String userId, String token) throws IOException, TimeOutEx{
//		HttpPostConn conn = new HttpPostConn(JNICallBack.getHttp4GetCount(userId, token));
//		return conn.getHttpResponse();
		HttpGetConn conn = new HttpGetConn(new JNICallBack().getHttp4GetCount(userId, token), true);
		return conn.getJsonResult();
	}
	
	public boolean analysis(String httpResp) {
		JSONObject httpObject;
		try {
			httpObject = new JSONObject(httpResp);
			int code = httpObject.getInt("code");
			if(code == 1){
				String response = httpObject.getString("response");
//				analysis(response);
				JSONObject resObject = new JSONObject(response);
				scores = resObject.getString("scores");
				order = resObject.getString("order");
				favoliten = resObject.getString("favoliten");
//				Log.i("info", favoliten +"  fa");
				msg = resObject.getString("msg");
				return true;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public String getScores() {
		return scores;
	}

	public String getOrder() {
		return order;
	}

	public String getFavoliten() {
		return favoliten;
	}

	public String getMsg() {
		return msg;
	}
	
	
}
