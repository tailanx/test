package com.yidejia.app.mall.ctrl;

import com.yidejia.app.mall.net.HttpGetVersionConn;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.widget.Toast;


public class Check4Update {
	private String versionUrl = "http://static.n8n8.cn/apk/version.txt";
	private String apkUrl = "http://static.n8n8.cn/apk/neican.apk";
	private String version;
	private String title;
	private String message;
	
	private Activity activity;
	private ProgressDialog bar;
	
	public Check4Update(Activity activity){
		this.activity = activity;
		bar = new ProgressDialog(activity);
		bar.setCancelable(true);
		bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	}
	
	private String getVersionName(){
		String versionName = "";
		//int versionCode = 1;
		try {
			PackageInfo packInfo = activity.getApplication().getPackageManager().getPackageInfo(activity.getPackageName(), 0);
			versionName = packInfo.versionName;
			//versionCode = packInfo.versionCode;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return versionName;
	}
	
	private class GetVersionTask extends AsyncTask<Void, Void, Boolean> {

//		private ProgressDialog pd = null;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
//			pd = ProgressDialog.show(WelcomeActivity.this, "", "正在检查更新...",
//					true);
			bar.show();
			super.onPreExecute();
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			boolean state = true;
			HttpGetVersionConn conn = new HttpGetVersionConn(versionUrl);
			try {
				String result = conn.getVersionString();
				String[] resultArray = result.split("#");
				version = resultArray[0];
				if (!getVersionName().equals(version)) {
					// showDialog();
					// upgrade = true;
					title = "发现新版本("+version+")！";
					if(resultArray.length != 1)
					message = resultArray[1];
					// message = "11331";
					state = true;
					// showUpdateDialog(title, message);
				} else {
					title = "提示";
					message = "已是最新版本，不用更新！";
					state = false;
				}
			} catch (Exception e) {
				// TODO: handle exception
				state = false;
				title = "网络连接错误！";
				message = "";
			}

			return state;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			if (result) {
				showUpdateDialog(title, "当前版本:"+getVersionName()+"\n"+message);
			} else {
				Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
			}
			bar.dismiss();
			super.onPostExecute(result);
		}

	}

	private void showUpdateDialog(String title, String message) {

		Dialog dialog = new AlertDialog.Builder(activity)
				.setTitle(title)
				.setMessage(message)
				.setPositiveButton("现在升级",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								Intent intent = new Intent();
								intent.setAction("android.intent.action.VIEW");
								Uri content_url = Uri.parse(apkUrl);
								intent.setData(content_url);
								activity.startActivity(intent);
						
							}
						})
				.setNegativeButton("下次再说", null).create();
		dialog.show();

	}
	
	private GetVersionTask task;
	
	public void closeTask(){
		if(task != null && Status.RUNNING == task.getStatus().RUNNING){
			task.cancel(true);
		}
	}

	public void checkUpdate() {
		closeTask();
		task = new GetVersionTask();
		task.execute();
		
		bar.setOnCancelListener(new DialogInterface.OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				// TODO Auto-generated method stub
				closeTask();
			}
		});
	}


}
