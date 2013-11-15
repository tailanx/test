package com.yidejia.app.mall.task;

import java.io.IOException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.net.user.Login;

public class TaskLoginAct {
	
	private String username , password, ip;
	private String message;
	
	private Task task;
	
	private Activity activity;
	
	public TaskLoginAct(Activity activity){
		this.activity = activity;
		
	}
	
	public void loginAct(String username, String password, String ip){
		this.username = username;
		this.password = password;
		this.ip = ip;
		
		if(task != null && task.getStatus().RUNNING == AsyncTask.Status.RUNNING){
			task.cancel(true);
		}
		
		task = new Task();
		task.execute();
	}
	
	
	private class Task extends AsyncTask<Void, Void, Boolean>{
		private ProgressDialog bar;

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			Login login = new Login();
			try {
				String httpresp = login.getHttpResponse(username, password, ip);
				boolean issuccess = login.analysisHttpResp(activity, httpresp);
				message = login.getMsg();
				return issuccess;
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
			bar = new ProgressDialog(activity);
			bar.setCancelable(false);
			bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			bar.show();
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result){
				Toast.makeText(activity, "登陆成功！", Toast.LENGTH_LONG).show();
				((MyApplication) activity.getApplication()).setIsLogin(true);
				activity.finish();
			} else {
				Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
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
