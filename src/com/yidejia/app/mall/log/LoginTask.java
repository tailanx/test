package com.yidejia.app.mall.log;

import java.io.IOException;



//import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
//import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
//import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.yidejia.app.mall.HomeLoginActivity;
//import com.yidejia.app.mall.HomeLogActivity;
import com.yidejia.app.mall.HomeMyMallActivity;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.exception.TimeOutEx;
import com.yidejia.app.mall.net.user.Login;
import com.yidejia.app.mall.util.Consts;
import com.yidejia.app.mall.util.DesUtils;
import com.yidejia.app.mall.widget.WiperSwitch;
//import com.yidejia.app.mall.widget.WiperSwitch.OnChangedListener;
import com.yidejia.app.mall.widget.YLProgressDialog;

public class LoginTask{
	private String name;
	private String pwd;
	private String ip;
	private SherlockFragmentActivity context;
	private String message;
	private boolean isTimeout = false;
//	private MyApplication myApplication;
	// private InputMethodManager inputMethodManager;
	private SharedPreferences sp;
//	private WiperSwitch mBox;
	private Consts consts;
//	private boolean isCheck;

	public LoginTask() {

	}

	public LoginTask(SherlockFragmentActivity context, String name, String pwd,
			String ip) {
		this.name = name;
//		this.mBox = mBox;
		consts = new Consts();
		sp = PreferenceManager.getDefaultSharedPreferences(context);
		// inputMethodManager = (InputMethodManager) context
		// .getSystemService(Context.INPUT_METHOD_SERVICE);
//		this.myApplication = (MyApplication) context.getApplication();
		this.context = context;
		this.pwd = pwd;
		this.ip = ip;
		Task task = new Task();
		task.execute();
	}

	private class Task extends AsyncTask<Void, Void, Boolean> {
//		private ProgressDialog bar;

		@Override
		protected Boolean doInBackground(Void... params) {
			Login login = new Login();
			try {
				String httpresp;
				try {
					httpresp = login.getHttpResponse(name, pwd, ip);
					boolean issuccess = login.analysisHttpResp(context,
							httpresp);
					message = login.getMsg();
					return issuccess;
				} catch (TimeOutEx e) {
					e.printStackTrace();
					isTimeout = true;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;
		}

		@SuppressWarnings("static-access")
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
//			bar = (ProgressDialog) new YLProgressDialog(context)
//					.createLoadingDialog(context, null);
//			bar.setOnCancelListener(new DialogInterface.OnCancelListener() {
//
//				@Override
//				public void onCancel(DialogInterface dialog) {
//					cancel(true);
//				}
//			});
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
//			bar.dismiss();
			if (result) {
				
				Toast.makeText(
						context,
						context.getResources()
								.getString(R.string.login_success),
						Toast.LENGTH_LONG).show();
				Intent intent = new Intent(context, HomeMyMallActivity.class);
				intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra("current", 3);
				intent.putExtra("next", 3);
				context.startActivity(intent);
				
				context.finish();
//				context.overridePendingTransition(R.anim.activity_in,
//						R.anim.activity_out);
				// // 隐藏键盘
				// inputMethodManager.hideSoftInputFromWindow(
				// stringName.getWindowToken(), 0);
				// inputMethodManager.hideSoftInputFromWindow(
				// stringPassword.getWindowToken(), 0);

//				myApplication.setIsLogin(true);

					sp.edit().putString("DESMI", name).commit();
					sp.edit().putBoolean("CHECK", true).commit();
					String keyName = name + consts.getMiStr();

					try {
						String demi = DesUtils.encode(keyName, pwd);
						sp.edit().putString("DESPWD", demi).commit();
					} catch (Exception e) {
						e.printStackTrace();
					}

			} else {
				if (isTimeout) {
					Toast.makeText(
							context,
							context.getResources().getString(R.string.time_out),
							Toast.LENGTH_SHORT).show();
					isTimeout = false;
					return;
				}
				Toast.makeText(context, message, Toast.LENGTH_LONG).show();
				sp.edit().remove("CHECK").commit();
				sp.edit().remove("DESMI").commit();
				sp.edit().remove("DESPWD").commit();
			}
		}
	}
}
