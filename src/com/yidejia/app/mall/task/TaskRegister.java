package com.yidejia.app.mall.task;

import java.io.IOException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.RegistActivity;
import com.yidejia.app.mall.exception.TimeOutEx;
import com.yidejia.app.mall.net.user.Register;

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
	
	private boolean isTimeout = false;
	
	private class Task extends AsyncTask<Void, Void, Boolean>{

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			Register register = new Register();
			try {
				String httpresp;
				try {
					httpresp = register.getHttpResponse(name, password, cps, ip);
					boolean issuccess = register.analysisHttpResp(httpresp);
					message = register.getMsg();
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
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			bar.dismiss();
			if(result){
				StatService.onEventDuration(activity,
						"registered success", "registered success", 100);
				Toast.makeText(activity, "注册成功!", Toast.LENGTH_LONG).show();
				activity.finish();
			} else {
				if (isTimeout) {
					Toast.makeText(
							activity,
							activity.getResources()
									.getString(R.string.time_out),
							Toast.LENGTH_SHORT).show();
					isTimeout = false;
					return;
				}
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
