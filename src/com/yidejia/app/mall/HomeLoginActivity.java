package com.yidejia.app.mall;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.ctrl.IpAddress;
import com.yidejia.app.mall.jni.JNICallBack;
import com.yidejia.app.mall.net.user.Login;
import com.yidejia.app.mall.util.Consts;
import com.yidejia.app.mall.util.DesUtils;
import com.yidejia.app.mall.util.HttpClientUtil;
import com.yidejia.app.mall.util.IHttpResp;
import com.yidejia.app.mall.widget.WiperSwitch;
import com.yidejia.app.mall.widget.WiperSwitch.OnChangedListener;

public class HomeLoginActivity extends HomeBaseActivity implements
		OnClickListener, OnChangedListener {
//	private MyApplication myApplication;
	private View view;
	private LayoutInflater inflater;
	private FrameLayout frameLayout;
	private ImageView edit1;
	private InputMethodManager inputMethodManager;
	private RelativeLayout findPwd;// 找回密码
	private RelativeLayout rapidRegist;// 快速注册
	private Button mLogin;
	private EditText stringName;
	private EditText stringPassword;
	private IpAddress ipAddress;
	private WiperSwitch mBox;
	private SharedPreferences sp;
	private Consts consts;
//	private RelativeLayout bottomLayout;
	public static boolean isCheck;

	private String name = "";
	private String pwd = "";
	private String ip = "";

	private String saveName;
	private String savePwd;
	private String deflaut;// 获取默认的值

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		Intent intent = getIntent();
		if(null != intent){
			Bundle bundle = intent.getExtras();
			if(null != bundle){
				deflaut = bundle.getString("exit");
			}
		}
		setContentView(R.layout.activity_main_fragment_layout);

//		myApplication = (MyApplication) getApplication();
		inflater = LayoutInflater.from(this);
		view = inflater.inflate(R.layout.my_mall_login, null);
		frameLayout = (FrameLayout) findViewById(R.id.main_fragment);
		frameLayout.addView(view);

		// 设置底部
//		bottomLayout = (RelativeLayout) findViewById(R.id.down_parent_layout);

		// 添加头部
		setActionBarConfigView();
		setCurrentActivityId(5);

		inputMethodManager = (InputMethodManager) HomeLoginActivity.this
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		ipAddress = new IpAddress();
		consts = new Consts();
		sp = PreferenceManager
				.getDefaultSharedPreferences(HomeLoginActivity.this);

		saveName = sp.getString("DESMI", null);
		//
		String basePwd = sp.getString("DESPWD", null);
		String keyName = saveName + consts.getMiStr();

		savePwd = DesUtils.decode(keyName, basePwd);

		// 设置默认选中
		mBox = (WiperSwitch) view.findViewById(R.id.cb_my_login_checkbox);
		mBox.setOnChangedListener(this);
		findPwd = (RelativeLayout) view
				.findViewById(R.id.re_my_mall_login_retrieve_password);

		// 设置监听
		findPwd.setOnClickListener(this);
		rapidRegist = (RelativeLayout) view
				.findViewById(R.id.re_my_mall_login_retrieve_regist);
		rapidRegist.setOnClickListener(this);
		mLogin = (Button) view.findViewById(R.id.bt_my_mall_login_button);
		mLogin.setOnClickListener(this);
		stringName = (EditText) view
				.findViewById(R.id.et_my_mall_login__edittext_account);
		stringPassword = (EditText) view
				.findViewById(R.id.et_my_mall_login__edittext_password);
		
		if (null != deflaut && Consts.EXIT_LOGIN.equals(deflaut)
				&& null != saveName && !"".equals(saveName)) {
			stringName.setText(saveName);
			stringPassword.setText(savePwd);
		}
		// 优化登录账号密码焦点获取
		LinearLayout login_acount = (LinearLayout) view
				.findViewById(R.id.ll_login_acount);
		LinearLayout login_psw = (LinearLayout) view
				.findViewById(R.id.ll_login_psw);
		login_acount
				.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
		login_psw.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
		login_acount.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				stringName.requestFocus();
				stringName.setCursorVisible(true);
				stringPassword.setCursorVisible(false);
			}
		});

		login_psw.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				stringPassword.requestFocus();
				stringPassword.setCursorVisible(true);
				stringName.setCursorVisible(false);
			}
		});
	}

	/**
	 * 头部
	 */
	private void setActionBarConfigView() {
		setActionbarConfig();
		setTitle(getResources().getString(R.string.login_title_add));
		TextView leftView = (TextView) findViewById(R.id.ab_common_back);
		leftView.setVisibility(View.GONE);
		edit1 = (ImageView) findViewById(R.id.ab_common_iv_share);
		edit1.setImageDrawable(getResources().getDrawable(R.drawable.setting));
		edit1.setVisibility(View.VISIBLE);
		edit1.setOnClickListener(edit);
	}

	/**
	 * 头部的点击事件
	 */
	private OnClickListener edit = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			Intent intent = new Intent(HomeLoginActivity.this,
					EditorActivity.class);
			startActivity(intent);

		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.re_my_mall_login_retrieve_password:// 找回密码
			Intent intent1 = new Intent(HomeLoginActivity.this,
					FindPwActivity.class);
			HomeLoginActivity.this.startActivity(intent1);

			break;

		case R.id.re_my_mall_login_retrieve_regist:// 快速注册
			Intent registIntent = new Intent(HomeLoginActivity.this,
					RegistActivity.class);
			HomeLoginActivity.this.startActivity(registIntent);

			break;
		case R.id.bt_my_mall_login_button:// 登录
			name = stringName.getText().toString();
			pwd = stringPassword.getText().toString();
			ip = ipAddress.getIpAddress();
			if (name == null || "".equals(name)) {
				Toast.makeText(HomeLoginActivity.this, "账号不能为空!",
						Toast.LENGTH_LONG).show();
				return;
			}
			if (pwd == null || "".equals(pwd)) {
				Toast.makeText(HomeLoginActivity.this, "密码不能为空!",
						Toast.LENGTH_LONG).show();
				return;
			}
			// 登录
			login();
			break;
		default:
			break;
		}
	}

	/** 登录操作，向服务器发送登录请求 **/
	private void login() {
		String param = new JNICallBack().getHttp4Login(name, pwd, ip);
		String url = new JNICallBack().HTTPURL;
//		Log.e("system.out", url + "?" + param);
		HttpClientUtil httpClientUtil = new HttpClientUtil(this);
		httpClientUtil.setIsShowLoading(true);
		httpClientUtil.setShowErrMessage(true);
		httpClientUtil.getHttpResp(url, param, new IHttpResp() {

			@Override
			public void onSuccess(String content) {
				
//				Log.e("system.out", content);
				
				Login login = new Login();
				boolean issuccess = login.parseLogin(content);
				if (issuccess) { // 登录成功
					Toast.makeText(HomeLoginActivity.this,
							getResources().getString(R.string.login_success),
							Toast.LENGTH_LONG).show();
					Intent intent = new Intent(HomeLoginActivity.this,
							HomeMyMallActivity.class);
					intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
					finish();

					sp.edit().putString("DESMI", name).commit();
					sp.edit().putBoolean("CHECK", true).commit();
					String keyName = name + consts.getMiStr();

					try {
						String demi = DesUtils.encode(keyName, pwd);
						sp.edit().putString("DESPWD", demi).commit();
					} catch (Exception e) {
						e.printStackTrace();
					}
					if(null != inputMethodManager)
					inputMethodManager.hideSoftInputFromWindow(HomeLoginActivity.this.getCurrentFocus().getWindowToken(), 0);
					
				} else {
					Toast.makeText(HomeLoginActivity.this, login.getMsg(),
							Toast.LENGTH_LONG).show();
					sp.edit().remove("CHECK").commit();
					sp.edit().remove("DESMI").commit();
					sp.edit().remove("DESPWD").commit();
				}
			}
		});
	}

	@Override
	protected void onPause() {
		super.onPause();
		StatService.onPageEnd(this, getString(R.string.login_title));
	}

	@Override
	protected void onResume() {
		super.onResume();
		StatService.onPageStart(this, getString(R.string.login_title));
	}

	@Override
	public void OnChanged(WiperSwitch wiperSwitch, boolean checkState) {
		// TODO Auto-generated method stub
		Log.e("info", checkState + "state");
		isCheck = checkState;
	}

}
