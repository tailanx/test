package com.yidejia.app.mall.qiandao;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.yidejia.app.mall.BaseActivity;
import com.yidejia.app.mall.MyApplication;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.util.HttpClientUtil;
import com.yidejia.app.mall.util.IHttpResp;

public class QiandaoActivity extends BaseActivity implements OnClickListener {
	
	private ImageView backImagView;
	private ImageView ivSignAdd;
	private TextView tvSignCount;
	
	private String userId;
	private String token;
	private JNICallBack jniCallBack;
	private String url;
	
	private int lxSign = 0;	//已连续签到天数
	private int signCount = 0;	//总签到天数
	private String respMsg;	//签到返回信息

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.qiandao);
		backImagView = (ImageView) findViewById(R.id.iv_qiandao_back);
		backImagView.setOnClickListener(this);
		
		ivSignAdd = (ImageView) findViewById(R.id.iv_qiandao_button);
		ivSignAdd.setOnClickListener(this);
		
		tvSignCount = (TextView) findViewById(R.id.tv_sign_count);
		
		userId = MyApplication.getInstance().getUserId();
		token = MyApplication.getInstance().getToken();
		jniCallBack = new JNICallBack();
		url = jniCallBack.HTTPURL;
		
		getSignCount();
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_qiandao_back:
			this.finish();
			break;
		case R.id.iv_qiandao_button:
			signUp();
			break;
		default:
			break;
		}
	}

	private void getSignCount(){
		String param = jniCallBack.getHttp4SignCount(userId, token);
		Log.e("system.out", url);
		HttpClientUtil httpClientUtil = new HttpClientUtil();
		httpClientUtil.getHttpResp(url, param, new IHttpResp() {
			
			@Override
			public void success(String content) {
				// TODO Auto-generated method stub
				Log.e("system.out", content);
				if(parseGetCount(content)){
					tvSignCount.setText(signCount + "");
				}
			}
		});
	}
	
	/**解析总签到天数和连续签到天数**/
	private boolean parseGetCount(String content){
		try {
			JSONObject httpObject = new JSONObject(content);
			int code = httpObject.optInt("code");
			String strResp = httpObject.optString("response");
			if(1 == code){
				JSONObject respObject = new JSONObject(strResp);
				lxSign = respObject.optInt("lxSign");
				signCount = respObject.optInt("countSign");
				return true;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private void signUp(){
		String param = new JNICallBack().getHttp4SignUp(userId, token);
		Log.e("system.out", url);
		
		HttpClientUtil httpClientUtil = new HttpClientUtil();
		httpClientUtil.getHttpResp(url, param, new IHttpResp() {
			
			@Override
			public void success(String content) {
				// TODO Auto-generated method stub
				Log.e("system.out", content);
				String showMsg ;
				if(parseSignAdd(content)){
					showMsg = "获得" + respMsg + "个爱豆";
				} else {
					showMsg = respMsg;
				}
				Toast.makeText(QiandaoActivity.this, showMsg, Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	private boolean parseSignAdd(String content){
		try {
			JSONObject httpObject = new JSONObject(content);
			int code = httpObject.optInt("code");
			respMsg = httpObject.optString("response");
			if(1 == code){
				return true;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		StatService.onPageStart(this, "签到页面");
	}

	@Override
	protected void onPause() {
		super.onPause();
		StatService.onPageEnd(this, "签到页面");
	}
}
