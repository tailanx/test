package com.yidejia.app.mall.log;

import java.io.IOException;

import com.yidejia.app.mall.HomeMyMallActivity;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.ctrl.IpAddress;
import com.yidejia.app.mall.exception.TimeOutEx;
import com.yidejia.app.mall.net.user.Login;
import com.yidejia.app.mall.util.Consts;
import com.yidejia.app.mall.util.DesUtils;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class LogService extends Service {
	private SharedPreferences sp;
	private Consts consts;
	private LoginTask task;
	private IpAddress ipAddress;
	private Login login;
	private String baseName;
	private String basepasswrod;
	private boolean isTimeout;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

		consts = new Consts();
		sp = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		ipAddress = new IpAddress();
		login = new Login();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		baseName = sp.getString("DESMI", null);
		//
		String basePwd = sp.getString("DESPWD", null);
		String keyName = baseName + consts.getMiStr();
		basepasswrod = DesUtils.decode(keyName, basePwd);
		Task task = new Task();
		task.execute();
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		stopSelf();
	}

	private class Task extends AsyncTask<Void, Void, Boolean> {
		// private ProgressDialog bar;

		@Override
		protected Boolean doInBackground(Void... params) {
			Login login = new Login();
			try {
				String httpresp;
				try {
					httpresp = login.getHttpResponse(baseName, basepasswrod, ipAddress.getIpAddress());
					boolean issuccess = login.analysisHttpResp(getApplicationContext(),
							httpresp);
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

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			// bar.dismiss();
			if (result) {
				stopSelf();

			} else {
				if (isTimeout) {
					Toast.makeText(
							getApplicationContext(),
							getApplicationContext().getResources().getString(R.string.time_out),
							Toast.LENGTH_SHORT).show();
					isTimeout = false;
					return;
				}

			}
		}
	}
}
