package com.yidejia.app.mall.task;

import java.io.IOException;

import com.yidejia.app.mall.R;
import com.yidejia.app.mall.exception.TimeOutEx;
import com.yidejia.app.mall.net.user.ResetPsw;
import com.yidejia.app.mall.widget.YLProgressDialog;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.widget.Toast;

public class TaskReset {
	
	private String name;
	private String psw;
	private String code;
	
	private String msg;
	
	private Activity activity;
	private Task task;
	
	public TaskReset(Activity activity){
		this.activity = activity;
	}
	
	
	public void resetPsw(String name, String psw, String code){
		this.name = name;

		this.psw = psw;
		this.code = code;
		
		closeTask();
		task = new Task();
		task.execute();
	}
	
	private boolean isTimeout = false;
	
	private class Task extends AsyncTask<Void, Void, Boolean>{
		private ProgressDialog bar;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
//			bar = new ProgressDialog(activity);
//			bar.setMessage(activity.getResources().getString(R.string.loading));
//			bar.setCancelable(true);
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
			bar.dismiss();
			if(!result){
				Toast.makeText(activity, "重置密码失败!", Toast.LENGTH_LONG).show();
			} else{//
				if (isTimeout) {
					Toast.makeText(
							activity,
							activity.getResources()
									.getString(R.string.time_out),
							Toast.LENGTH_SHORT).show();
					isTimeout = false;
				}
				Toast.makeText(activity, "重置密码成功!", Toast.LENGTH_LONG).show();
				activity.finish();
			}
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			ResetPsw resetPsw = new ResetPsw();
			try {
				String httpresp;
				try {
					httpresp = resetPsw.getHttpResponse(name, psw, code);
					boolean issuccess = resetPsw.analysisHttp(httpresp);
					msg = resetPsw.getMsg();
					return issuccess;
				} catch (TimeOutEx e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					isTimeout = true;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return false;
		}
		
	}
	
	
	public void closeTask(){
		if(task != null && task.getStatus().RUNNING == AsyncTask.Status.RUNNING){
			task.cancel(true);
		}
	}
}
