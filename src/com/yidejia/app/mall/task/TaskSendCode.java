package com.yidejia.app.mall.task;

import java.io.IOException;

import com.yidejia.app.mall.net.user.SendMsg;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

public class TaskSendCode {

	private Task task;
	
	private String phone, code;

	private Activity activity;
	private ProgressDialog bar;

	public TaskSendCode(Activity activity, ProgressDialog bar) {
		this.activity = activity;
		this.bar = bar;
	}

	public void sendMsg(String phone, String code) {
		this.phone = phone;
		this.code = code;
		
		if(task != null && task.getStatus().RUNNING == AsyncTask.Status.RUNNING){
			task.cancel(true);
		}
		
		task = new Task();
		task.execute();
	}

	private class Task extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			SendMsg sendMsg = new SendMsg();
			try {
				String httpResp = sendMsg.getHttpResp(phone, code);
				boolean isSuccess = sendMsg.analysisHttpResp(httpResp);
				response = sendMsg.getResponse();
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
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			bar.dismiss();
			if(result){
				if(response != -1){
					Toast.makeText(activity, "亲，请在"+response+"秒后重试！", Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(activity, "验证码已发送到您的手机，请注意查看!", Toast.LENGTH_LONG).show();
				}
			}
		}

	}
	
	private int response;
	
	public void closeTask(){
		if(task != null && task.getStatus().RUNNING == AsyncTask.Status.RUNNING){
			task.cancel(true);
		}
	}
}
