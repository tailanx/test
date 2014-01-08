package com.yidejia.app.mall;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.R.layout;
import com.yidejia.app.mall.R.string;
import com.yidejia.app.mall.net.ConnectionDetector;
import com.yidejia.app.mall.net.HttpGetVersionConn;


public class WelcomeActivity extends Activity {
	
//	private Handler mHandler;
	public static Activity ACTIVITY;
	private String versionUrl = "http://dl.yidejia.com/yidejia.txt";
	private String apkUrl = "http://dl.yidejia.com/yidejia.apk";
	
	private String version;
	private String title;
	private String message;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ACTIVITY = this;
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_welcome);
		
//		if((getApplicationInfo().flags &= ApplicationInfo.FLAG_DEBUGGABLE) != 0){
//			android.os.Process.killProcess(android.os.Process.myPid());
//		}
		StatService.setAppChannel(this, "", false);
		StatService.setSessionTimeOut(30);
		StatService.setLogSenderDelayed(0);
		if(ConnectionDetector.isConnectingToInternet(this)){
			checkUpdate();
		} else {
			Toast.makeText(this, getResources().getString(R.string.no_network), Toast.LENGTH_LONG).show();
			go2MainAct();
		}
		
		
	}
	
	
	public void checkUpdate() {
		closeTask();
		task = new GetVersionTask();
		task.execute();
	}
	
	private class GetVersionTask extends AsyncTask<Void, Void, Boolean> {

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
					title = "发现新版本!("+version+")";
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
//				Toast.makeText(WelcomeActivity.this, message, Toast.LENGTH_LONG).show();
				go2MainAct();
			}
			super.onPostExecute(result);
		}

	}

	private void showUpdateDialog(String title, String message) {

		Dialog dialog = new AlertDialog.Builder(WelcomeActivity.this)
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
								WelcomeActivity.this.startActivity(intent);
								
							}
						})
				.setNegativeButton("下次再说", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						go2MainAct();
					}
				}).create();
		dialog.show();

	}
	
	private GetVersionTask task;
	
	public void closeTask(){
		if(task != null && Status.RUNNING == task.getStatus().RUNNING){
			task.cancel(true);
		}
	}
	
	public String getVersionName(){
		String versionName = "";
		try {
			PackageInfo packInfo = WelcomeActivity.this.getApplication().getPackageManager().getPackageInfo(WelcomeActivity.this.getPackageName(), 0);
			versionName = packInfo.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return versionName;
	}
	
	private void go2MainAct() {
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Intent intent = new Intent(WelcomeActivity.this,
						HomeMallActivity.class);
				startActivity(intent);
				WelcomeActivity.this.finish();
			}
		}, 1 * 1500);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		StatService.onResume(this);
	}


	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		StatService.onPause(this);
	}
	
}
