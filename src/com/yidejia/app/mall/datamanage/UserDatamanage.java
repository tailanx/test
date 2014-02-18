package com.yidejia.app.mall.datamanage;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.yidejia.app.mall.R;
import com.yidejia.app.mall.exception.TimeOutEx;
import com.yidejia.app.mall.net.ConnectionDetector;
import com.yidejia.app.mall.net.user.Login;
import com.yidejia.app.mall.net.user.Register;
import com.yidejia.app.mall.widget.YLProgressDialog;

public class UserDatamanage {
	
	private String TAG = UserDatamanage.class.getName();
	
	private String username;
	private String password;
	private String ip;
	private String cps;
	
	private String message;
	private boolean isSuccess = true;
	
	private Activity context;
	public UserDatamanage(Activity context){
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
			boolean issuccess = false;
			try {
				String httpResponse = login.getHttpResponse(username, password, ip);
				issuccess = login.parseLogin(httpResponse);
				message = login.getMsg();
				return issuccess;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.e(TAG, "login io ex");
				e.printStackTrace();
				issuccess = false;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				issuccess = false;
			}
			return issuccess;
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
		taskRegister.execute();
//		try {
//			state = taskRegister.execute().get();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ExecutionException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (Exception e){
//			Log.e(TAG, "login other ex");
//			e.printStackTrace();
//		} 
		if(!isSuccess){
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
		}
		return state;
	}
	
	private boolean isTimeout = false;
	
	private class TaskRegister extends AsyncTask<Void, Void, Boolean>{

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			Register register = new Register();
			try {
				String httpResponse;
				try {
					httpResponse = register.getHttpResponse(username, password, cps, ip);
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
				} catch (TimeOutEx e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			return isSuccess;
		}
		
		private ProgressDialog bar;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			
//			bar = new ProgressDialog(context);
//			bar.setCancelable(true);
//			bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//			bar.setMessage(context.getResources().getString(R.string.loading));
//			bar.show();
			bar = (ProgressDialog) new YLProgressDialog(context)
					.createLoadingDialog(context, null);
			bar.setOnCancelListener(new DialogInterface.OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					// TODO Auto-generated method stub
					cancel(true);
				}
			});
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			bar.dismiss();
			if (result) {
				Toast.makeText(context,
						context.getResources().getString(R.string.equal),
						Toast.LENGTH_LONG).show();
				context.finish();
			} else {
				if (isTimeout) {
					Toast.makeText(
							context,
							context.getResources()
									.getString(R.string.time_out),
							Toast.LENGTH_SHORT).show();
					isTimeout = false;
				}
			}
		}
		
		
		
	}
}