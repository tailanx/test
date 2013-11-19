package com.yidejia.app.mall.task;

import java.io.IOException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.yidejia.app.mall.net.user.GetCode;

public class TaskGetCode {
	
	private Activity activity;
	
	private String name;
	
	private Task task;
	private TaskSendCode taskSendCode;
	
	public TaskGetCode(Activity activity){
		this.activity = activity;
	}
	
	public void getCode(String name){
		this.name = name;
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
			GetCode getCode = new GetCode();
			try {
				String httpResp = getCode.getHttpResp(name);
				boolean isSuccess = getCode.analysisGetCode(httpResp);
				regCode = getCode.getCode();
				return isSuccess;
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
				TaskSendCode taskSendCode = new TaskSendCode(activity, bar);
				taskSendCode.sendMsg(name, regCode + "");
			} else {
				bar.dismiss();
			}
		}
		
	}
	
	
	private int regCode;
	
	public int getRegCode(){
		return regCode;
	}
	
	public void closeTask(){
		if(task != null && task.getStatus().RUNNING == AsyncTask.Status.RUNNING){
			task.cancel(true);
		}
		
		if(taskSendCode != null) {
			taskSendCode.closeTask();
		}
	}
	
}
