package com.yidejia.app.mall.datamanage;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.net.ConnectionDetector;
import com.yidejia.app.mall.net.ImageUrl;
import com.yidejia.app.mall.net.user.Login;
import com.yidejia.app.mall.net.user.Register;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class UserDatamanage {
	
	private String TAG = UserDatamanage.class.getName();
	
	private String username;
	private String password;
	private String ip;
	private String cps;
	
	private String message;
	private boolean isSuccess = true;
	
	private Context context;
	public UserDatamanage(Context context){
		this.context = context;
	}
	/**
	 * 登录
	 * @param username
	 * @param password
	 * @param ip
	 * @return
	 */
	public boolean userLogin(String username, String password, String ip){
		this.username = username;
		this.password = password;
		this.ip = ip;
		
		boolean state = false;
		if(!ConnectionDetector.isConnectingToInternet(context)) {
			Toast.makeText(context, context.getResources().getString(R.string.no_network), Toast.LENGTH_LONG).show();
			return state;
		}
		TaskLogin taskLogin = new TaskLogin();
		try {
			state = taskLogin.execute().get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e){
			Log.e(TAG, "login other ex");
			e.printStackTrace();
		} 
		if(!isSuccess){
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
		}
		return state;
	}
	
	private class TaskLogin extends AsyncTask<Void, Void, Boolean>{

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			Login login = new Login();
			try {
				String httpResponse = login.getHttpResponse(username, password, ip);
				try {
					int code;
					JSONObject jsonObject = new JSONObject(httpResponse);
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
						myApplication.setUserHeadImg(ImageUrl.IMAGEURL + imgUrl);
						String token = responseObject.getString("token");
						myApplication.setToken(token);
						message = context.getResources().getString(R.string.login_success);
						return true;
					} if(code == 1001 || code ==1002){
						message = context.getResources().getString(R.string.login_error);
//						throw new UserLoginEx(message);
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
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.e(TAG, "login io ex");
				e.printStackTrace();
				isSuccess = false;
			}
			return isSuccess;
		}
		
	}
	
	public boolean register(String username, String password, String cps, String ip){
		this.username = username;
		this.password = password;
		this.cps = cps;
		this.ip = ip;
		
		boolean state = false;
		if(!ConnectionDetector.isConnectingToInternet(context)) {
			Toast.makeText(context, context.getResources().getString(R.string.no_network), Toast.LENGTH_LONG).show();
			return state;
		}
		TaskRegister taskRegister = new TaskRegister();
		try {
			state = taskRegister.execute().get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e){
			Log.e(TAG, "login other ex");
			e.printStackTrace();
		} 
		if(!isSuccess){
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
		}
		return state;
	}
	
	private class TaskRegister extends AsyncTask<Void, Void, Boolean>{

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			Register register = new Register();
			try {
				String httpResponse = register.getHttpResponse(username, password, cps, ip);
				try {
					int code;
					JSONObject jsonObject = new JSONObject(httpResponse);
					code = jsonObject.getInt("code");
//					String response = jsonObject.getString("response");
					if(code == 1003){
//						MyApplication myApplication = conte;
//						String token = responseObject.getString("token");
//						myApplication.setToken(token);
						return true;
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
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.e(TAG, "login io ex");
				e.printStackTrace();
				isSuccess = false;
			}
			return isSuccess;
		}
		
	}
}