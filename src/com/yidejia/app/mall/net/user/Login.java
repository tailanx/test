package com.yidejia.app.mall.net.user;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.net.HttpPostConn;
import com.yidejia.app.mall.net.ImageUrl;

public class Login {
	
	private String TAG = Login.class.getName();
	
	public String getHttpResponse(String username, String password, String ip) throws IOException{
		HttpPostConn conn = new HttpPostConn(new JNICallBack().getHttp4Login(username, password, ip));
		return conn.getHttpResponse();
	}
	
	public boolean analysisHttpResp(Context context, String httpResp){
		boolean isSuccess = false;
		try {
			int code;
			JSONObject jsonObject = new JSONObject(httpResp);
			code = jsonObject.getInt("code");
			String response = jsonObject.getString("response");
			if(code == 1000){
				MyApplication myApplication = (MyApplication) context.getApplicationContext();
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
	
	private String message;
	
	public String getMsg(){
		return message;
	}
}
