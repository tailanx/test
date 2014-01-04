package com.yidejia.app.mall.datamanage;

import java.io.IOException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.UserPayActivity;
import com.yidejia.app.mall.exception.TimeOutEx;
import com.yidejia.app.mall.net.order.GetTn;
import com.yidejia.app.mall.widget.YLProgressDialog;

public class TaskGetTn {

	private Activity activity;
	
	private String TAG = TaskGetTn.class.getName();
	
	private Task task ;
	
	private String orderTn;
	private String orderCode;
	private String userid;
	private String token;
	
	
	public TaskGetTn(Activity activity){
		this.activity = activity;
		userid = ((MyApplication)activity.getApplication()).getUserId();
		token = ((MyApplication)activity.getApplication()).getToken();
	}
	
	public void getOrderTn(String orderCode){
		this.orderCode = orderCode;
		
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
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
//			bar = new ProgressDialog(activity);
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
			
			bar.dismiss();

			if(result){
				if("".equals(orderTn) || null == orderTn) {
					Log.e(TAG, "tn is null");
					return;
				}
				Log.e(TAG, "tn is not null");
				Intent intent = new Intent(activity, UserPayActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("mode", 1);
				bundle.putString("tn", orderTn);
				bundle.putString("resp_code", "00");
				bundle.putString("code", orderCode);
				bundle.putString("uid", userid);
				intent.putExtras(bundle);
				activity.startActivity(intent);
				activity.finish();
			} else {
				if (isTimeout) {
					Toast.makeText(
							activity,
							activity.getResources()
									.getString(R.string.time_out),
							Toast.LENGTH_SHORT).show();
					isTimeout = false;
				}
			}
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			GetTn getTn = new GetTn();
			String httpresp = "";
			boolean isSuccess = false;
			try {
				try {
					httpresp = getTn.getTnHttpResp(userid, orderCode, token);
					isSuccess = getTn.analysisHttpJson(httpresp);
					orderTn = getTn.getTn();
				} catch (TimeOutEx e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					isTimeout = true;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return isSuccess;
		}
		
	}
}
