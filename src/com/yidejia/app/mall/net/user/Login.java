package com.yidejia.app.mall.net.user;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.exception.TimeOutEx;
import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.net.HttpPostConn;
import com.yidejia.app.mall.net.ImageUrl;

public class Login {
	
	private String TAG = Login.class.getName();
	
	public String getHttpResponse(String username, String password, String ip) throws IOException, TimeOutEx{
		HttpPostConn conn = new HttpPostConn(new JNICallBack().getHttp4Login(username, password, ip));
		return conn.getHttpResponse();
	}
	
	private boolean analysisHttpResp(Context context, String httpResp){
		boolean isSuccess = false;
		try {
			int code;
			JSONObject jsonObject = new JSONObject(httpResp);
			code = jsonObject.getInt("code");
			String response = jsonObject.getString("response");
			if(code == 1000){
				MyApplication myApplication = MyApplication.getInstance();
				JSONObject responseObject = new JSONObject(response);
				String customer_id = responseObject.getString("customer_id");
				myApplication.setUserId(customer_id);
				myApplication.setPassword(responseObject.getString("password"));
				myApplication.setVip(responseObject.getString("customer_grade"));
				myApplication.setNick(responseObject.getString("customer_nick"));
				String imgUrl = responseObject.getString("avatar_path");
				myApplication.setUserHeadImg(ImageUrl.IMAGEURL + imgUrl + "!100");
				String token = responseObject.getString("token");
				myApplication.setToken(token);
				message = context.getResources().getString(R.string.login_success);
				myApplication.setIsLogin(true);
				return true;
			} if(code == 1001 || code ==1002){
				message = context.getResources().getString(R.string.login_error);
//				throw new UserLoginEx(message);
				isSuccess = false;
			} else{
				message = jsonObject.getString("msg");
				isSuccess = false;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "login json ex");
			e.printStackTrace();
			isSuccess = false;
		}
		return isSuccess;
	}
	
	public boolean parseLogin(String httpResp){
		boolean isSuccess = false;
		try {
			int code;
			JSONObject jsonObject = new JSONObject(httpResp);
			code = jsonObject.optInt("code");
			String response = jsonObject.optString("response");
			MyApplication myApplication = MyApplication.getInstance();
			if(code == 1000){
				JSONObject responseObject = new JSONObject(response);
				String customer_id = responseObject.optString("customer_id");
				myApplication.setUserId(customer_id);
				myApplication.setPassword(responseObject.optString("password"));
				myApplication.setVip(responseObject.optString("customer_grade"));
				myApplication.setNick(responseObject.optString("customer_nick"));
				String imgUrl = responseObject.optString("avatar_path");
				myApplication.setUserHeadImg(ImageUrl.IMAGEURL + imgUrl + "!100");
				String token = responseObject.optString("token");
				myApplication.setToken(token);
				message = myApplication.getResources().getString(R.string.login_success);
				myApplication.setIsLogin(true);
				return true;
			} else{
				message = jsonObject.optString("msg");
				isSuccess = false;
			}
		} catch (JSONException e) {
			Log.e(TAG, "login json ex");
			e.printStackTrace();
			isSuccess = false;
		}
		return isSuccess;
	}
	
	private String message;
	
	public String getMsg(){
		return message;
	}
}
