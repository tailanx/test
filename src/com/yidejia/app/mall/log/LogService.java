package com.yidejia.app.mall.log;


import com.opens.asyncokhttpclient.AsyncHttpResponse;
import com.opens.asyncokhttpclient.AsyncOkHttpClient;
import com.opens.asyncokhttpclient.RequestParams;
import com.yidejia.app.mall.ctrl.IpAddress;
import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.net.user.Login;
import com.yidejia.app.mall.util.Consts;
import com.yidejia.app.mall.util.DesUtils;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;

public class LogService extends Service {
	private SharedPreferences sp;
	private Consts consts;
	// private LoginTask task;
	private IpAddress ipAddress;
	// private Login login;
	private String baseName;
	private String basepasswrod;
	private AsyncOkHttpClient client;
	private RequestParams params;
	private String hosturl;
	private String contentType;
	private Boolean isCheck;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		consts = new Consts();
		sp = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		ipAddress = new IpAddress();
		params = new RequestParams();
		hosturl = new JNICallBack().HTTPURL;
		contentType = "application/x-www-form-urlencoded;charset=UTF-8";
		client = new AsyncOkHttpClient();

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		baseName = sp.getString("DESMI", null);
		//
		String basePwd = sp.getString("DESPWD", null);
		String keyName = baseName + consts.getMiStr();

		basepasswrod = DesUtils.decode(keyName, basePwd);
		isCheck = sp.getBoolean("CHECK", false);
		if (null == basePwd || null == basepasswrod || !isCheck) {
			stopSelf();
		} else {
			String url = new JNICallBack().getHttp4Login(baseName,
					basepasswrod, ipAddress.getIpAddress());
			params.put(url);
			loginService();
		}
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		stopSelf();
	}


	private void loginService() {
		client.post(hosturl, contentType, params, new AsyncHttpResponse() {
			@Override
			public void onStart() {
				super.onStart();
			}

			@Override
			public void onFinish() {
				super.onFinish();
				stopSelf();
			}

			@Override
			public void onSuccess(int statusCode, String content) {
				super.onSuccess(statusCode, content);
				if (null != content && !"".equals(content)) {
					Login login = new Login();
					login.analysisHttpResp(getApplicationContext(), content);
					stopSelf();
				} else {
					stopSelf();
				}

			}

			@Override
			public void onError(Throwable error, String content) {
				super.onError(error, content);
				stopSelf();
			}
		});
	}
}
