package com.yidejia.app.mall.skintest;

import org.apache.http.HttpClientConnection;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.net.skin.CheckCps;
import com.yidejia.app.mall.util.HttpClientUtil;
import com.yidejia.app.mall.util.IHttpResp;
import com.yidejia.app.mall.widget.YLProgressDialog;

public class SkinHomeActivity extends Activity implements OnClickListener {

	public String getCpsNumber() {
		return cpsNumber;
	}

	public void setCpsNumber(String cpsNumber) {
		this.cpsNumber = cpsNumber;
	}

	private ImageView back;// 返回
	private EditText number;
	private ImageView startSkin;
	private String cpsNumber;

	private void setupShow() {
		back = (ImageView) findViewById(R.id.skin_test_home_back);
		number = (EditText) findViewById(R.id.skin_test_home_number);
		startSkin = (ImageView) findViewById(R.id.skin_test_home_startSkin);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.skin_test_home_pag);
		setupShow();
		back.setOnClickListener(this);
		startSkin.setOnClickListener(this);
		cpsNumber = number.getText().toString();

		// bar = new ProgressDialog(this);
		// bar.setCancelable(true);
		// bar.setMessage(getResources().getString(R.string.loading));
		// bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		//
		// bar.setOnCancelListener(new DialogInterface.OnCancelListener() {
		//
		// @Override
		// public void onCancel(DialogInterface dialog) {
		// // TODO Auto-generated method stub
		// closeTask();
		// }
		// });
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		closeTask();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.skin_test_home_back:
			this.finish();
			break;

		case R.id.skin_test_home_startSkin:
			cpsNumber = number.getText().toString().trim();
			if (null == cpsNumber || "".equals(cpsNumber)) {
				Toast.makeText(this, "服务号不能为空", Toast.LENGTH_LONG).show();
				return;
			}
			getServiceData(cpsNumber);
			break;
		}
	}

	/**
	 * 判断服务号
	 * 
	 * @param number
	 */
	private void getServiceData(String number) {
		String url = new JNICallBack().getHttp4CheckCps(number);
		HttpClientUtil client = new HttpClientUtil();
		client.getHttpResp(url, new IHttpResp() {
			@Override
			public void onSuccess(String content) {
				super.onSuccess(content);
				Log.e("info", content + "");
				if (isParseSucess(content)) {
					Intent intent = new Intent(SkinHomeActivity.this,
							SkinQuesActivity.class);
					intent.putExtra("cps", cpsNumber);
					startActivity(intent);
					SkinHomeActivity.this.finish();
				} else {
					Toast.makeText(SkinHomeActivity.this, "请输入正确的服务号",
							Toast.LENGTH_LONG).show();
				}
			}
		});

	}

	/**
	 * 解析获取的数据
	 * 
	 * @param content
	 */
	private boolean isParseSucess(String content) {
		JSONObject object;
		try {
			object = new JSONObject(content);
			int code = object.optInt("code");
			if (code == 1) {
				return true;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		return false;

	}

	
	
	
	
	
	
	private ProgressDialog bar;
	private TaskCheck taskCheck;

	private class TaskCheck extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				CheckCps checkCps = new CheckCps();
				String httpresp = checkCps.getHttpResp(cpsNumber);
				// String httpresp = "{code:1}";
				boolean issuccess = checkCps.isCpsTrue(httpresp);
				return issuccess;
			} catch (Exception e) {
				// TODO: handle exception
			}
			return false;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			// bar.show();
			bar = (ProgressDialog) new YLProgressDialog(SkinHomeActivity.this)
					.createLoadingDialog(SkinHomeActivity.this, null);
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
			if (result) {
				Intent intent = new Intent(SkinHomeActivity.this,
						SkinQuesActivity.class);
				intent.putExtra("cps", cpsNumber);
				startActivity(intent);
				SkinHomeActivity.this.finish();
			} else {
				Toast.makeText(SkinHomeActivity.this, "请输入正确的服务号",
						Toast.LENGTH_LONG).show();
			}
		}

	}

	private void closeTask() {
		if (taskCheck != null
				&& taskCheck.getStatus().RUNNING == AsyncTask.Status.RUNNING) {
			taskCheck.cancel(true);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		StatService.onPageStart(this, "皮肤测试首页页面");
	}

	@Override
	protected void onPause() {
		super.onPause();
		StatService.onPageEnd(this, "皮肤测试首页页面");
	}
}
