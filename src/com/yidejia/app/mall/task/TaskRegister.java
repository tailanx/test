package com.yidejia.app.mall.task;

import java.io.IOException;

import com.yidejia.app.mall.net.user.Register;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

public class TaskRegister {
	
	
	private String name;
	private String password;
	private String ip;
	private String cps;
	
	private String message;
	
	private Activity activity;
	private ProgressDialog bar;
	private Task task;
	
	public TaskRegister(Activity activity, ProgressDialog bar){
		this.activity = activity;
		this.bar = bar;
	}
	
	public void register(String name, String password, String ip, String cps){
		this.name = name;
		this.password = password;
		this.ip = ip;
		this.cps = cps;
		
		if(task != null && task.getStatus().RUNNING == AsyncTask.Status.RUNNING){
			task.cancel(true);
		}
		
		task = new Task();
		task.execute();
	}
	
	private class Task extends AsyncTask<Void, Void, Boolean>{

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			Register register = new Register();
			try {
				String httpresp = register.getHttpResponse(name, password, cps, ip);
				boolean issuccess = register.analysisHttpResp(httpresp);
				message = register.getMsg();
				return issuccess;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			bar.dismiss();
			if(result){
				Toast.makeText(activity, "注册成功!", Toast.LENGTH_LONG).show();
				activity.finish();
			} else {
				Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
			}
		}
		
	}
	
	public void closeTask(){
		if(task != null && task.getStatus().RUNNING == AsyncTask.Status.RUNNING){
			task.cancel(true);
		}
	}

}
