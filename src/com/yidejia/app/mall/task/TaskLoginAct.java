package com.yidejia.app.mall.task;

import java.io.IOException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.CheckBox;
import android.widget.Toast;

import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.exception.TimeOutEx;
import com.yidejia.app.mall.net.user.Login;
import com.yidejia.app.mall.util.Consts;
import com.yidejia.app.mall.util.DesUtils;
import com.yidejia.app.mall.widget.WiperSwitch;
import com.yidejia.app.mall.widget.YLProgressDialog;

public class TaskLoginAct {
	
	private String username , password, ip;
	private WiperSwitch mBox;
	private String message;
	private SharedPreferences sp ;
	private Consts consts;
	
	private Task task;
	
	private Activity activity;
	
	public TaskLoginAct(Activity activity){
		this.activity = activity;
	}
	
	public void loginAct(String username, String password, String ip,WiperSwitch mBox,SharedPreferences sp ,Consts consts){
		this.username = username;
		this.sp = sp;
		this.consts = consts;
		this.password = password;
		this.ip = ip;
		this.mBox = mBox;
		if(task != null && task.getStatus().RUNNING == AsyncTask.Status.RUNNING){
			task.cancel(true);
		}
		
		task = new Task();
		task.execute();
	}
	
	private boolean isTimeout = false;
	
	private class Task extends AsyncTask<Void, Void, Boolean>{
		private ProgressDialog bar;

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			Login login = new Login();
			try {
				String httpresp;
				try {
					httpresp = login.getHttpResponse(username, password, ip);
					boolean issuccess = login.parseLogin(httpresp);
					message = login.getMsg();
					return issuccess;
				} catch (TimeOutEx e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					isTimeout = true;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
//			bar = new ProgressDialog(activity);
//			bar.setCancelable(true);
//			bar.setMessage(activity.getResources().getString(R.string.loading));
//			bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//			bar.show();
			bar = (ProgressDialog) new YLProgressDialog(activity)
					.createLoadingDialog(activity, null);
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
			if(result){
				Toast.makeText(activity, "登陆成功！", Toast.LENGTH_LONG).show();
				((MyApplication) activity.getApplication()).setIsLogin(true);
				Intent intent = new Intent(Consts.LONGIN_SUCESSES);
				activity.sendBroadcast(intent);
				activity.finish();
				if (mBox.isSelected()) {
					 sp.edit().putString("DESMI", username).commit();
					
					 String keyName = username+consts.getMiStr();
					
					try {
						String demi = DesUtils.encode(keyName,password);
						sp.edit().putString("DESPWD", demi).commit();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 
				
					
					 }else{
						 sp.edit().remove("DESMI").commit();
						 sp.edit().remove("DESPWD").commit();
					 }
				
			} else {
				if (isTimeout) {
					Toast.makeText(
							activity,
							activity.getResources()
									.getString(R.string.time_out),
							Toast.LENGTH_SHORT).show();
					isTimeout = false;
//					return;
				}
				else Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
			}
			bar.dismiss();
		}
		
		
	}
	
	
	public void closeTask(){
		if(task != null && task.getStatus().RUNNING == AsyncTask.Status.RUNNING){
			task.cancel(true);
		}
	}
}
