package com.yidejia.app.mall.task;

import java.io.IOException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.widget.Toast;

import com.yidejia.app.mall.R;
import com.yidejia.app.mall.exception.TimeOutEx;
import com.yidejia.app.mall.net.user.CheckCode;
import com.yidejia.app.mall.widget.YLProgressDialog;

public class TaskCheckCode {
	
	private String name;
	private String msgCode;
	private String password;
	private String ip;
	private String cps;
	
	private Activity activity;
	private Task task ;
	private TaskRegister taskRegister;
	
	public TaskCheckCode(Activity activity){
		this.activity = activity;
	}
	
	public void checkCode(String name, String msgCode, String password, String ip, String cps){
		this.name = name;
		this.msgCode = msgCode;
		this.password = password;
		this.ip = ip;
		this.cps = cps;
		
		if(task != null && task.getStatus().RUNNING == AsyncTask.Status.RUNNING){
			task.cancel(true);
		}
		
		task = new Task();
		task.execute();
	}

	private boolean isTimeOut = false;
	
	private class Task extends AsyncTask<Void, Void, Boolean>{
		private ProgressDialog bar;
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
			if(!result){
				if(isTimeOut) {
					bar.dismiss();
					Toast.makeText(activity, activity.getResources().getString(R.string.time_out), Toast.LENGTH_LONG).show();
					isTimeOut = false;
					return;
				}
				Toast.makeText(activity, "验证码验证失败!", Toast.LENGTH_LONG).show();
				bar.dismiss();
			} else{
				taskRegister = new TaskRegister(activity, bar);
				taskRegister.register(name, password, ip, cps);
			}
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			CheckCode checkCode = new CheckCode();
			try {
				String httpResp;
				try {
					httpResp = checkCode.getHttpResp(name, msgCode);
					boolean isSuccess = checkCode.analysisResp(httpResp);
					return isSuccess;
				} catch (TimeOutEx e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					isTimeOut = true;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
		
		
	}
	
	public void closeTask(){
		if(task != null && task.getStatus().RUNNING == AsyncTask.Status.RUNNING){
			task.cancel(true);
		}
		if(taskRegister != null) taskRegister.closeTask();
	}
}
