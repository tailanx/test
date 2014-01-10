package com.yidejia.app.mall;

import java.io.IOException;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.baidu.mobstat.StatService;
import com.yidejia.app.mall.R;
import com.yidejia.app.mall.ctrl.IpAddress;
import com.yidejia.app.mall.datamanage.CartsDataManage;
import com.yidejia.app.mall.exception.TimeOutEx;
import com.yidejia.app.mall.net.user.Login;
import com.yidejia.app.mall.util.BottomChange;
import com.yidejia.app.mall.util.Consts;
import com.yidejia.app.mall.util.DesUtils;
import com.yidejia.app.mall.widget.YLProgressDialog;

public class HomeLogActivity extends SherlockFragmentActivity implements
		OnClickListener {
	private MyApplication myApplication;
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
//	private ImageView configImageView;
	// private UserDatamanage userManage;
	private IpAddress ipAddress;
	private CheckBox mBox;
	private SharedPreferences sp;
	private Consts consts;
	private Task taskLoginAct;
	private BottomChange bottomChange;
	private RelativeLayout bottomLayout;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		cartsDataManage = new CartsDataManage();
		setContentView(R.layout.activity_main_fragment_layout);
		myApplication = (MyApplication) getApplication();
		inflater = LayoutInflater.from(this);
		view = inflater.inflate(R.layout.login, null);
		frameLayout = (FrameLayout) findViewById(R.id.main_fragment);
		frameLayout.addView(view);
		int current = getIntent().getIntExtra("current", -1);
		int next = getIntent().getIntExtra("next", -1);
		// 设置底部
		bottomLayout = (RelativeLayout) findViewById(R.id.down_parent_layout);
		bottomChange = new BottomChange(this, bottomLayout);
		if (current != -1 || next != -1) {
			bottomChange.initNavView(current, next);
		}
		initNavView();
		// 添加头部
		setActionBarConfig();

		inputMethodManager = (InputMethodManager) HomeLogActivity.this
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		ipAddress = new IpAddress();
		consts = new Consts();
		sp = PreferenceManager
				.getDefaultSharedPreferences(HomeLogActivity.this);
		// 设置默认选中
		mBox = (CheckBox) view.findViewById(R.id.my_login_checkbox);
		findPwd = (RelativeLayout) view
				.findViewById(R.id.my_mall_login_retrieve_password);
		// 设置监听
		findPwd.setOnClickListener(this);
		rapidRegist = (RelativeLayout) view
				.findViewById(R.id.my_mall_login_retrieve_regist);
		rapidRegist.setOnClickListener(this);
		mLogin = (Button) view.findViewById(R.id.my_mall_login_button);
		mLogin.setOnClickListener(this);
		stringName = (EditText) view
				.findViewById(R.id.my_mall_login__edittext_account);
		stringPassword = (EditText) view
				.findViewById(R.id.my_mall_login__edittext_password);
		// 优化登录账号密码焦点获取
		LinearLayout login_acount = (LinearLayout) view
				.findViewById(R.id.login_acount);
		LinearLayout login_psw = (LinearLayout) view
				.findViewById(R.id.login_psw);
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
		String baseName = sp.getString("DESMI", null);
		//
		String basePwd = sp.getString("DESPWD", null);
		String keyName = baseName + consts.getMiStr();
		String basepasswrod = DesUtils.decode(keyName, basePwd);

		if (baseName != null && basepasswrod != null) {
			mBox.setChecked(true);
			stringName.setText(baseName);
			stringPassword.setText(basepasswrod);
		}
	}

	private RelativeLayout downHomeLayout;
	private RelativeLayout downGuangLayout;
	private RelativeLayout downSearchLayout;
	private RelativeLayout downShoppingLayout;
	private RelativeLayout downMyLayout;
//	private ImageView down_home_imageView;// 首页按钮图片
//	private ImageView down_guang_imageView;// 逛按钮图片
//	private ImageView down_search_imageView;// 搜索按钮图片
//	private ImageView down_shopping_imageView; // 购物车按钮图片
//	private ImageView down_my_imageView; // 我的商城按钮图片
	private CartsDataManage cartsDataManage;
//	private TextView down_home_textview;
//	private TextView down_guang_textview;
//	private TextView down_search_textview;
//	private TextView down_shopping_textview;
//	private TextView down_my_textview;
	private int number;
	private Button cartImage;

	/**
	 * 底部事件
	 */
	/**
	 * 初始化底部导航栏
	 */
	private void initNavView() {
		// 改变底部首页背景，有按下去的效果的背景
		number = cartsDataManage.getCartAmount();
		cartImage = (Button) findViewById(R.id.down_shopping_cart);
		if (number == 0) {
			cartImage.setVisibility(View.GONE);
		} else {
			cartImage.setText(number + "");
		}
		downGuangLayout = (RelativeLayout) findViewById(R.id.re_down_guang_layout);
		downHomeLayout = (RelativeLayout) findViewById(R.id.re_down_home_layout);
		downSearchLayout = (RelativeLayout) findViewById(R.id.re_down_search_layout);
		downShoppingLayout = (RelativeLayout) findViewById(R.id.re_down_shopping_layout);
		downMyLayout = (RelativeLayout) findViewById(R.id.re_down_my_layout);

//		down_home_imageView = (ImageView) findViewById(R.id.down_home_icon);
//		down_home_textview = (TextView) findViewById(R.id.down_home_text);
//		down_my_imageView = (ImageView) findViewById(R.id.down_my_icon);
//		down_my_textview = (TextView) findViewById(R.id.down_my_text);

		downHomeLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HomeLogActivity.this, HomeMallActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
			}
		});
		downSearchLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HomeLogActivity.this, HomeSearchActivity.class);
				intent.putExtra("current", 3);
				intent.putExtra("next", 1);
				startActivity(intent);
				HomeLogActivity.this.finish();
				overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
				
			}
		});
		downShoppingLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HomeLogActivity.this,HomeCarActivity.class);
				intent.putExtra("current", 3);
				intent.putExtra("next", 2);
				startActivity(intent);
				HomeLogActivity.this.finish();
				overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
			}
		});
	}

	/**
	 * 头部
	 */
	private void setActionBarConfig() {
		getSupportActionBar().setCustomView(R.layout.actionbar_search);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setDisplayUseLogoEnabled(false);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setCustomView(R.layout.login_top);
		edit1 = (ImageView) findViewById(R.id.config_btn);
		edit1.setOnClickListener(edit);
	}

	/**
	 * 头部的点击事件
	 */
	private OnClickListener edit = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			Intent intent = new Intent(HomeLogActivity.this,
					EditorActivity.class);
			startActivity(intent);

		}
	};
	private String name;
	private String pwd;
	private String ip;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		
		case R.id.my_mall_login_retrieve_password:// 找回密码
			Intent intent1 = new Intent(HomeLogActivity.this,
					FindPwActivity.class);
			HomeLogActivity.this.startActivity(intent1);

			break;

		case R.id.my_mall_login_retrieve_regist:// 快速注册
			Intent registIntent = new Intent(HomeLogActivity.this,
					RegistActivity.class);
			HomeLogActivity.this.startActivity(registIntent);

			break;
		case R.id.my_mall_login_button:// 登录
			name = stringName.getText().toString();
			pwd = stringPassword.getText().toString();
			ip = ipAddress.getIpAddress();
			if (name == null || "".equals(name)) {
				Toast.makeText(HomeLogActivity.this, "账号不能为空!",
						Toast.LENGTH_LONG).show();
				return;
			}
			if (pwd == null || "".equals(pwd)) {
				Toast.makeText(HomeLogActivity.this, "密码不能为空!",
						Toast.LENGTH_LONG).show();
				return;
			}
		
			taskLoginAct = new Task();
			taskLoginAct.execute();
			// taskLoginAct.loginAct(name, pwd, ipAddress.getIpAddress());
			break;
		}
	}

	private String message;
	private boolean isTimeout = false;

	private class Task extends AsyncTask<Void, Void, Boolean> {
		private ProgressDialog bar;

		@Override
		protected Boolean doInBackground(Void... params) {
			Login login = new Login();
			try {
				String httpresp;
				try {
					httpresp = login.getHttpResponse(name, pwd, ip);
					boolean issuccess = login.analysisHttpResp(
							HomeLogActivity.this, httpresp);
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

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// bar = new ProgressDialog(getSherlockActivity());
			// bar.setCancelable(true);
			// bar.setMessage(getSherlockActivity().getResources().getString(R.string.loading));
			// bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			// bar.show();
			bar = (ProgressDialog) new YLProgressDialog(HomeLogActivity.this)
					.createLoadingDialog(HomeLogActivity.this, null);
			bar.setOnCancelListener(new DialogInterface.OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					cancel(true);
				}
			});
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			bar.dismiss();
			if (result) {
				Toast.makeText(HomeLogActivity.this, "登陆成功！", Toast.LENGTH_LONG)
						.show();
				Intent intent = new Intent(HomeLogActivity.this, HomeMyMaActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
				HomeLogActivity.this.finish();
				// 隐藏键盘
				inputMethodManager.hideSoftInputFromWindow(
						stringName.getWindowToken(), 0);
				inputMethodManager.hideSoftInputFromWindow(
						stringPassword.getWindowToken(), 0);

				myApplication.setIsLogin(true);
				
				if (mBox.isChecked()) {
					sp.edit().putString("DESMI", name).commit();

					String keyName = name + consts.getMiStr();

					try {
						String demi = DesUtils.encode(keyName, pwd);
						sp.edit().putString("DESPWD", demi).commit();
					} catch (Exception e) {
						e.printStackTrace();
					}

				} else {
					sp.edit().remove("DESMI").commit();
					sp.edit().remove("DESPWD").commit();
				}
			} else {
				if (isTimeout) {
					Toast.makeText(
							HomeLogActivity.this,
							HomeLogActivity.this.getResources().getString(
									R.string.time_out), Toast.LENGTH_SHORT)
							.show();
					isTimeout = false;
					return;
				}
				Toast.makeText(HomeLogActivity.this, message, Toast.LENGTH_LONG)
						.show();
			}
		}

	}

	// 双击返回键退出程序
	private long exitTime = 0;

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(),
						getResources().getString(R.string.exit),
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				// ((MyApplication)getApplication()).setUserId("");
				// ((MyApplication)getApplication()).setToken("");
				finish();
				// System.exit(0);
			}
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}

	@Override
	protected void onPause() {
		super.onPause();
		StatService.onPause(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		StatService.onResume(this);
	}
	
}
