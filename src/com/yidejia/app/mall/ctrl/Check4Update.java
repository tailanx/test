package com.yidejia.app.mall.ctrl;

import com.yidejia.app.mall.net.HttpGetVersionConn;
import com.yidejia.app.mall.widget.YLProgressDialog;

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
import android.text.TextUtils;
import android.widget.Toast;

/**
 * 
 * 检查更新控制类
 * 
 * @author LongBin
 * 
 */
public class Check4Update {
	private String versionUrl = "http://dl.yidejia.com/yidejia.txt";
	private String apkUrl = "http://dl.yidejia.com/yidejia.apk";
	private String version;
	private String title;
	private String message;

	private Activity activity;
	private ProgressDialog bar;

	public Check4Update(Activity activity) {
		this.activity = activity;
	}

	/**
	 * 获取当前版本
	 * 
	 * @return 当前版本号,如1.0；1.1；2.0...
	 */
	public String getVersionName() {
		String versionName = "";
		try {
			PackageInfo packInfo = activity.getApplication()
					.getPackageManager()
					.getPackageInfo(activity.getPackageName(), 0);
			versionName = packInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionName;
	}

	private class GetVersionTask extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected void onPreExecute() {
			bar = (ProgressDialog) new YLProgressDialog(activity)
					.createLoadingDialog(activity, null);
			super.onPreExecute();
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			boolean state = true;
			HttpGetVersionConn conn = new HttpGetVersionConn(versionUrl);
			try {
				String result = conn.getVersionString();
				String[] resultArray = result.split("#");
				version = resultArray[0];
				if (!getVersionName().equals(version)) {
					title = "发现新版本!(" + version + ")";
					if (resultArray.length != 1) {
						for (int i = 0; i < resultArray.length; i++) {
							message += resultArray[i];
							if(i == resultArray.length) {
								message += "\n";
							}
						}
					}
					state = true;
				} else {
					title = "提示";
					message = "已是最新版本，不用更新！";
					state = false;
				}
			} catch (Exception e) {
				state = false;
				title = "网络连接错误！";
				message = "";
			}

			return state;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			if (result && !TextUtils.isEmpty(message)) {
				showUpdateDialog(title, "当前版本:" + getVersionName() + "\n"
						+ message);
			} else {
				Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
			}
			bar.dismiss();
			super.onPostExecute(result);
		}

	}

	/**显示更新提示**/
	private void showUpdateDialog(String title, String message) {

		Dialog dialog = new AlertDialog.Builder(activity)
				.setTitle(title)
				.setMessage(message)
				.setPositiveButton("现在升级",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								Intent intent = new Intent();
								intent.setAction("android.intent.action.VIEW");
								Uri content_url = Uri.parse(apkUrl);
								intent.setData(content_url);
								activity.startActivity(intent);
							}
						}).setNegativeButton("下次再说", null).create();
		dialog.show();

	}

	private GetVersionTask task;

	public void closeTask() {
		if (task != null && Status.RUNNING == task.getStatus().RUNNING) {
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
				closeTask();
			}
		});
	}

}
