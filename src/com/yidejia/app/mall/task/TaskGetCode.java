package com.yidejia.app.mall.task;

import java.io.IOException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.yidejia.app.mall.R;
import com.yidejia.app.mall.exception.TimeOutEx;
import com.yidejia.app.mall.net.user.GetCode;
import com.yidejia.app.mall.widget.YLProgressDialog;

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
	
	private boolean isTimeout = false;
	
	private class Task extends AsyncTask<Void, Void, Boolean>{

		private ProgressDialog bar;
		
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			GetCode getCode = new GetCode();
			try {
				String httpResp;
				try {
					httpResp = getCode.getHttpResp(name);
					boolean isSuccess = getCode.analysisGetCode(httpResp);
					regCode = getCode.getCode();
					return isSuccess;
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
				TaskSendCode taskSendCode = new TaskSendCode(activity, bar);
				taskSendCode.sendMsg(name, regCode + "");
			} else {
				if (isTimeout) {
					Toast.makeText(
							activity,
							activity.getResources()
									.getString(R.string.time_out),
							Toast.LENGTH_SHORT).show();
					isTimeout = false;
				}
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
