package com.yidejia.app.mall.qiandao;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
//import android.util.Log;
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
import com.yidejia.app.mall.util.Consts;
import com.yidejia.app.mall.util.HttpClientUtil;
import com.yidejia.app.mall.util.IHttpResp;
import com.yidejia.app.mall.util.SharedPreferencesUtil;

public class QiandaoActivity extends BaseActivity implements OnClickListener,
		Callback {

	private ImageView backImagView;
	private ImageView ivSignAdd;
	private TextView tvSignCount1;// 显示天数的个位数字
	private TextView tvSignCount2;// 显示天数的十位数字
	private TextView tvSignCount3;// 显示天数的百位数字

	private String userId;
	private String token;
	private JNICallBack jniCallBack;
	private String url;

	private int lxSign = 0; // 已连续签到天数
	private int signCount = 0; // 总签到天数
	private String respMsg; // 签到返回信息
	private SharedPreferencesUtil sp;// 保存日期
	private SimpleDateFormat sdf;// 日期转换的格式
	private Calendar cal;// 获取日期对象
	private String nowDate;// 当前日期
	private String lastDate;// 保存的日期
	private String lastId;// 保存的用户的Id
	private Handler handler;// 用来更新界面
	
	private boolean isCanSign = true;	//是否可以点击签到

	@SuppressLint("SimpleDateFormat")
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.qiandao);
		handler = new Handler(this);
		backImagView = (ImageView) findViewById(R.id.iv_qiandao_back);
		backImagView.setOnClickListener(this);

		ivSignAdd = (ImageView) findViewById(R.id.iv_qiandao_button);
		ivSignAdd.setOnClickListener(this);

		tvSignCount1 = (TextView) findViewById(R.id.tv_sign_count1);
		tvSignCount2 = (TextView) findViewById(R.id.tv_sign_count2);
		tvSignCount3 = (TextView) findViewById(R.id.tv_sign_count3);

		userId = MyApplication.getInstance().getUserId();
		token = MyApplication.getInstance().getToken();
		jniCallBack = new JNICallBack();
		url = jniCallBack.HTTPURL;

		getSignCount();

		sp = new SharedPreferencesUtil(this);
		// MyApplication.getInstance().getSharedPreferences("TIME",MODE_PRIVATE);
		sdf = new SimpleDateFormat("yyyy-MM-dd");// 日期转换的格式
		cal = Calendar.getInstance();// 获取日期对象
		cal.add(Calendar.DAY_OF_MONTH, -1);
		nowDate = sdf.format(cal.getTime());
		lastDate = sp.getData("TIME", "Date", "");
		lastId = sp.getData("TIME", "User", "");
		if (nowDate.equals(lastDate) && lastId.equals(userId)) {
			ivSignAdd.setImageResource(R.drawable.yiqiandao);
			isCanSign = false;
		} else {
			ivSignAdd.setImageResource(R.drawable.qiandao_button);
			isCanSign = true;
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_qiandao_back:
			this.finish();
			break;
		case R.id.iv_qiandao_button:
			if(!isCanSign) return;
			signUp();
			break;
		default:
			break;
		}
	}

	private void getSignCount() {
		String param = jniCallBack.getHttp4SignCount(userId, token);
		HttpClientUtil httpClientUtil = new HttpClientUtil(this);
		httpClientUtil.setIsShowLoading(true);
		httpClientUtil.setShowErrMessage(true);
		httpClientUtil.getHttpResp(url, param, new IHttpResp() {

			@Override
			public void onSuccess(String content) {
				if (parseGetCount(content)) {
					if (!"".equals(signCount)) {
						int gewei = signCount % 10;
						int shiwei = signCount / 10 % 10;
						int baiwei = signCount / 100;
						tvSignCount3.setText(gewei + "");
						tvSignCount2.setText(shiwei + "");
						tvSignCount1.setText(baiwei + "");
					}
				}
			}
		});
	}

	/** 解析总签到天数和连续签到天数 **/
	private boolean parseGetCount(String content) {
		try {
			JSONObject httpObject = new JSONObject(content);
			int code = httpObject.optInt("code");
			String strResp = httpObject.optString("response");
			if (1 == code) {
				JSONObject respObject = new JSONObject(strResp);
				lxSign = respObject.optInt("lxSign");
				signCount = respObject.optInt("countSign") ;
				return true;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}

	private void signUp() {

		cal.add(Calendar.DAY_OF_MONTH, 0);// 保存当天的数据
		sp.saveData("TIME", "Date", sdf.format(cal.getTime()));
		sp.saveData("TIME", "User", userId);
		String param = new JNICallBack().getHttp4SignUp(userId, token);

		HttpClientUtil httpClientUtil = new HttpClientUtil(this);
		httpClientUtil.setIsShowLoading(true);
		httpClientUtil.setShowErrMessage(true);
		httpClientUtil.getHttpResp(url, param, new IHttpResp() {

			@Override
			public void onSuccess(String content) {
				String showMsg;
//				Log.e("info", content);
				if (parseSignAdd(content)) {
					showMsg = "您已连续签到"+ (lxSign + 1) + "天，本次签到获得" + respMsg + "个爱豆";
					ivSignAdd.setImageResource(R.drawable.yiqiandao);
					signCount++;
					handler.sendEmptyMessage(Consts.QIANDAO);
					isCanSign = false;
				} else {
					showMsg = respMsg;
				}
				Toast.makeText(QiandaoActivity.this, showMsg,
						Toast.LENGTH_LONG).show();
			}
		});
	}

	private boolean parseSignAdd(String content) {
		try {
			JSONObject httpObject = new JSONObject(content);
			int code = httpObject.optInt("code");
			respMsg = httpObject.optString("response");
			if (1 == code) {
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

	@Override
	public boolean handleMessage(Message msg) {
		if (msg.what == Consts.QIANDAO) {
			int gewei = signCount % 10;
			int shiwei = signCount / 10 % 10;
			int baiwei = signCount / 100;
			tvSignCount3.setText(gewei + "");
			tvSignCount2.setText(shiwei + "");
			tvSignCount1.setText(baiwei + "");
		}
		return false;
	}
}
